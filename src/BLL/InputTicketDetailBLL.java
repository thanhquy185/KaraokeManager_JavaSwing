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

    public ArrayList<InputTicketDetailDTO> getAllInputTicketDetailByCondition(String[] join, String condition,
            String order) {
        return inputTicketDetailDAL.selectAllByCondition(join, condition, order);
    }

    public ArrayList<InputTicketDetailDTO> getAllInputTicketDetailByInputTicketId(String inputTicketId) {
        return inputTicketDetailDAL.selectAllByInputTicketId(inputTicketId);
    }

    public InputTicketDetailDTO getOneInputTicketDetailById(String id) {
        return inputTicketDetailDAL.selectOneById(id);
    }

    public String insertInputTicketDetail(Integer inputTicketId, String foodId, Long price, Long quantity) {
        if (inputTicketId == null || foodId == null || price == null || quantity == null) {
            return "Thông tin chi tiết phiếu nhập không đầy đủ";
        }

        InputTicketDetailDTO dto = new InputTicketDetailDTO(inputTicketId, foodId, price, quantity);
        int result = inputTicketDetailDAL.insert(dto);
        return result > 0 ? "Thêm chi tiết phiếu nhập thành công" : "Thêm chi tiết phiếu nhập thất bại";
    }
}