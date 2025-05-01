package BLL;

import java.util.ArrayList;

import DAL.InputTicketDAL;
import DTO.InputTicketDTO;

public class InputTicketBLL {
    private InputTicketDAL inputTicketDAL;

    public InputTicketBLL() {
        inputTicketDAL = new InputTicketDAL();
    }

    public ArrayList<InputTicketDTO> getAllInputTicket() {
        return inputTicketDAL.selectAll();
    }

    public ArrayList<InputTicketDTO> getAllInputTicketByCondition(String[] join, String condition, String order) {
        return inputTicketDAL.selectAllByCondition(join, condition, order);
    }

    public InputTicketDTO getOneInputTicketById(String id) {
        return inputTicketDAL.selectOneById(id);
    }

    public InputTicketDTO getLastInputTicket() {
        ArrayList<InputTicketDTO> list = inputTicketDAL.selectAllByCondition(null, null, "maPhieuNhap DESC");
        return list.isEmpty() ? new InputTicketDTO(0, null, 0, null, 0L, 0) : list.get(0);
    }

    public String insertInputTicket(Integer id, String timeCreate, Integer employee, String supplier,
            Long totalPrice, Integer status, long totalDetails) {
        if (id == null && timeCreate == null && employee == null && supplier == null && totalPrice == null
                && status == null && totalDetails == 0) {
            return "Thông tin phiếu nhập không đầy đủ";
        }
        if (supplier == null)
            return "Chưa chọn nhà cung cấp";
        if (totalDetails == 0)
            return "Chưa nhập món ăn nào";

        InputTicketDTO dto = new InputTicketDTO(id, timeCreate, employee, supplier, totalPrice, status);
        int result = inputTicketDAL.insert(dto);
        return result > 0 ? "Thêm phiếu nhập thành công" : "Thêm phiếu nhập thất bại";
    }

    // public String updateInputTicket(Integer id, String timeCreate, Integer
    // employeeId, String supplierId,
    // Long totalPrice, Integer status) {
    // if (id == null || timeCreate == null || supplierId == null || totalPrice ==
    // null || status == null
    // || employeeId == null) {
    // return "Thông tin phiếu nhập không đầy đủ";
    // }

    // InputTicketDTO dto = new InputTicketDTO(id, timeCreate, employeeId,
    // supplierId, totalPrice, status);
    // int result = inputTicketDAL.update(dto);
    // return result > 0 ? "Cập nhật phiếu nhập thành công" : "Cập nhật phiếu nhập
    // thất bại";
    // }

    public String updateStatusInputTicket(String id, int status) {
        InputTicketDTO inputTicketUpdate = getOneInputTicketById(id);
        inputTicketUpdate.setStatus(status);
        int result = inputTicketDAL.updateStatus(inputTicketUpdate);

        return result > 0 ? "Thay đổi trạng thái phiếu nhập thành công" : "Thay đổi trạng thái phiếu nhập thất bại";
    }
}