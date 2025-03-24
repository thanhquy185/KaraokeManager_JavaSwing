package BLL;

import java.util.ArrayList;

import DAL.OrderDAL;
import DTO.OrderDTO;

public class OrderBLL {
	// Properties
	private OrderDAL orderDAL;

	// Constructors
	public OrderBLL() {
		orderDAL = new OrderDAL();
	}

	// Methods
	// - Hàm kiểm tra giá sản phẩm đã được nhập hay chưa ?
	public boolean isInputedCost(String cost)
	{
		if(cost == null || cost.isEmpty()) return false;
		return true;
	}

	// - Hàm kiểm tra giá sản phẩm đã hợp lệ hay chưa ?
	public boolean isValidCost(String cost)
	{
		if(!CommonBLL.isValidStringType04(cost)) {
			return false;
		}
		return true;
	}
	// - Hàm cập nhật hóa đơn
	public String updateOrder(String id, String dateOrder, String roomId, String employeeId, 
			String customerId, String discountId, String time, String cost, String status, String dateUpdate)
	{
		if(!isInputedCost(cost) || !isValidCost(cost)) return "Số tiền không hợp lệ";
		
		// - Nếu thỏa mãn thì cập nhật vào hóa đơn
		int orderId = Integer.parseInt(id);
		String date1 = dateOrder;
		String room = roomId;
		int employeeid = Integer.parseInt(employeeId);
		String customer = customerId;
		String discount = discountId;
		int t = Integer.parseInt(time);
		Long gia = Long.parseLong(cost);
		boolean trangThai = (status == "1") ? true : false;
		String date2 = dateUpdate;
		OrderDTO order = new OrderDTO(orderId, date1, room, employeeid, customer, discount, t, gia, trangThai, date2);
		
		orderDAL.update(order);
		return "Có thể thay đổi một hóa đơn";
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
}
