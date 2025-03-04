package BLL;

import java.util.ArrayList;

import DAL.RoomTypeDAL;
import DTO.RoomTypeDTO;

public class RoomTypeBLL {
	// Properties
	private RoomTypeDAL roomTypeDAL;

	// Constructors
	public RoomTypeBLL() {
		roomTypeDAL = new RoomTypeDAL();
	}

	// Methods
	// - Hàm lấy ra danh sách các loại phòng hiện có trong CSDL
	public ArrayList<RoomTypeDTO> getAllRoomType() {
		return roomTypeDAL.selectAll();
	}

	// - Hàm lấy ra danh sách các loại phòng hiện có với 1 điều kiện trong CSDL
	public ArrayList<RoomTypeDTO> getAllRoomTypeByCondition(String[] join, String condition, String order) {
		return roomTypeDAL.selectAllByCondition(join, condition, order);
	}

	// - Hàm lấy ra một loại phòng với mã loại phòng tương ứng
	public RoomTypeDTO getOneRoomTypeById(String id) {
		return roomTypeDAL.selectOneById(id);
	}
}
