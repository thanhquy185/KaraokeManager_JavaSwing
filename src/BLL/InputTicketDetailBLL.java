package BLL;

import java.util.ArrayList;

import DAL.InputTicketDetailDAL;
import DTO.InputTicketDetailDTO;

public class InputTicketDetailBLL {
    private InputTicketDetailDAL inputTicketDetailDAL;

    public InputTicketDetailBLL() {
        inputTicketDetailDAL = new InputTicketDetailDAL();
    }

    public ArrayList<InputTicketDetailDTO> getAllInputTicketDetail() {
        return inputTicketDetailDAL.selectAll();
    }

    public ArrayList<InputTicketDetailDTO> getAllInputTicketDetailByCondition(String[] join, String condition, String order) {
        return inputTicketDetailDAL.selectAllByCondition(join, condition, order);
    }

    public InputTicketDetailDTO getOneInputTicketDetailById(String id) {
        return inputTicketDetailDAL.selectOneById(id);
    }

    public String insertInputTicketDetail(Integer id, String ingredientId, Long inputPrice, Integer inputQuantity) {
        if (id == null || ingredientId == null || inputPrice == null || inputQuantity == null) {
            return "Thông tin chi tiết phiếu nhập không đầy đủ";
        }
        InputTicketDetailDTO dto = new InputTicketDetailDTO(id, ingredientId, inputPrice, inputQuantity);
        int result = inputTicketDetailDAL.insert(dto);
        return result > 0 ? "Thêm chi tiết phiếu nhập thành công" : "Thêm chi tiết phiếu nhập thất bại";
    }

    public String updateInputTicketDetail(Integer id, String ingredientId, Long inputPrice, Integer inputQuantity) {
        if (id == null || ingredientId == null || inputPrice == null || inputQuantity == null) {
            return "Thông tin chi tiết phiếu nhập không đầy đủ";
        }
        InputTicketDetailDTO dto = new InputTicketDetailDTO(id, ingredientId, inputPrice, inputQuantity);
        int result = inputTicketDetailDAL.update(dto);
        return result > 0 ? "Cập nhật chi tiết phiếu nhập thành công" : "Cập nhật chi tiết phiếu nhập thất bại";
    }
}