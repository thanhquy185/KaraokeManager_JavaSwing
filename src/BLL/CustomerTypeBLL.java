package BLL;

import java.util.ArrayList;

import DAL.CustomerTypeDAL;
import DTO.CustomerTypeDTO;

public class CustomerTypeBLL {
	// Properties
		private CustomerTypeDAL customerTypeDAL;

		// Constructors
		public CustomerTypeBLL() {
			customerTypeDAL = new CustomerTypeDAL();
		}

		// Methods
		// - Hàm lấy ra danh sách các loại khách hàng hiện có trong CSDL
		public ArrayList<CustomerTypeDTO> getAllCustomerType() {
			return customerTypeDAL.selectAll();
		}

		// - Hàm lấy ra danh sách các loại khách hàng hiện có với 1 điều kiện trong CSDL
		public ArrayList<CustomerTypeDTO> getAllCustomerTypeByCondition(String[] join, String condition, String order) {
			return customerTypeDAL.selectAllByCondition(join, condition, order);
		}

		// - Hàm lấy ra một người dùng với mã loại khách hàng tương ứng
		public CustomerTypeDTO getOneCustomerTypeById(String id) {
			return customerTypeDAL.selectOneById(id);
		}
}
