package BLL;

import java.util.ArrayList;
import DAL.IngredientDAL;
import DTO.IngredientDTO;

public class IngredientBLL {
    private IngredientDAL ingredientDAL;

    public IngredientBLL() {
        ingredientDAL = new IngredientDAL();
    }

    public ArrayList<IngredientDTO> getAllIngredients() {
        return ingredientDAL.selectAll();
    }

    public ArrayList<IngredientDTO> getAllIngredientsByCondition(String[] join, String condition, String order) {
        return ingredientDAL.selectAllByCondition(join, condition, order);
    }

    public IngredientDTO getOneIngredientById(String id) {
        return ingredientDAL.selectOneById(id);
    }

    public String insertIngredient(String id, String name, String unit, int inventory, boolean status, String dateUpdate) {
        if (getOneIngredientById(id) != null) {
            return "Mã nguyên liệu đã tồn tại";
        }
        IngredientDTO newIngredient = new IngredientDTO(id, name, unit, inventory, status, dateUpdate);
        if (ingredientDAL.insert(newIngredient) > 0) {
            return "Có thể thêm một nguyên liệu";
        }
        return "Không thể thêm nguyên liệu do lỗi cơ sở dữ liệu";
    }

    public String updateIngredient(String id, String name, String unit, int inventory, boolean status, String dateUpdate) {
        IngredientDTO existingIngredient = getOneIngredientById(id);
        if (existingIngredient == null) {
            return "Không tìm thấy nguyên liệu để cập nhật";
        }
        IngredientDTO updatedIngredient = new IngredientDTO(id, name, unit, inventory, status, dateUpdate);
        if (ingredientDAL.update(updatedIngredient) > 0) {
            return "Có thể thay đổi một nguyên liệu";
        }
        return "Không thể cập nhật nguyên liệu do lỗi cơ sở dữ liệu";
    }

    public String lockIngredient(String id, String dateUpdate) {
        IngredientDTO existingIngredient = getOneIngredientById(id);
        if (existingIngredient == null) {
            return "Không tìm thấy nguyên liệu để khóa/mở khóa";
        }
        existingIngredient.setStatus(!existingIngredient.getStatus());
        existingIngredient.setDateUpdate(dateUpdate); // Nhất quán với setDateUpdate()
        if (ingredientDAL.update(existingIngredient) > 0) {
            return "Có thể thay đổi trạng thái nguyên liệu";
        }
        return "Không thể thay đổi trạng thái do lỗi cơ sở dữ liệu";
    }

    public IngredientDTO getLastIngredient() {
        ArrayList<IngredientDTO> ingredients = getAllIngredientsByCondition(null, null, "maNguyenLieu DESC");
        if (ingredients.isEmpty()) {
            return new IngredientDTO("NL0000", "", "", 0, true, "");
        }
        return ingredients.get(0);
    }
}