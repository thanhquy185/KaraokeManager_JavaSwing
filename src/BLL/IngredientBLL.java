package BLL;

import java.util.ArrayList;

import DAL.IngredientDAL;
import DTO.IngredientDTO;

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
        return id != null;
    }

    // - Hàm kiểm tra tên nguyên liệu đã được nhập hay chưa
    public boolean isInputedName(String name) {
        return name != null;
    }

    // - Hàm kiểm tra đơn vị đã được nhập hay chưa
    public boolean isInputedUnit(String unit) {
        return unit != null;
    }

    // - Hàm kiểm tra tồn kho đã được nhập hay chưa
    public boolean isInputedInventory(String inventory) {
        return inventory != null;
    }

    // - Hàm kiểm tra trạng thái đã được chọn hay chưa
    public boolean isSelectedStatus(String status) {
        return status != null;
    }

    // - Hàm kiểm tra mã nguyên liệu có hợp lệ không
    public boolean isValidId(String id) {
        if (!CommonBLL.isValidStringType03(id) || !id.matches("NL\\d{5}")) {
            return false;
        }
        return true;
    }

    // - Hàm kiểm tra tên nguyên liệu có hợp lệ không
    public boolean isValidName(String name) {
        if (name != null && !CommonBLL.isValidStringType01(name)) {
            return false;
        }
        return true;
    }

    // - Hàm kiểm tra đơn vị có hợp lệ không
    public boolean isValidUnit(String unit) {
        String[] validUnits = {"Gói", "Quả", "Hộp", "Thẻ", "Chai", "Lon", "Bó"};
        if (unit == null) return false;
        for (String validUnit : validUnits) {
            if (unit.equals(validUnit)) {
                return true;
            }
        }
        return false;
    }

    // - Hàm kiểm tra tồn kho có hợp lệ không
    public boolean isValidInventory(String inventory) {
        try {
            int value = Integer.parseInt(inventory);
            return value >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // - Hàm kiểm tra trạng thái có hợp lệ không
    public boolean isValidStatus(String status) {
        return status.equals("Hoạt động") || status.equals("Tạm dừng");
    }

    // - Hàm kiểm tra mã nguyên liệu đã tồn tại chưa
    public boolean isExistsId(String id) {
        String[] join = null;
        String condition = String.format("maNguyenLieu = '%s'", id);
        String order = null;
        return !ingredientDAL.selectAllByCondition(join, condition, order).isEmpty();
    }

    // - Hàm kiểm tra tên nguyên liệu đã tồn tại chưa
    public boolean isExistsName(String name) {
        String[] join = null;
        String condition = String.format("tenNguyenLieu = '%s'", name);
        String order = null;
        return !ingredientDAL.selectAllByCondition(join, condition, order).isEmpty();
    }

    // - Hàm thêm một nguyên liệu
    public String insertIngredient(String id, String name, String unit, String inventoryStr, String status, String dateUpdate) {
        // Kiểm tra các trường hợp
        if (!isInputedId(id) && !isInputedName(name) && !isInputedUnit(unit) && !isInputedInventory(inventoryStr) && !isSelectedStatus(status)) {
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
        if (!isInputedInventory(inventoryStr)) {
            return "Chưa nhập tồn kho";
        }
        if (!isSelectedStatus(status)) {
            return "Chưa chọn trạng thái";
        }
        if (!isValidId(id) || !isValidName(name) || !isValidUnit(unit) || !isValidInventory(inventoryStr) || !isValidStatus(status)) {
            return "Nhập sai định dạng thông tin nguyên liệu";
        }
        if (!isValidId(id)) {
            return "Nhập sai định dạng mã nguyên liệu (NLxxxxx)";
        }
        if (!isValidName(name)) {
            return "Nhập sai định dạng tên nguyên liệu";
        }
        if (!isValidUnit(unit)) {
            return "Nhập sai định dạng đơn vị";
        }
        if (!isValidInventory(inventoryStr)) {
            return "Nhập sai định dạng tồn kho (phải là số không âm)";
        }
        if (!isValidStatus(status)) {
            return "Chọn sai định dạng trạng thái";
        }
        if (isExistsId(id) || isExistsName(name)) {
            return "Thông tin nguyên liệu đã tồn tại";
        }
        if (isExistsId(id)) {
            return "Mã nguyên liệu đã tồn tại";
        }
        if (isExistsName(name)) {
            return "Tên nguyên liệu đã tồn tại";
        }

        // Nếu thỏa mãn, thêm vào CSDL
        int inventory = Integer.parseInt(inventoryStr);
        boolean ingredientStatus = status.equals("Hoạt động");
        IngredientDTO newIngredient = new IngredientDTO(id, name, unit, inventory, ingredientStatus, dateUpdate);
        ingredientDAL.insert(newIngredient);

        return "Có thể thêm một nguyên liệu";
    }

    // - Hàm cập nhật một nguyên liệu
    public String updateIngredient(String id, String name, String unit, String inventoryStr, String status, String dateUpdate) {
        // Kiểm tra các trường hợp
        if (!isInputedName(name) || !isInputedUnit(unit) || !isInputedInventory(inventoryStr)) {
            return "Chưa nhập đầy đủ thông tin nguyên liệu cần thiết";
        }
        if (!isInputedName(name)) {
            return "Chưa nhập tên nguyên liệu";
        }
        if (!isInputedUnit(unit)) {
            return "Chưa nhập đơn vị";
        }
        if (!isInputedInventory(inventoryStr)) {
            return "Chưa nhập tồn kho";
        }
        if (!isValidName(name) || !isValidUnit(unit) || !isValidInventory(inventoryStr)) {
            return "Nhập sai định dạng thông tin nguyên liệu";
        }
        if (!isValidName(name)) {
            return "Nhập sai định dạng tên nguyên liệu";
        }
        if (!isValidUnit(unit)) {
            return "Nhập sai định dạng đơn vị";
        }
        if (!isValidInventory(inventoryStr)) {
            return "Nhập sai định dạng tồn kho (phải là số không âm)";
        }
        IngredientDTO existingIngredient = getOneIngredientById(id);
        if (existingIngredient == null) {
            return "Nguyên liệu không tồn tại";
        }
        if (!existingIngredient.getName().equals(name) && isExistsName(name)) {
            return "Tên nguyên liệu đã tồn tại";
        }

        // Nếu thỏa mãn, cập nhật vào CSDL
        int inventory = Integer.parseInt(inventoryStr);
        boolean ingredientStatus = status.equals("Hoạt động");
        IngredientDTO updatedIngredient = new IngredientDTO(id, name, unit, inventory, ingredientStatus, dateUpdate);
        ingredientDAL.update(updatedIngredient);

        return "Có thể thay đổi một nguyên liệu";
    }

    // - Hàm khóa/mở khóa một nguyên liệu
    public String lockIngredient(String id, String dateUpdate) {
        IngredientDTO ingredient = getOneIngredientById(id);
        if (ingredient == null) {
            return "Nguyên liệu không tồn tại";
        }
        ingredient.setStatus(!ingredient.getStatus());
        ingredient.setDateUpdate(dateUpdate);
        ingredientDAL.update(ingredient);

        return "Có thể thay đổi trạng thái nguyên liệu";
    }

    // - Hàm lấy tất cả nguyên liệu
    public ArrayList<IngredientDTO> getAllIngredients() {
        return ingredientDAL.selectAll();
    }

    // - Hàm lấy tất cả nguyên liệu theo điều kiện
    public ArrayList<IngredientDTO> getAllIngredientsByCondition(String[] join, String condition, String order) {
        return ingredientDAL.selectAllByCondition(join, condition, order);
    }

    // - Hàm lấy một nguyên liệu theo mã
    public IngredientDTO getOneIngredientById(String id) {
        String[] join = null;
        String condition = String.format("maNguyenLieu = '%s'", id);
        String order = null;
        ArrayList<IngredientDTO> result = ingredientDAL.selectAllByCondition(join, condition, order);
        return result.isEmpty() ? null : result.get(0);
    }

    // - Hàm lấy nguyên liệu cuối cùng
    public IngredientDTO getLastIngredient() {
        ArrayList<IngredientDTO> ingredients = ingredientDAL.selectAllByCondition(null, null, "maNguyenLieu DESC");
        return ingredients.isEmpty() ? new IngredientDTO("NL00000", "", "", 0, true, "") : ingredients.get(0);
    }
 // - Hàm lấy thông tin nguyên liệu dạng "id - name" theo mã
    public String getElementById(String id) {
        IngredientDTO ingredient = getOneIngredientById(id);
        if (ingredient == null) {
            return id + " - Không xác định";
        }
        return ingredient.getId() + " - " + ingredient.getName();
    }
}