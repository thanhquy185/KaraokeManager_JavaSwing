package BLL;

import java.util.ArrayList;

import DAL.OrderDetailDAL;
import DTO.InputTicketDetailDTO;
import DTO.OrderDetailDTO;
import DTO.OrderDetailDTO;

public class OrderDetailBLL {
	// Properties
	private OrderDetailDAL orderDetailDAL;

	// Constructors
	public OrderDetailBLL() {
		orderDetailDAL = new OrderDetailDAL();
	}

	// Methods
	// - Hàm kiểm tra giá sản phẩm đã được nhập hay chưa ?
	public boolean isInputedQuantity(String quantity) {
		if (quantity == null || quantity.trim().isEmpty())
			return false;
		return true;
	}

	// - Hàm kiểm tra giá sản phẩm đã hợp lệ hay chưa ?
	public boolean isValidQuantity(String quantity) {
		if (!CommonBLL.isValidStringType04(quantity)) {
			return false;
		}
		return true;
	}

	public String insertOrderDetail(Integer order, String foodId, Long price, Long quantity) {
		if (order == null || foodId == null || price == null || quantity == null) {
			return "Thông tin chi tiết phiếu nhập không đầy đủ";
		}

		OrderDetailDTO dto = new OrderDetailDTO(order, foodId, price, quantity);
		int result = orderDetailDAL.insert(dto);
		return result > 0 ? "Thêm chi tiết phiếu nhập thành công" : "Thêm chi tiết phiếu nhập thất bại";
	}

	// - Hàm lấy ra danh sách các CTHD hiện có trong CSDL
	public ArrayList<OrderDetailDTO> getAllOrderDetail() {
		return orderDetailDAL.selectAll();
	}

	// - Hàm lấy ra danh sách các CTHD hiện có với 1 điều kiện trong CSDL
	public ArrayList<OrderDetailDTO> getAllOrderDetailByCondition(String[] join, String condition, String OrderDetail) {
		return orderDetailDAL.selectAllByCondition(join, condition, OrderDetail);
	}

	// - Hàm lấy ra danh sách các chi tiết hoá đơn theo mã hoá đơn
	public ArrayList<OrderDetailDTO> getAllOrderDetailByOrderId(String orderId) {
        return orderDetailDAL.selectAllByOrderId(orderId);
    }

	// - Hàm lấy ra một CTHD với mã hoá đơn tương ứng
	public OrderDetailDTO getOneOrderDetailById(String id) {
		return orderDetailDAL.selectOneById(id);
	}
}
