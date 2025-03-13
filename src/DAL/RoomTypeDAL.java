package DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import DTO.RoomTypeDTO;

public class RoomTypeDAL implements DAL<RoomTypeDTO> {
	// Methods
	// - Hàm thêm một loại phòng
	@Override
	public int insert(RoomTypeDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	// - Hàm thay đổi một loại phòng
	@Override
	public int update(RoomTypeDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	// - Hàm khoá một loại phòng
	@Override
	public int lock(RoomTypeDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	// - Hàm lấy ra danh sách các loại phòng
	@Override
	public ArrayList<RoomTypeDTO> selectAll() {
		// - Khai báo biến chứa danh sách trả về
		ArrayList<RoomTypeDTO> list = new ArrayList<>();

		// - Kết nối đến CSDL để truy vấn
		Connection c = JDBCUtil.getInstance().getConnection();
		try {
			String sql = "SELECT * FROM Karaoke.LoaiPhong;";
			PreparedStatement pstmt = c.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				RoomTypeDTO roomTypeDTO = new RoomTypeDTO(rs.getString("maLoaiPhong"), rs.getString("tenLoaiPhong"),
						rs.getLong("giaPhong"), rs.getBoolean("trangThai"), rs.getString("ngayCapNhat"));
				list.add(roomTypeDTO);
			}
			JDBCUtil.getInstance().closeConnection(c);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	// - Hàm lấy ra danh sách các phòng dựa trên 1 điều kiện
	@Override
	public ArrayList<RoomTypeDTO> selectAllByCondition(String[] join, String condition, String order) {
		// - Khai báo biến chứa danh sách trả về
		ArrayList<RoomTypeDTO> list = new ArrayList<>();

		// - Kết nối đến CSDL để truy vấn
		Connection c = JDBCUtil.getInstance().getConnection();
		try {
			String sql = String.format("SELECT * FROM Karaoke.LoaiPhong%s%s%s;",
					join != null ? CommonDAL.getJoinValues(join) : "", condition != null ? "\nWHERE " + condition : "",
					order != null ? "\nORDER BY " + order : "");
			PreparedStatement pstmt = c.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				RoomTypeDTO roomTypeDTO = new RoomTypeDTO(rs.getString("maLoaiPhong"), rs.getString("tenLoaiPhong"),
						rs.getLong("giaPhong"), rs.getBoolean("trangThai"), rs.getString("ngayCapNhat"));
				list.add(roomTypeDTO);
			}
			JDBCUtil.getInstance().closeConnection(c);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	// // - Hàm lấy ra một loại phòng dựa trên mã loại phòng đó
	@Override
	public RoomTypeDTO selectOneById(String id) {
		// - Khai báo biến chứa danh sách trả về
		RoomTypeDTO roomTypeDTO = null;

		// - Kết nối đến CSDL để truy vấn
		Connection c = JDBCUtil.getInstance().getConnection();
		try {
			String sql = String.format("SELECT * FROM Karaoke.Phong \nWHERE maPhong = %s;", id);
			PreparedStatement pstmt = c.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				roomTypeDTO = new RoomTypeDTO(rs.getString("maLoaiPhong"), rs.getString("tenLoaiPhong"),
						rs.getLong("giaPhong"), rs.getBoolean("trangThai"), rs.getString("ngayCapNhat"));
			}
			JDBCUtil.getInstance().closeConnection(c);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return roomTypeDTO;
	}
}
