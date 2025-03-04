package BLL;

import java.util.ArrayList;

import DAL.RoomDAL;
import DTO.RoomDTO;

public class RoomBLL {
	// Properties
	private RoomDAL roomDAL;

	// Constructors
	public RoomBLL() {
		roomDAL = new RoomDAL();
	}

	// Methods
	// - Hàm lấy ra danh sách các loại phòng hiện có trong CSDL
	public ArrayList<RoomDTO> getAllRoom() {
		return roomDAL.selectAll();
	}

	// - Hàm lấy ra danh sách các loại phòng hiện có với 1 điều kiện trong CSDL
	public ArrayList<RoomDTO> getAllRoomByCondition(String[] join, String condition, String order) {
		return roomDAL.selectAllByCondition(join, condition, order);
	}

	// - Hàm lấy ra một loại phòng với mã loại phòng tương ứng
	public RoomDTO getOneRoomById(String id) {
		return roomDAL.selectOneById(id);
	}
}
