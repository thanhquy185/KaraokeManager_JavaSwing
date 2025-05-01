package BLL;

import java.util.ArrayList;

import DAL.ProductDAL;
import DTO.ProductDTO;
import DTO.ProductDetailDTO;

public class ProductBLL {
	// Properties
	private ProductDAL productDAL;
	private ProductDetailBLL productDetailBLL;
	private FoodBLL ingredientBLL;
	// Constructors
	public ProductBLL() {
		productDAL = new ProductDAL();
		productDetailBLL = new ProductDetailBLL();
		ingredientBLL = new FoodBLL();
	}

	// Methods
	// - Hàm kiểm tra mã sản phẩm đã được nhập hay chưa ?
	public boolean isInputedId(String id)
	{
		if(id == null || id.isEmpty()) return false;
		return true;
	}

	// - Hàm kiểm tra mã sản phẩm đã hợp lệ hay chưa ?
	public boolean isValidId(String id)
	{
		if(id.length() != 7 || !CommonBLL.isValidStringType03(id)) {
			return false;
		}
		return true;
	}

	// - Hàm kiểm tra tên sản phẩm đã được nhập hay chưa ?
	public boolean isInputedName(String name)
	{
		if(name == null || name.isEmpty()) return false;
		return true;
	}

	// - Hàm kiểm tra giá sản phẩm đã được nhập hay chưa ?
	public boolean isInputedPrice(String price)
	{
		if(price == null || price.isEmpty()) return false;
		return true;
	}

	// - Hàm kiểm tra giá sản phẩm đã hợp lệ hay chưa ?
	public boolean isValidPrice(String price)
	{
		if(!CommonBLL.isValidStringType04(price)) {
			return false;
		}
		return true;
	}

	// - Hàm kiểm tra mã loại sản phẩm đã được chọn hay chưa ?
	public boolean isSelectedType(String type) {
		if (type == null || type.isEmpty()) {
			return false;
		}
		return true;
	}

	// - Hàm kiểm tra mã loại sản phẩm đã hợp lệ hay chưa ?
	public String convertType(String type) {
		switch (type) {
			case "Món khô": return "KHO";
			case "Món nước": return "NUOC";
			case "Khác": return "KHAC";
			case "Món tráng miệng": return "TRANGMIENG";
			case "Đồ uống": return "DOUONG";
			case "Chất có hại": return "CHATCOHAI";
			default: return null; // Nếu không hợp lệ, trả về null
		}
	}

	// - Hàm kiểm tra trạng thái đã được chọn hay chưa ?
	public boolean isSelectedStatus(String status) {
		if (status == null || status.isEmpty()) {
			return false;
		}
		return true;
	}

	// - Hàm kiểm tra trạng thái đã hợp lệ hay chưa ?
	public boolean isValidStatus(String status) {
		if (!status.equals("Hoạt động") && !status.equals("Tạm dừng")) {
			return false;
		}

		return true;
	}

	// - Hàm kiểm tra mã sản phẩm đã tồn tại hay chưa ?
	public boolean isExistsId(String id) {
		String[] join = null;
		String condition = String.format("maSanPham = '%s'", id);
		String order = null;
		ArrayList<ProductDTO> result = productDAL.selectAllByCondition(join, condition, order);
		if (result == null || result.isEmpty()) {
			return false;
		}
		return true;
	}

	// - Hàm kiểm tra tên sản phẩm đã tồn tại hay chưa ?
	public boolean isExistsName(String name) {
		String[] join = null;
		String condition = String.format("tenSanPham = '%s'", name);
		String order = null;
		ArrayList<ProductDTO> result = productDAL.selectAllByCondition(join, condition, order);
		if (result == null || result.isEmpty()) {
			return false;
		}
		return true;
	}

	// - Hàm thêm sản phẩm
	public String insertProduct(String id, String name, String type, String price,
			String image, String status, String dateUpdate)
	{
		if (!isInputedId(id) && !isInputedName(name) && !isSelectedType(type)
				&& !isSelectedStatus(status)) 
		{
			return "Chưa nhập đầy đủ thông tin sản phẩm cần thiết";
		}
		if(!isInputedId(id)) return "Chưa nhập mã sản phẩm";
		if(!isValidId(id)) return "Nhập sai định dạng mã sản phẩm";
		if(!isInputedName(name)) return "Chưa nhập tên sản phẩm";
		if(!isInputedPrice(price)) return "Chưa nhập giá sản phẩm";
		if(!isValidPrice(price)) return "Nhập sai định dạng giá";
		if (!isSelectedType(type)) return "Chưa chọn mã loại sản phẩm";
		type = convertType(type);
		if (type == null) return "Chọn sai định dạng mã loại sản phẩm";
		if (!isSelectedStatus(status)) return "Chưa chọn trạng thái";
		if (!isValidStatus(status)) return "Chọn sai định dạng trạng thái";
		
		if(isExistsId(id)) return "Mã sản phẩm đã tồn tại";
		if (isExistsName(name)) return "Tên sản phẩm đã tồn tại";
		// - Nếu thỏa mãn hết thì thêm vào CSDL
		String productId = id;
		String productName = name;
		String productType = type;
		Long productPrice = Long.parseLong(price);
		String productImage = image;
		boolean productStatus = status.equals("Hoạt động") ? true : false; 
		String productDateUpdate = dateUpdate;
		ProductDTO product = new ProductDTO(productId,productName,productType,productPrice,
				productImage,productStatus,productDateUpdate);
		
		productDAL.insert(product);
		return "Có thể thêm một sản phẩm";
	}

	// - Hàm cập nhật sản phẩm
	public String updateProduct( String id, String name, String type, String price,
			String image, String status, String dateUpdate)
	{
		if (!isInputedName(name) && !isSelectedType(type)) 
		{
			return "Chưa nhập đầy đủ thông tin sản phẩm cần thiết";
		}
		if(!isInputedName(name)) return "Chưa nhập tên sản phẩm";
		if(!isInputedPrice(price)) return "Chưa nhập giá sản phẩm";
		if(!isValidPrice(price)) return "Nhập sai định dạng giá";
		if (!isSelectedType(type)) return "Chưa chọn mã loại sản phẩm";
		type = convertType(type);
		if (type == null) return "Chọn sai định dạng mã loại sản phẩm";
		

		// - Nếu thỏa mãn hết thì thêm vào CSDL
		String productId = id;
		String productName = name;
		String productType = type;
		Long productPrice = Long.parseLong(price);
		String productImage = image;
		boolean productStatus = status.equals("Hoạt động") ? true : false; 
		String productDateUpdate = dateUpdate;
		ProductDTO product = new ProductDTO(productId,productName,productType,productPrice,
				productImage,productStatus,productDateUpdate);
		
		productDAL.update(product);
		return "Có thể thay đổi một sản phẩm";
	}

	// - Kiểm tra định lượng của sản phẩm với số lượng tồn
	public boolean checkProduct(String id)
	{
		String condition = String.format("maSanPham = '%s'", id);
		ArrayList<ProductDetailDTO> productList = productDetailBLL.getAllProductDetailByCondition(null, condition, null);
		for(int i = 0; i < productList.size(); i++)
		{
			int dinhLuong = Integer.parseInt(productList.get(i).getQuantity());
			Long ton = (ingredientBLL.getOneFoodById(productList.get(i).getIngredientId())).getInventory();
			if(dinhLuong > ton || dinhLuong == 0) return false;
		}
		return true;
	}

	// - Hàm khoá một sản phẩm
	public String lockProduct(String id, String dateUpdate) {
		// - Khoá hoặc mở khoá tuỳ vào trạng thái hiện tại
		ProductDTO lockProductDTO = getOneProductById(id);
		lockProductDTO.setStatus(lockProductDTO.getStatus() ? false : true);
		if(checkProduct(id) || !lockProductDTO.getStatus())
		{
			lockProductDTO.setDateUpdate(dateUpdate);
			productDAL.lock(lockProductDTO);
			return "Có thể khoá một sản phẩm";
		}
		return "Mặc định khóa do số lượng tồn nhỏ hơn định lượng nguyên liệu sản phẩm";
	}

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
