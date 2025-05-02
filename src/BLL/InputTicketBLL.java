package BLL;

import java.util.ArrayList;
import java.util.Vector;

import DAL.InputTicketDAL;
import DTO.InputTicketDTO;
import DTO.InputTicketDetailDTO;
import PL.CommonPL;

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
            Long totalPrice, Integer status, Vector<InputTicketDetailDTO> inputTicketDetails) {
        if (id == null && timeCreate == null && employee == null && supplier == null && totalPrice == null
                && status == null && inputTicketDetails.size() == 0) {
            return "Thông tin phiếu nhập không đầy đủ";
        }
        if (supplier == null)
            return "Chưa chọn nhà cung cấp";
        if (inputTicketDetails.size() == 0)
            return "Chưa nhập món ăn nào";

        InputTicketDTO dto = new InputTicketDTO(id, timeCreate, employee, supplier, totalPrice, status);
        int inform = inputTicketDAL.insert(dto);

        InputTicketDetailBLL inputTicketDetailBLL = new InputTicketDetailBLL();
        for (InputTicketDetailDTO inputTicketDetail : inputTicketDetails) {
            String detailInform = inputTicketDetailBLL.insertInputTicketDetail(inputTicketDetail.getInputTicketId(),
                    inputTicketDetail.getFoodId(), inputTicketDetail.getPrice(), inputTicketDetail.getQuantity());
            if (!detailInform.equals("Thêm chi tiết phiếu nhập thành công")) {
                return detailInform;
            }
        }

        return inform > 0 ? "Thêm phiếu nhập thành công" : "Thêm phiếu nhập thất bại";
    }

    public String updateStatusInputTicket(Integer inputTicketId, Integer status,
            Vector<InputTicketDetailDTO> inputTicketDetails) {
        if (inputTicketDetails.size() > 0 && status == 2) {
            FoodBLL foodBLL = new FoodBLL();
            for (InputTicketDetailDTO inputTicketDetail : inputTicketDetails) {
                foodBLL.updateInventoryFood("create", inputTicketDetail.getFoodId(), inputTicketDetail.getQuantity());
            }
        }

        InputTicketDTO inputTicketUpdate = new InputTicketDTO(inputTicketId, null, null, null, null, status);
        int result = inputTicketDAL.updateStatus(inputTicketUpdate);

        return result > 0 ? "Thay đổi trạng thái phiếu nhập thành công" : "Thay đổi trạng thái phiếu nhập thất bại";
    }
}