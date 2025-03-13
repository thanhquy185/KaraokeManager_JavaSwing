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
	// - Hàm lấy ra danh sách các hoá đơn hiện có trong CSDL
	public ArrayList<OrderDetailDTO> getAllOrderDetail() {
		return orderDetailDAL.selectAll();
	}

	// - Hàm lấy ra danh sách các hoá đơn hiện có với 1 điều kiện trong CSDL
	public ArrayList<OrderDetailDTO> getAllOrderDetailByCondition(String[] join, String condition, String OrderDetail) {
		return orderDetailDAL.selectAllByCondition(join, condition, OrderDetail);
	}

	// - Hàm lấy ra một hoá đơn với mã hoá đơn tương ứng
	public OrderDetailDTO getOneOrderDetailById(String id) {
		return orderDetailDAL.selectOneById(id);
	}
}
