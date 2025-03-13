package BLL;

import java.util.ArrayList;

import DAL.InputTicketDetailDAL;
import DTO.InputTicketDetailDTO;

public class InputTicketDetailBLL {
	// Properties
		private InputTicketDetailDAL inputTicketDetailDAL;

		// Constructors
		public InputTicketDetailBLL() {
			inputTicketDetailDAL = new InputTicketDetailDAL();
		}

		// Methods
		// - Hàm lấy ra danh sách các chi tiết phiếu nhập hiện có trong CSDL
		public ArrayList<InputTicketDetailDTO> getAllInputTicketDetail() {
			return inputTicketDetailDAL.selectAll();
		}

		// - Hàm lấy ra danh sách các chi tiết phiếu nhập hiện có với 1 điều kiện trong CSDL
		public ArrayList<InputTicketDetailDTO> getAllInputTicketDetailByCondition(String[] join, String condition, String order) {
			return inputTicketDetailDAL.selectAllByCondition(join, condition, order);
		}

		// - Hàm lấy ra một người dùng với mã chi tiết phiếu nhập tương ứng
		public InputTicketDetailDTO getOneInputTicketDetailById(String id) {
			return inputTicketDetailDAL.selectOneById(id);
		}
}
