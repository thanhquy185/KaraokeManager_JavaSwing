package BLL;

import java.util.ArrayList;

import DAL.OrderDetailDAL;
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
	public boolean isInputedQuantity(String quantity)
	{
		if(quantity == null || quantity.trim().isEmpty()) return false;
		return true;
	}

	// - Hàm kiểm tra giá sản phẩm đã hợp lệ hay chưa ?
	public boolean isValidQuantity(String quantity)
	{
		if(!CommonBLL.isValidStringType04(quantity)) {
			return false;
		}
		return true;
	}

	// - Hàm kiểm tra sản phẩm đã tồn tại trong CTHD chưa?
	public int isExistProductId(String orderId, String productId)
	{
		String[] join = null;
		String condition = String.format("maHoaDon = '%s' AND maSanPham = '%s' ",orderId,productId);
		String order = null;
		ArrayList<OrderDetailDTO> result = orderDetailDAL.selectAllByCondition(join, condition, order);
		if(result == null || result.isEmpty()) return -1;
		return result.get(0).getQuantity();
	}
	// - Hàm cập nhật một CTHD
	public String updateOrderDetail(String orderId, String productId, String quantity)
	{
		if(!isInputedQuantity(quantity)) return "Chưa nhập số lượng sản phẩm";
		if(!isValidQuantity(quantity)) return "Nhập sai định dạng của số lượng sản phẩm";
		// Kiểm tra sản phẩm này đã tồn tại trong CTHD chưa ?
		int check = isExistProductId(orderId,productId);
		OrderDetailDTO orderDetail = new OrderDetailDTO(Integer.parseInt(orderId), productId, (check == -1 ) ? Integer.parseInt(quantity) : Integer.parseInt(quantity)+check);
		if(check == -1)
		{
			orderDetailDAL.insert(orderDetail);
		}
		else {
			orderDetailDAL.update(orderDetail);
		}
		return "Có thể cập nhật một CTHD";
	}
	// - Hàm lấy ra danh sách các CTHD hiện có trong CSDL
	public ArrayList<OrderDetailDTO> getAllOrderDetail() {
		return orderDetailDAL.selectAll();
	}

	// - Hàm lấy ra danh sách các CTHD hiện có với 1 điều kiện trong CSDL
	public ArrayList<OrderDetailDTO> getAllOrderDetailByCondition(String[] join, String condition, String OrderDetail) {
		return orderDetailDAL.selectAllByCondition(join, condition, OrderDetail);
	}

	// - Hàm lấy ra một CTHD với mã hoá đơn tương ứng
	public OrderDetailDTO getOneOrderDetailById(String id) {
		return orderDetailDAL.selectOneById(id);
	}
}
