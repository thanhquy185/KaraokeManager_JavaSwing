package BLL;

import java.util.ArrayList;

import DAL.ProductDAL;
import DTO.ProductDTO;

public class ProductBLL {
	// Properties
	private ProductDAL productDAL;

	// Constructors
	public ProductBLL() {
		productDAL = new ProductDAL();
	}

	// Methods
	// - Hàm lấy ra danh sách các sản phẩm hiện có trong CSDL
	public ArrayList<ProductDTO> getAllProduct() {
		return productDAL.selectAll();
	}

	// - Hàm lấy ra danh sách các sản phẩm hiện có với 1 điều kiện trong CSDL
	public ArrayList<ProductDTO> getAllProductByCondition(String[] join, String condition, String order) {
		return productDAL.selectAllByCondition(join, condition, order);
	}

	// - Hàm lấy ra một người dùng với mã sản phẩm tương ứng
	public ProductDTO getOneProductById(String id) {
		return productDAL.selectOneById(id);
	}
}
