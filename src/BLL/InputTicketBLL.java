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
        return list.isEmpty() ? new InputTicketDTO(0, null, null, 0L, false, null, 0, false) : list.get(0);
    }

    public String insertInputTicket(Integer id, String dateCreate, String supplierId, Long cost, Boolean status, 
                                   String dateUpdate, Integer employeeId, Boolean isCancelled) {
        // Kiểm tra thông tin đầu vào đầy đủ
        if (id == null || dateCreate == null || supplierId == null || cost == null || status == null || 
            dateUpdate == null || employeeId == null || isCancelled == null) {
            return "Thông tin phiếu nhập không đầy đủ";
        }

        // Kiểm tra ngày lập phiếu phải trước hoặc cùng ngày với ngày cập nhật
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // Định dạng ngày phù hợp
            Date createDate = sdf.parse(dateCreate);
            Date updateDate = sdf.parse(dateUpdate);
            if (createDate.after(updateDate)) {
                return "Ngày lập phiếu phải trước hoặc cùng ngày với ngày cập nhật";
            }
        } catch (ParseException e) {
            return "Định dạng ngày không hợp lệ";
        }

        // Tạo DTO và thực hiện chèn dữ liệu
        InputTicketDTO dto = new InputTicketDTO(id, dateCreate, supplierId, cost, status, dateUpdate, employeeId, isCancelled);
        int result = inputTicketDAL.insert(dto);
        return result > 0 ? "Thêm phiếu nhập thành công" : "Thêm phiếu nhập thất bại";
    }

    public String updateInputTicket(Integer id, String dateCreate, String supplierId, Long cost, Boolean status, 
                                   String dateUpdate, Integer employeeId, Boolean isCancelled) {
        // Kiểm tra thông tin đầu vào đầy đủ
        if (id == null || dateCreate == null || supplierId == null || cost == null || status == null || 
            dateUpdate == null || employeeId == null || isCancelled == null) {
            return "Thông tin phiếu nhập không đầy đủ";
        }

        // Kiểm tra ngày lập phiếu phải trước hoặc cùng ngày với ngày cập nhật
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // Định dạng ngày phù hợp
            Date createDate = sdf.parse(dateCreate);
            Date updateDate = sdf.parse(dateUpdate);
            if (createDate.after(updateDate)) {
                return "Ngày lập phiếu phải trước hoặc cùng ngày với ngày cập nhật";
            }
        } catch (ParseException e) {
            return "Định dạng ngày không hợp lệ";
        }

        // Tạo DTO và thực hiện cập nhật dữ liệu
        InputTicketDTO dto = new InputTicketDTO(id, dateCreate, supplierId, cost, status, dateUpdate, employeeId, isCancelled);
        int result = inputTicketDAL.update(dto);
        return result > 0 ? "Cập nhật phiếu nhập thành công" : "Cập nhật phiếu nhập thất bại";
    }

    public String lockInputTicket(Integer id, String dateUpdate) {
        // Kiểm tra thông tin đầu vào
        if (id == null || dateUpdate == null) {
            return "Thông tin không đầy đủ để thay đổi trạng thái";
        }

        // Lấy thông tin phiếu nhập hiện tại
        InputTicketDTO dto = getOneInputTicketById(String.valueOf(id));
        if (dto == null) return "Phiếu nhập không tồn tại";

        // Kiểm tra ngày lập phiếu phải trước hoặc cùng ngày với ngày cập nhật
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // Định dạng ngày phù hợp
            Date createDate = sdf.parse(dto.getDateCreate());
            Date updateDate = sdf.parse(dateUpdate);
            if (createDate.after(updateDate)) {
                return "Ngày lập phiếu phải trước hoặc cùng ngày với ngày cập nhật";
            }
        } catch (ParseException e) {
            return "Định dạng ngày không hợp lệ";
        }

        // Cập nhật trạng thái khóa phiếu
        dto.setStatus(false);
        dto.setDateUpdate(dateUpdate);
        dto.setIsCancelled(true);
        int result = inputTicketDAL.lock(dto);
        return result > 0 ? "Thay đổi trạng thái phiếu nhập thành công" : "Thay đổi trạng thái thất bại";
    }
}