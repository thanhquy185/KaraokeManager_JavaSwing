package BLL;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
        return list.isEmpty() ? new InputTicketDTO(0, null, null, 0L, 0, null, 0) : list.get(0);
    }

    public String insertInputTicket(Integer id, String dateCreate, String supplierId, Long cost, Integer status, 
                                   String dateUpdate, Integer employeeId) {
        if (id == null || dateCreate == null || supplierId == null || cost == null || status == null || 
            dateUpdate == null || employeeId == null) {
            return "Thông tin phiếu nhập không đầy đủ";
        }

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date createDate = sdf.parse(dateCreate);
            Date updateDate = sdf.parse(dateUpdate);
            if (createDate.after(updateDate)) {
                return "Ngày lập phiếu phải trước hoặc cùng ngày với ngày cập nhật";
            }
        } catch (ParseException e) {
            return "Định dạng ngày không hợp lệ";
        }

        InputTicketDTO dto = new InputTicketDTO(id, dateCreate, supplierId, cost, status, dateUpdate, employeeId);
        int result = inputTicketDAL.insert(dto);
        return result > 0 ? "Thêm phiếu nhập thành công" : "Thêm phiếu nhập thất bại";
    }

    public String updateInputTicket(Integer id, String dateCreate, String supplierId, Long cost, Integer status, 
                                   String dateUpdate, Integer employeeId) {
        if (id == null || dateCreate == null || supplierId == null || cost == null || status == null || 
            dateUpdate == null || employeeId == null) {
            return "Thông tin phiếu nhập không đầy đủ";
        }

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date createDate = sdf.parse(dateCreate);
            Date updateDate = sdf.parse(dateUpdate);
            if (createDate.after(updateDate)) {
                return "Ngày lập phiếu phải trước hoặc cùng ngày với ngày cập nhật";
            }
        } catch (ParseException e) {
            return "Định dạng ngày không hợp lệ";
        }

        InputTicketDTO dto = new InputTicketDTO(id, dateCreate, supplierId, cost, status, dateUpdate, employeeId);
        int result = inputTicketDAL.update(dto);
        return result > 0 ? "Cập nhật phiếu nhập thành công" : "Cập nhật phiếu nhập thất bại";
    }

    public String lockInputTicket(Integer id, String dateUpdate) {
        if (id == null || dateUpdate == null) {
            return "Thông tin không đầy đủ để thay đổi trạng thái";
        }

        InputTicketDTO dto = getOneInputTicketById(String.valueOf(id));
        if (dto == null) return "Phiếu nhập không tồn tại";

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date createDate = sdf.parse(dto.getDateCreate());
            Date updateDate = sdf.parse(dateUpdate);
            if (createDate.after(updateDate)) {
                return "Ngày lập phiếu phải trước hoặc cùng ngày với ngày cập nhật";
            }
        } catch (ParseException e) {
            return "Định dạng ngày không hợp lệ";
        }

        dto.setStatus(2); // Trạng thái Hủy = 2
        dto.setDateUpdate(dateUpdate);
        int result = inputTicketDAL.lock(dto);
        return result > 0 ? "Thay đổi trạng thái phiếu nhập thành công" : "Thay đổi trạng thái thất bại";
    }
}