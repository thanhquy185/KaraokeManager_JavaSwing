package BLL;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Vector;

import DAL.OrderDAL;
import DTO.CustomerTypeDTO;
import DTO.FoodDTO;
import DTO.OrderDTO;
import DTO.OrderDetailDTO;
import DTO.RoomTypeDTO;
import DTO.OrderDTO;

public class OrderBLL {
	// Properties
	private OrderDAL orderDAL;

	// Constructors
	public OrderBLL() {
		orderDAL = new OrderDAL();
	}

	// Methods
	// - Hàm tạo một đơn hàng
	public String insertOrder(Integer id, String timeCreate, String timeStart, String timeEnd, String room,
			Integer employee, String customer,
			Long totalPrice, Integer status) {
		if (id == null && timeCreate == null && timeStart == null && room == null
				&& employee == null && customer == null && totalPrice == null
				&& status == null) {
			return "Thông tin hoá đơn không đầy đủ";
		}
		if (room == null)
			return "Chưa chọn phòng hát";
		if (customer == null)
			return "Chưa chọn khách hàng";

		OrderDTO order = new OrderDTO(id, timeCreate, timeStart, timeEnd, room, employee, customer, totalPrice, status);
		int inform = orderDAL.insert(order);
		return inform > 0 ? "Thêm hoá đơn thành công" : "Thêm hoá đơn thất bại";
	}

	// - Hàm cập nhật một đơn hàng
	public String updateOrder(Integer orderId, String timeStart, String timeEnd, String room, String customer,
			Integer status,
			Vector<OrderDetailDTO> orderDetails) {
		// Xử lý các món ăn (chi tiết hoá đơn)
		if (orderDetails.size() > 0) {
			FoodBLL foodBLL = new FoodBLL();
			OrderDetailBLL orderDetailBLL = new OrderDetailBLL();

			if (status == 2) {
				for (OrderDetailDTO orderDetail : orderDetails) {
					FoodDTO foodDTO = foodBLL.getOneFoodById(orderDetail.getFoodId());
					if (foodDTO.getInventory() - orderDetail.getQuantity() < 0) {
						return String.format("Món ăn có mã '%s' không đủ số lượng để bán", foodDTO.getId());
					}
				}

				for (OrderDetailDTO orderDetail : orderDetails) {
					foodBLL.updateInventoryFood("delete", orderDetail.getFoodId(),
							orderDetail.getQuantity());
				}
			}

			for (OrderDetailDTO orderDetail : orderDetails) {
				orderDetailBLL.insertOrderDetail(orderDetail.getOrderId(), orderDetail.getFoodId(),
						orderDetail.getPrice(), orderDetail.getQuantity());
			}
		}

		// Các đối tượng BLL khác
		RoomBLL roomBll = new RoomBLL();
		RoomTypeBLL roomTypeBLL = new RoomTypeBLL();
		CustomerBLL customerBLL = new CustomerBLL();
		CustomerTypeBLL customerTypeBLL = new CustomerTypeBLL();

		// Các đối tượng từ tầng DTO
		RoomTypeDTO roomTypeDTO = roomTypeBLL.getOneRoomTypeById(roomBll.getOneRoomById(room).getType());
		CustomerTypeDTO customerTypeDTO = customerTypeBLL
				.getOneCustomerTypeById(customerBLL.getOneCustomerById(customer).getType());

		BigInteger totalPriceBI = BigInteger.ZERO;
		// - Tính tiền phòng
		Long hours = CommonBLL.calHoursBetweenTwoTimes(timeStart, timeEnd);
		BigInteger costPerHour = BigInteger.valueOf(roomTypeDTO.getCost());
		totalPriceBI = totalPriceBI.add(BigInteger.valueOf(hours).multiply(costPerHour));
		// - Tính tiền món ăn
		for (OrderDetailDTO orderDetail : orderDetails) {
			BigInteger price = BigInteger.valueOf(orderDetail.getPrice());
			BigInteger quantity = BigInteger.valueOf(orderDetail.getQuantity());
			totalPriceBI = totalPriceBI.add(price.multiply(quantity));
		}
		// - Giảm giá nếu là khách quen
		if (customerTypeDTO.getDiscount() > 0) {
			BigDecimal totalPriceDecimal = new BigDecimal(totalPriceBI);
			BigDecimal discountPercent = BigDecimal.valueOf(customerTypeDTO.getDiscount())
					.divide(BigDecimal.valueOf(100));
			BigDecimal discountAmount = totalPriceDecimal.multiply(discountPercent);
			BigInteger discountAmountInt = discountAmount.setScale(0, RoundingMode.DOWN).toBigInteger();
			totalPriceBI = totalPriceBI.subtract(discountAmountInt);
		}
		Long totalPrice = totalPriceBI.longValueExact();

		// - Xử lý xong thì cập nhật vào csdl
		OrderDTO orderUpdate = new OrderDTO(orderId, null, null, timeEnd, null, null, null, totalPrice, status);
		int result = orderDAL.update(orderUpdate);

		return result > 0 ? "Thay đổi trạng thái hoá đơn thành công" : "Thay đổi trạng thái hoá đơn thất bại";
	}

	// - Hàm lấy ra danh sách các hoá đơn hiện có trong CSDL
	public ArrayList<OrderDTO> getAllOrder() {
		return orderDAL.selectAll();
	}

	// - Hàm lấy ra danh sách các hoá đơn hiện có với 1 điều kiện trong CSDL
	public ArrayList<OrderDTO> getAllOrderByCondition(String[] join, String condition, String order) {
		return orderDAL.selectAllByCondition(join, condition, order);
	}

	// - Hàm lấy ra một hoá đơn với mã hoá đơn tương ứng
	public OrderDTO getOneOrderById(String id) {
		return orderDAL.selectOneById(id);
	}

	// - Hàm lấy ra đơn hàng cuối cùng
	public OrderDTO getLastOrder() {
		ArrayList<OrderDTO> list = orderDAL.selectAllByCondition(null, null, "maHoaDon DESC");
		return list.isEmpty() ? new OrderDTO(0, null, null, null, null, null, null, 0L, 0) : list.get(0);
	}
}
