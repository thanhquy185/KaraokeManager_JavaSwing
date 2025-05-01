package BLL;

import java.io.File;
import java.util.ArrayList;

import DAL.FoodDAL;
import DTO.FoodDTO;

public class FoodBLL {
    // Properties
    private FoodDAL foodDAL;

    // Constructors
    public FoodBLL() {
        foodDAL = new FoodDAL();
    }

    // Methods
    // - Hàm kiểm tra mã món ăn đã được nhập hay chưa
    public boolean isInputId(String id) {
        return id != null;
    }

    // - Hàm kiểm tra tên món ăn đã được nhập hay chưa
    public boolean isInputName(String name) {
        return name != null;
    }

    // - Hàm kiểm tra loại món ăn đã được nhập hay chưa
    public boolean isInputCategory(String category) {
        return category != null;
    }

    // - Hàm kiểm tra giá bán đã được nhập hay chưa
    public boolean isInputPrice(String price) {
        return price != null;
    }

    // - Hàm kiểm tra trạng thái đã được chọn hay chưa
    public boolean isSelectedStatus(String status) {
        return status != null;
    }

    // - Hàm kiểm tra mã món ăn có hợp lệ không
    public boolean isValidId(String id) {
        if (!CommonBLL.isValidStringType03(id) || !id.matches("MA\\d{5}")) {
            return false;
        }
        return true;
    }

    // - Hàm kiểm tra tên món ăn có hợp lệ không
    public boolean isValidName(String name) {
        if (name != null && !CommonBLL.isValidStringType01(name)) {
            return false;
        }
        return true;
    }

    // - Hàm kiểm tra trạng thái có hợp lệ không
    public boolean isValidStatus(String status) {
        return status.equals("Hoạt động") || status.equals("Tạm dừng");
    }

    // - Hàm kiểm tra mã món ăn đã tồn tại chưa
    public boolean isExistsId(String id) {
        String[] join = null;
        String condition = String.format("maMonAn = '%s'", id);
        String order = null;
        return !foodDAL.selectAllByCondition(join, condition, order).isEmpty();
    }

    // - Hàm thêm một món ăn
    public String insertFood(String id, String name, String category, String priceStr,
            File fileImage, String statusStr, String timeUpdate) {
        // Kiểm tra các trường hợp
        if (!isInputId(id) && !isInputName(name) && !isInputCategory(category) && !isInputPrice(priceStr)
                && !isSelectedStatus(statusStr)) {
            return "Chưa nhập đầy đủ thông tin món ăn cần thiết";
        }
        if (!isInputId(id)) {
            return "Chưa nhập mã món ăn";
        }
        if (!isInputName(name)) {
            return "Chưa nhập tên món ăn";
        }
        if (!isInputCategory(category)) {
            return "Chưa nhập loại món ăn";
        }
        if (!isInputPrice(priceStr)) {
            return "Chưa nhập giá bán";
        }
        if (!isSelectedStatus(statusStr)) {
            return "Chưa chọn trạng thái";
        }
        if (!isValidId(id) || !isValidName(name) || !CommonBLL.isValidStringType04(priceStr)) {
            return "Nhập sai định dạng thông tin món ăn";
        }
        if (!isValidId(id)) {
            return "Nhập sai định dạng mã món ăn (MAxxxxx)";
        }
        if (!isValidName(name)) {
            return "Nhập sai định dạng tên món ăn";
        }
        if (!CommonBLL.isValidStringType04(priceStr)) {
            return "Nhập sai định dạng giá bán";
        }
        if (isExistsId(id)) {
            return "Mã món ăn đã tồn tại";
        }

        // Cập nhật ảnh vào source code nếu có tồn tại
        String image = null;
        if (fileImage != null && fileImage.exists()) {
            image = CommonBLL.updateFoodImage(fileImage, id);
        }

        // Nếu thỏa mãn, thêm vào CSDL
        Long price = Long.parseLong(priceStr);
        Boolean status = statusStr.equals("Hoạt động");
        FoodDTO newfood = new FoodDTO(id, name, category, 0L, price, image, status, timeUpdate);
        foodDAL.insert(newfood);

        return "Có thể thêm một món ăn";
    }

    // - Hàm cập nhật một món ăn
    public String updateFood(String id, String name, String category, String priceStr, File fileImage,
            String timeUpdate) {
        // Kiểm tra các trường hợp
        if (!isInputId(id) && !isInputName(name) && !isInputCategory(category) && !isInputPrice(priceStr)) {
            return "Chưa nhập đầy đủ thông tin món ăn cần thiết";
        }
        if (!isInputId(id)) {
            return "Chưa nhập mã món ăn";
        }
        if (!isInputName(name)) {
            return "Chưa nhập tên món ăn";
        }
        if (!isInputCategory(category)) {
            return "Chưa chọn loại món ăn";
        }
        if (!isInputPrice(priceStr)) {
            return "Chưa nhập giá bán";
        }
        if (!isValidId(id) || !isValidName(name) || !CommonBLL.isValidStringType04(priceStr)) {
            return "Nhập sai định dạng thông tin món ăn";
        }
        if (!isValidId(id)) {
            return "Nhập sai định dạng mã món ăn (MAxxxxx)";
        }
        if (!isValidName(name)) {
            return "Nhập sai định dạng tên món ăn";
        }
        if (!CommonBLL.isValidStringType04(priceStr)) {
            return "Nhập sai định dạng giá bán";
        }

        // Cập nhật ảnh vào source code nếu có tồn tại
        String image = getOneFoodById(id).getImage();
        if (fileImage != null && fileImage.exists()) {
            image = CommonBLL.updateFoodImage(fileImage, id);
        }

        // Nếu thỏa mãn, cập nhật vào CSDL
        Long price = Long.parseLong(priceStr);
        FoodDTO updatedfood = new FoodDTO(id, name, category, null, price, image, null, timeUpdate);
        foodDAL.update(updatedfood);

        return "Có thể thay đổi một món ăn";
    }

    // - Hàm cập nhật tồn kho một món ăn
    public String updateInventoryFood(String action, String id, Long inventory) {
        // Đối tượng chứa món ăn cần cập nhật
        FoodDTO updatedfood = getOneFoodById(id);

        // Kiểm tra hành động và tồn kho hiện tại mà cập nhật trạng thái tương ứng
        Long inventoryCurrent = updatedfood.getInventory();
        if (action.equals("create")) {
            updatedfood.setInventory(inventoryCurrent + inventory);
            updatedfood.setStatus(true);
        } else if (action.equals("delete")) {

        }
        foodDAL.updateInventory(updatedfood);

        return "Có thể thay đổi tồn kho  một món ăn";
    }

    // - Hàm khóa/mở khóa một món ăn
    public String lockFood(String id, String timeUpdate) {
        FoodDTO food = getOneFoodById(id);
        if (food == null) {
            return "Món ăn không tồn tại";
        }
        food.setStatus(!food.getStatus());
        food.setTimeUpdate(timeUpdate);
        foodDAL.lock(food);

        return "Có thể thay đổi trạng thái món ăn";
    }

    // - Hàm lấy tất cả món ăn
    public ArrayList<FoodDTO> getAllFood() {
        return foodDAL.selectAll();
    }

    // - Hàm lấy tất cả món ăn theo điều kiện
    public ArrayList<FoodDTO> getAllFoodByCondition(String[] join, String condition, String order) {
        return foodDAL.selectAllByCondition(join, condition, order);
    }

    // - Hàm lấy một món ăn theo mã
    public FoodDTO getOneFoodById(String id) {
        String[] join = null;
        String condition = String.format("maMonAn = '%s'", id);
        String order = null;
        ArrayList<FoodDTO> result = foodDAL.selectAllByCondition(join, condition, order);
        return result.isEmpty() ? null : result.get(0);
    }

    // - Hàm lấy món ăn cuối cùng
    public FoodDTO getLastfood() {
        ArrayList<FoodDTO> foods = foodDAL.selectAllByCondition(null, null, "maMonAn DESC");
        return foods.isEmpty() ? new FoodDTO("NL00000", "", "", 0L, 0L, "", true, "") : foods.get(0);
    }

    // - Hàm lấy thông tin món ăn dạng "id - name" theo mã
    public String getElementById(String id) {
        FoodDTO food = getOneFoodById(id);
        if (food == null) {
            return id + " - Không xác định";
        }
        return food.getId() + " - " + food.getName();
    }
}