package BLL;

import java.util.ArrayList;

import DAL.CategoryDAL;
import DTO.CategoryDTO;
import DTO.SupplierDTO;

public class CategoryBLL {
	// Properties
	private CategoryDAL categoryDAL;

	// Constructors
	public CategoryBLL() {
		categoryDAL = new CategoryDAL();
	}

	// Methods
	// Kiểm tra dữ liệu đầu vào
	private boolean isInputed(String value) {
		return value != null && !value.trim().isEmpty();
	}

	private boolean isValidId(String id) {
		return id != null && id.matches("LMA\\d{2}");
	}

	private boolean isValidStatus(String status) {
		return status != null && (status.equals("Hoạt động") || status.equals("Tạm dừng"));
	}

	private boolean isExistsId(String id) {
		String[] join = null;
		String condition = String.format("maLMA = '%s'", id);
		String order = null;
		return !categoryDAL.selectAllByCondition(join, condition, order).isEmpty();
	}

	// Thêm loại món ăn
	public String insertCategory(String id, String name, String status, String timeUpdate) {
		// Kiểm tra đầu vào
		if (!isInputed(id))
			return "Chưa nhập mã loại món ăn";
		if (!isInputed(name))
			return "Chưa nhập tên loại món ăn";
		if (!isInputed(status))
			return "Chưa chọn trạng thái";

		// Kiểm tra định dạng
		if (!isValidId(id))
			return "Mã loại món ăn phải có định dạng LMAxx (x là số)";
		if (!isValidStatus(status))
			return "Trạng thái không hợp lệ";

		// Kiểm tra trùng lặp
		if (isExistsId(id))
			return "Mã loại món ăn đã tồn tại";

		// Thêm vào database
		boolean categoryStatus = status.equals("Hoạt động");
		CategoryDTO newCategory = new CategoryDTO(id, name, categoryStatus, timeUpdate);
		int rowsAffected = categoryDAL.insert(newCategory);
		return rowsAffected > 0 ? "Thêm loại món ăn thành công" : "Không thể thêm loại món ăn vào cơ sở dữ liệu";
	}

	// Cập nhật loại món ăn
	public String updateCategory(String id, String name, String timeUpdate) {
		// Kiểm tra đầu vào
		if (!isInputed(name))
			return "Chưa nhập tên loại món ăn";

		// Kiểm tra tồn tại
		CategoryDTO existingCategory = categoryDAL.selectOneById(id);
		if (existingCategory == null)
			return "loại món ăn không tồn tại";

		// Cập nhật
		CategoryDTO updatedCategory = new CategoryDTO(id, name, existingCategory.getStatus(), timeUpdate);
		int rowsAffected = categoryDAL.update(updatedCategory);
		return rowsAffected > 0 ? "Cập nhật loại món ăn thành công" : "Không thể cập nhật loại món ăn";
	}

	// Khóa/Mở khóa loại món ăn
	public String lockCategory(String id, String timeUpdate) {
		CategoryDTO category = categoryDAL.selectOneById(id);
		if (category == null)
			return "Loại món ăn không tồn tại";

		// Đảo ngược trạng thái trước khi cập nhật
		category.setStatus(!category.getStatus());
		category.setTimeUpdate(timeUpdate);
		int rowsAffected = categoryDAL.lock(category);
		return rowsAffected > 0 ? "Thay đổi trạng thái thành công" : "Không thể thay đổi trạng thái loại món ăn";
	}

	// - Hàm lấy ra danh sách các loại món ăn hiện có trong CSDL
	public ArrayList<CategoryDTO> getAllCategory() {
		return categoryDAL.selectAll();
	}

	// - Hàm lấy ra danh sách các loại món ăn hiện có với 1 điều kiện trong CSDL
	public ArrayList<CategoryDTO> getAllCategoryByCondition(String[] join, String condition, String order) {
		return categoryDAL.selectAllByCondition(join, condition, order);
	}

	// - Hàm lấy ra một người dùng với mã loại món ăn tương ứng
	public CategoryDTO getOneCategoryById(String id) {
		return categoryDAL.selectOneById(id);
	}

	// - Hàm lấy ra loại món ăn cuối cùng
	public CategoryDTO getLastCategory() {
		ArrayList<CategoryDTO> categories = categoryDAL.selectAllByCondition(null, null, "maLoaiMonAn DESC");
		return categories.isEmpty() ? new CategoryDTO("LMA00", "", true, "") : categories.get(0);
	}
}
