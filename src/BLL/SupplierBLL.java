package BLL;

import java.util.ArrayList;

import DAL.SupplierDAL;
import DTO.SupplierDTO;

public class SupplierBLL {
    private SupplierDAL supplierDAL;

    public SupplierBLL() {
        supplierDAL = new SupplierDAL();
    }

    // Kiểm tra dữ liệu đầu vào
    private boolean isInputed(String value) {
        return value != null && !value.trim().isEmpty();
    }

    private boolean isValidId(String id) {
        return id != null && id.matches("NCC\\d{2}");
    }

    private boolean isValidPhone(String phone) {
        return phone != null && phone.matches("\\d{10}");
    }

    private boolean isValidEmail(String email) {
        return email != null && email.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }

    private boolean isValidStatus(String status) {
        return status != null && (status.equals("Hoạt động") || status.equals("Tạm dừng"));
    }

    private boolean isExistsId(String id) {
        String[] join = null;
        String condition = String.format("maNCC = '%s'", id);
        String order = null;
        return !supplierDAL.selectAllByCondition(join, condition, order).isEmpty();
    }

    // Thêm nhà cung cấp
    public String insertSupplier(String id, String name, String phone, String email, String address, String status, String dateUpdate) {
        // Kiểm tra đầu vào
        if (!isInputed(id)) return "Chưa nhập mã nhà cung cấp";
        if (!isInputed(name)) return "Chưa nhập tên nhà cung cấp";
        if (!isInputed(phone)) return "Chưa nhập số điện thoại";
        if (!isInputed(email)) return "Chưa nhập email";
        if (!isInputed(address)) return "Chưa nhập địa chỉ";
        if (!isInputed(status)) return "Chưa chọn trạng thái";

        // Kiểm tra định dạng
        if (!isValidId(id)) return "Mã nhà cung cấp phải có định dạng NCCxx (x là số)";
        if (!isValidPhone(phone)) return "Số điện thoại phải có 10 chữ số";
        if (!isValidEmail(email)) return "Email không đúng định dạng";
        if (!isValidStatus(status)) return "Trạng thái không hợp lệ";

        // Kiểm tra trùng lặp
        if (isExistsId(id)) return "Mã nhà cung cấp đã tồn tại";

        // Thêm vào database
        boolean supplierStatus = status.equals("Hoạt động");
        SupplierDTO newSupplier = new SupplierDTO(id, name, phone, email, address, supplierStatus, dateUpdate);
        int rowsAffected = supplierDAL.insert(newSupplier);
        return rowsAffected > 0 ? "Thêm nhà cung cấp thành công" : "Không thể thêm nhà cung cấp vào cơ sở dữ liệu";
    }

    // Cập nhật nhà cung cấp
    public String updateSupplier(String id, String name, String phone, String email, String address, String status, String dateUpdate) {
        // Kiểm tra đầu vào
        if (!isInputed(name)) return "Chưa nhập tên nhà cung cấp";
        if (!isInputed(phone)) return "Chưa nhập số điện thoại";
        if (!isInputed(email)) return "Chưa nhập email";
        if (!isInputed(address)) return "Chưa nhập địa chỉ";

        // Kiểm tra định dạng
        if (!isValidPhone(phone)) return "Số điện thoại phải có 10 chữ số";
        if (!isValidEmail(email)) return "Email không đúng định dạng";

        // Kiểm tra tồn tại
        SupplierDTO existingSupplier = supplierDAL.selectOneById(id);
        if (existingSupplier == null) return "Nhà cung cấp không tồn tại";

        // Cập nhật
        boolean supplierStatus = existingSupplier.getStatus(); // Giữ nguyên trạng thái khi cập nhật
        SupplierDTO updatedSupplier = new SupplierDTO(id, name, phone, email, address, supplierStatus, dateUpdate);
        int rowsAffected = supplierDAL.update(updatedSupplier);
        return rowsAffected > 0 ? "Cập nhật nhà cung cấp thành công" : "Không thể cập nhật nhà cung cấp";
    }

    // Khóa/Mở khóa nhà cung cấp
    public String lockSupplier(String id, String dateUpdate) {
        SupplierDTO supplier = supplierDAL.selectOneById(id);
        if (supplier == null) return "Nhà cung cấp không tồn tại";

        // Đảo ngược trạng thái trước khi cập nhật
        supplier.setStatus(!supplier.getStatus());
        supplier.setDateUpdate(dateUpdate);
        int rowsAffected = supplierDAL.lock(supplier);
        return rowsAffected > 0 ? "Thay đổi trạng thái thành công" : "Không thể thay đổi trạng thái nhà cung cấp";
    }

    // Lấy tất cả nhà cung cấp theo điều kiện
    public ArrayList<SupplierDTO> getAllSuppliersByCondition(String[] join, String condition, String order) {
        return supplierDAL.selectAllByCondition(join, condition, order);
    }

    // Lấy nhà cung cấp cuối cùng
    public SupplierDTO getLastSupplier() {
        ArrayList<SupplierDTO> suppliers = supplierDAL.selectAllByCondition(null, null, "maNCC DESC");
        return suppliers.isEmpty() ? new SupplierDTO("NCC00", "", "", "", "", true, "") : suppliers.get(0);
    }

    // Lấy tất cả nhà cung cấp (không điều kiện)
    public ArrayList<SupplierDTO> getAllSuppliers() {
        return supplierDAL.selectAll();
    }

    // Lấy tên nhà cung cấp theo mã
    public String getSupplierNameById(String supplierId) {
        SupplierDTO supplier = supplierDAL.selectOneById(supplierId);
        return supplier != null ? supplier.getName() : null;
    }
}