package BLL;

import java.util.ArrayList;

import DAL.InputTicketDAL;
import DTO.InputTicketDTO;

public class InputTicketBLL {
	// Properties
	private InputTicketDAL inputTicketDAL;

	// Constructors
	public InputTicketBLL() {
		inputTicketDAL = new InputTicketDAL();
	}

	// Methods
	// - Hàm lấy ra danh sách các phiếu nhập hiện có trong CSDL
	public ArrayList<InputTicketDTO> getAllInputTicket() {
		return inputTicketDAL.selectAll();
	}

	// - Hàm lấy ra danh sách các phiếu nhập hiện có với 1 điều kiện trong CSDL
	public ArrayList<InputTicketDTO> getAllInputTicketByCondition(String[] join, String condition, String order) {
		return inputTicketDAL.selectAllByCondition(join, condition, order);
	}

	// - Hàm lấy ra một người dùng với mã phiếu nhập tương ứng
	public InputTicketDTO getOneInputTicketById(String id) {
		return inputTicketDAL.selectOneById(id);
	}
}
