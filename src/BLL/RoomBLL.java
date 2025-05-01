package BLL;

import java.util.ArrayList;

import DAL.RoomDAL;
import DTO.RoomDTO;
import DTO.RoomDTO;

public class RoomBLL {
	// Properties
	private RoomDAL roomDAL;

	// Constructors
	public RoomBLL() {
		roomDAL = new RoomDAL();
	}

	// Methods
	// Kiểm tra dữ liệu đầu vào
	private boolean isInputed(String value) {
		return value != null && !value.trim().isEmpty();
	}

	private boolean isValidId(String id) {
		return id != null && id.matches("PH\\d{3}");
	}

	private boolean isValidStatus(String status) {
		return status != null && (status.equals("Hoạt động") || status.equals("Tạm dừng"));
	}

	private boolean isExistsId(String id) {
		String[] join = null;
		String condition = String.format("maPH = '%s'", id);
		String order = null;
		return !roomDAL.selectAllByCondition(join, condition, order).isEmpty();
	}

	// Thêm phòng hát
	public String insertRoom(String id, String name, String type, String status, String timeUpdate) {
		// Kiểm tra đầu vào
		if (!isInputed(id))
			return "Chưa nhập mã phòng hát";
		if (!isInputed(name))
			return "Chưa nhập tên phòng hát";
		if (!isInputed(type))
			return "Chưa chọn loại phòng hát";
		if (!isInputed(status))
			return "Chưa chọn trạng thái";

		// Kiểm tra định dạng
		if (!isValidId(id))
			return "Mã phòng hát phải có định dạng PHxxx (x là số)";
		if (!isValidStatus(status))
			return "Trạng thái không hợp lệ";

		// Kiểm tra trùng lặp
		if (isExistsId(id))
			return "Mã phòng hát đã tồn tại";

		// Thêm vào database
		boolean roomStatus = status.equals("Hoạt động");
		RoomDTO newRoom = new RoomDTO(id, name, type, roomStatus, timeUpdate);
		int rowsAffected = roomDAL.insert(newRoom);
		return rowsAffected > 0 ? "Thêm phòng hát thành công" : "Không thể thêm phòng hát vào cơ sở dữ liệu";
	}

	// Cập nhật phòng hát
	public String updateRoom(String id, String name, String type, String timeUpdate) {
		// Kiểm tra đầu vào
		if (!isInputed(name))
			return "Chưa nhập tên phòng hát";
		if (!isInputed(type))
			return "Chưa chọn loại phòng hát";

		// Cập nhật
		RoomDTO updatedRoom = new RoomDTO(id, name, type, null, timeUpdate);
		int rowsAffected = roomDAL.update(updatedRoom);
		return rowsAffected > 0 ? "Cập nhật phòng hát thành công" : "Không thể cập nhật phòng hát";
	}

	// Khóa/Mở khóa phòng hát
	public String lockRoom(String id, String timeUpdate) {
		RoomDTO Room = roomDAL.selectOneById(id);
		if (Room == null)
			return "phòng hát không tồn tại";

		// Đảo ngược trạng thái trước khi cập nhật
		Room.setStatus(!Room.getStatus());
		Room.setTimeUpdate(timeUpdate);
		int rowsAffected = roomDAL.lock(Room);
		return rowsAffected > 0 ? "Thay đổi trạng thái thành công" : "Không thể thay đổi trạng thái phòng hát";
	}

	// - Hàm lấy ra danh sách các phòng hát hiện có trong CSDL
	public ArrayList<RoomDTO> getAllRoom() {
		return roomDAL.selectAll();
	}

	// - Hàm lấy ra danh sách các phòng hát hiện có với 1 điều kiện trong CSDL
	public ArrayList<RoomDTO> getAllRoomByCondition(String[] join, String condition, String order) {
		return roomDAL.selectAllByCondition(join, condition, order);
	}

	// - Hàm lấy ra một người dùng với mã phòng hát tương ứng
	public RoomDTO getOneRoomById(String id) {
		return roomDAL.selectOneById(id);
	}

	// - Hàm lấy ra phòng hát cuối cùng
	public RoomDTO getLastRoom() {
		ArrayList<RoomDTO> rooms = roomDAL.selectAllByCondition(null, null, "maPhong DESC");
		return rooms.isEmpty() ? new RoomDTO("PH000", "", "", true, "") : rooms.get(0);
	}
}
