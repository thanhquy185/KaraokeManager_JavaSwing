package BLL;

import java.util.ArrayList;

import DAL.IngredientDAL;
import DTO.IngredientDTO;
import PL.CommonPL;

public class IngredientBLL {
    // Properties
    private IngredientDAL ingredientDAL;

    // Constructors
    public IngredientBLL() {
        ingredientDAL = new IngredientDAL();
    }

    // Methods
    // - Hàm kiểm tra mã nguyên liệu đã được nhập hay chưa
    public boolean isInputedId(String id) {
        if (id == null) {
            return false;
        }
        return true;
    }

    // - Hàm kiểm tra tên nguyên liệu đã được nhập hay chưa
    public boolean isInputedName(String name) {
        if (name == null) {
            return false;
        }
        return true;
    }

    // - Hàm kiểm tra đơn vị đã được nhập hay chưa
    public boolean isInputedUnit(String unit) {
        if (unit == null) {
            return false;
        }
        return true;
    }

    // - Hàm kiểm tra tồn kho đã được nhập hay chưa
    public boolean isInputedInventory(String inventory) {
        if (inventory == null) {
            return false;
        }
        return true;
    }

    // - Hàm kiểm tra trạng thái đã được chọn hay chưa
    public boolean isSelectedStatus(String status) {
        if (status == null) {
            return false;
        }
        return true;
    }

    // - Hàm kiểm tra mã nguyên liệu có hợp lệ hay không
    public boolean isValidId(String id) {
        if (id == null || !id.matches("NL\\d{4}")) {
            return false;
        }
        return CommonBLL.isValidStringType03(id); // Giữ kiểm tra cũ nếu cần
    }

    // - Hàm kiểm tra tên nguyên liệu có hợp lệ hay không
    public boolean isValidName(String name) {
        if (!CommonBLL.isValidStringType01(name)) { // Giả sử tên nguyên liệu giống định dạng họ tên
            return false;
        }
        return true;
    }

    // - Hàm kiểm tra đơn vị có hợp lệ hay không
    public boolean isValidUnit(String unit) {
        String[] validUnits = {"Gói", "Quả", "Hộp", "Thẻ", "Chai", "Lon", "Bó"};
        for (String validUnit : validUnits) {
            if (unit.equals(validUnit)) {
                return true;
            }
        }
        return false;
    }

    // - Hàm kiểm tra tồn kho có hợp lệ hay không
    public boolean isValidInventory(String inventory) {
        try {
            int value = Integer.parseInt(inventory);
            return value >= 0; // Tồn kho phải là số không âm
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // - Hàm kiểm tra trạng thái có hợp lệ hay không
    public boolean isValidStatus(String status) {
        if (!status.equals("Hoạt động") && !status.equals("Tạm dừng")) {
            return false;
        }
        return true;
    }

    // - Hàm kiểm tra mã nguyên liệu đã tồn tại hay chưa
    public boolean isExistsId(String id) {
        String[] join = null;
        String condition = String.format("maNguyenLieu = '%s'", id);
        String order = null;
        if (ingredientDAL.selectAllByCondition(join, condition, order).size() == 0) {
            return false;
        }
        return true;
    }

    // - Hàm kiểm tra tên nguyên liệu đã tồn tại hay chưa
    public boolean isExistsName(String name) {
        String[] join = null;
        String condition = String.format("tenNguyenLieu = '%s'", name);
        String order = null;
        if (ingredientDAL.selectAllByCondition(join, condition, order).size() == 0) {
            return false;
        }
        return true;
    }

    // - Hàm thêm một nguyên liệu
    public String insertIngredient(String id, String name, String unit, int inventory, boolean status, String dateUpdate) {
        // Kiểm tra các trường hợp
        if (!isInputedId(id) && !isInputedName(name) && !isInputedUnit(unit) && !isInputedInventory(String.valueOf(inventory)) && !isSelectedStatus(status ? "Hoạt động" : "Tạm dừng")) {
            return "Chưa nhập đầy đủ thông tin nguyên liệu cần thiết";
        }
        if (!isInputedId(id)) {
            return "Chưa nhập mã nguyên liệu";
        }
        if (!isInputedName(name)) {
            return "Chưa nhập tên nguyên liệu";
        }
        if (!isInputedUnit(unit)) {
            return "Chưa nhập đơn vị";
        }
        if (!isInputedInventory(String.valueOf(inventory))) {
            return "Chưa nhập tồn kho";
        }
        if (!isSelectedStatus(status ? "Hoạt động" : "Tạm dừng")) {
            return "Chưa chọn trạng thái";
        }
        if (!isValidId(id) && !isValidName(name) && !isValidUnit(unit) && !isValidInventory(String.valueOf(inventory))) {
            return "Nhập sai định dạng thông tin nguyên liệu";
        }
        if (!isValidId(id)) {
            return "Nhập sai định dạng mã nguyên liệu";
        }
        if (!isValidName(name)) {
            return "Nhập sai định dạng tên nguyên liệu";
        }
        if (!isValidUnit(unit)) {
            return "Nhập sai định dạng đơn vị";
        }
        if (!isValidInventory(String.valueOf(inventory))) {
            return "Nhập sai định dạng tồn kho";
        }
        if (isExistsId(id)) {
            return "Mã nguyên liệu đã tồn tại";
        }
        if (isExistsName(name)) {
            return "Tên nguyên liệu đã tồn tại";
        }

        // Nếu thỏa mãn hết thì thêm vào CSDL
        IngredientDTO newIngredient = new IngredientDTO(id, name, unit, inventory, status, dateUpdate);
        if (ingredientDAL.insert(newIngredient) > 0) {
            return "Có thể thêm một nguyên liệu";
        }
        return "Không thể thêm nguyên liệu do lỗi cơ sở dữ liệu";
    }

    // - Hàm cập nhật một nguyên liệu
    public String updateIngredient(String id, String name, String unit, int inventory, boolean status, String dateUpdate) {
        // Kiểm tra các trường hợp
        if (!isInputedName(name) && !isInputedUnit(unit) && !isInputedInventory(String.valueOf(inventory))) {
            return "Chưa nhập đầy đủ thông tin nguyên liệu cần thiết";
        }
        if (!isInputedName(name)) {
            return "Chưa nhập tên nguyên liệu";
        }
        if (!isInputedUnit(unit)) {
            return "Chưa nhập đơn vị";
        }
        if (!isInputedInventory(String.valueOf(inventory))) {
            return "Chưa nhập tồn kho";
        }
        if (!isValidName(name) && !isValidUnit(unit) && !isValidInventory(String.valueOf(inventory))) {
            return "Nhập sai định dạng thông tin nguyên liệu";
        }
        if (!isValidName(name)) {
            return "Nhập sai định dạng tên nguyên liệu";
        }
        if (!isValidUnit(unit)) {
            return "Nhập sai định dạng đơn vị";
        }
        if (!isValidInventory(String.valueOf(inventory))) {
            return "Nhập sai định dạng tồn kho";
        }
        IngredientDTO existingIngredient = getOneIngredientById(id);
        if (existingIngredient == null) {
            return "Không tìm thấy nguyên liệu để cập nhật";
        }
        if (!existingIngredient.getName().equals(name) && isExistsName(name)) {
            return "Tên nguyên liệu đã tồn tại";
        }

        // Nếu thỏa mãn hết thì cập nhật vào CSDL
        IngredientDTO updatedIngredient = new IngredientDTO(id, name, unit, inventory, status, dateUpdate);
        if (ingredientDAL.update(updatedIngredient) > 0) {
            return "Có thể thay đổi một nguyên liệu";
        }
        return "Không thể cập nhật nguyên liệu do lỗi cơ sở dữ liệu";
    }

    // - Hàm khóa/mở khóa một nguyên liệu
    public String lockIngredient(String id, String dateUpdate) {
        IngredientDTO existingIngredient = getOneIngredientById(id);
        if (existingIngredient == null) {
            return "Không tìm thấy nguyên liệu để khóa/mở khóa";
        }

        // Đổi trạng thái và cập nhật
        existingIngredient.setStatus(!existingIngredient.getStatus());
        existingIngredient.setDateUpdate(dateUpdate);
        if (ingredientDAL.update(existingIngredient) > 0) {
            return "Có thể thay đổi trạng thái nguyên liệu";
        }
        return "Không thể thay đổi trạng thái do lỗi cơ sở dữ liệu";
    }

    // - Hàm lấy tất cả nguyên liệu theo điều kiện
    public ArrayList<IngredientDTO> getAllIngredientsByCondition(String[] join, String condition, String order) {
        return ingredientDAL.selectAllByCondition(join, condition, order);
    }

    // - Hàm lấy nguyên liệu cuối cùng
    public IngredientDTO getLastIngredient() {
        ArrayList<IngredientDTO> ingredients = getAllIngredientsByCondition(null, null, "maNguyenLieu DESC");
        if (ingredients.isEmpty()) {
            return new IngredientDTO("NL0000", "", "", 0, true, "");
        }
        return ingredients.get(0);
    }

    // - Hàm lấy một nguyên liệu theo mã
    public IngredientDTO getOneIngredientById(String id) {
        String[] join = null;
        String condition = String.format("maNguyenLieu = '%s'", id);
        String order = null;
        ArrayList<IngredientDTO> result = ingredientDAL.selectAllByCondition(join, condition, order);
        if (result.isEmpty()) {
            return null;
        }
        return result.get(0);
    }
}