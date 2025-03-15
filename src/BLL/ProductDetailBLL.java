package BLL;

import java.util.ArrayList;

import DAL.ProductDetailDAL;
import DTO.ProductDetailDTO;

public class ProductDetailBLL {
    // Properties
	private ProductDetailDAL productDetailDAL;

	// Constructors
	public ProductDetailBLL() {
		productDetailDAL = new ProductDetailDAL();
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
		if(!CommonBLL.isValidStringType04(quantity) || Integer.parseInt(quantity)>0) {
			return false;
		}
		return true;
	}

	// - Hàm kiểm tra nguyên liệu đã tồn tại trong CTSP chưa?
	public boolean isExistIngredientId(String productId, String ingredientId)
	{
		String[] join = null;
		String condition = String.format("maSanPham = '%s' AND maNguyenLieu = '%s' ", productId, ingredientId);
		String order = null;
		ArrayList<ProductDetailDTO> result = productDetailDAL.selectAllByCondition(join, condition, order);
		if(result == null || !result.isEmpty()) return false;
		return true;
	}

	// - Hàm cập nhật CTSP
	public String updateProductDetail(String productId, String ingredientId, String quantity)
	{
		if(!isInputedQuantity(quantity)) return "Chưa nhập định lượng nguyên liệu sản phẩm";
		if(!isValidQuantity(quantity)) return "Nhập sai định dạng của định lượng sản phẩm";
		
		// Kiểm tra nguyên liệu này đã tồn tại trong CTSP chưa ?
		ProductDetailDTO productDetail = new ProductDetailDTO(productId, ingredientId, quantity);
		if(!isExistIngredientId(productId,ingredientId)) 
		{
			productDetailDAL.insert(productDetail);
		}
		else
		{
			productDetailDAL.update(productDetail);
		}
		return "Có thể cập nhật một CTSP";
	}

	// - Hàm xóa một CTSP
	public void deleteProductDetail(String productId, String ingredientId)
	{
		if(isExistIngredientId(productId,ingredientId)) 
		{
			productDetailDAL.delete(productId, ingredientId);
		}
	}

	// - Hàm lấy ra danh sách các chi tiết sản phẩm hiện có trong CSDL
	public ArrayList<ProductDetailDTO> getAllProductDetail() {
		return productDetailDAL.selectAll();
	}

	// - Hàm lấy ra danh sách các chi tiết sản phẩm hiện có với 1 điều kiện trong CSDL
	public ArrayList<ProductDetailDTO> getAllProductDetailByCondition(String[] join, String condition, String order) {
		return productDetailDAL.selectAllByCondition(join, condition, order);
	}

	// - Hàm lấy ra danh sách CTSP của mã sản phẩm tương ứng
	public ProductDetailDTO getProductDetailById(String id) {
		return productDetailDAL.selectOneById(id);
	}
}
