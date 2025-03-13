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
