package BLL;

import java.util.ArrayList;

import DAL.DiscountTypeDAL;
import DTO.DiscountTypeDTO;

public class DiscountTypeBLL {
	// Properties
		private DiscountTypeDAL discountTypeDAL;

		// Constructors
		public DiscountTypeBLL() {
			discountTypeDAL = new DiscountTypeDAL();
		}

		// Methods
		// - Hàm lấy ra danh sách các loại khuyến mãi hiện có trong CSDL
		public ArrayList<DiscountTypeDTO> getAllDiscountType() {
			return discountTypeDAL.selectAll();
		}

		// - Hàm lấy ra danh sách các loại khuyến mãi hiện có với 1 điều kiện trong CSDL
		public ArrayList<DiscountTypeDTO> getAllDiscountTypeByCondition(String[] join, String condition, String order) {
			return discountTypeDAL.selectAllByCondition(join, condition, order);
		}

		// - Hàm lấy ra một người dùng với mã loại khuyến mãi tương ứng
		public DiscountTypeDTO getOneDiscountTypeById(String id) {
			return discountTypeDAL.selectOneById(id);
		}
}
