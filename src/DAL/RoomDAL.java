package DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.RoomDTO;

public class RoomDAL implements DAL<RoomDTO> {
	// Methods
	// - Hàm thêm một phòng
	@Override
	public int insert(RoomDTO roomDTO) {
		// - Biến chứa số dòng đã được thêm
		int rowChange = 0;

		// - Kết nối đến CSDL để truy vấn
		Connection c = JDBCUtil.getInstance().getConnection();
		try {
			String sql = "INSERT INTO Karaoke.Phong(maPhong, tenPhong, maLoaiPhong, trangThai, thoiGianCapNhat)"
					+ "\nVALUES(?, ?, ?, ?, ?);";
			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setString(1, roomDTO.getId());
			pstmt.setString(2, roomDTO.getName());
			pstmt.setString(3, roomDTO.getType());
			pstmt.setBoolean(4, roomDTO.getStatus());
			pstmt.setString(5, roomDTO.getTimeUpdate());
			rowChange = pstmt.executeUpdate();
			JDBCUtil.getInstance().closeConnection(c);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rowChange;
	}

	// - Hàm cập nhật một phòng
	@Override
	public int update(RoomDTO roomDTO) {
		// - Biến chứa số dòng đã được thêm
		int rowChange = 0;

		// - Kết nối đến CSDL để truy vấn
		Connection c = JDBCUtil.getInstance().getConnection();
		try {
			String sql = "UPDATE Karaoke.Phong"
					+ "\nSET tenPhong = ?, maLoaiPhong = ?, thoiGianCapNhat = ?"
					+ "\nWHERE maPhong = ?";
			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setString(1, roomDTO.getName());
			pstmt.setString(2, roomDTO.getType());
			pstmt.setString(3, roomDTO.getTimeUpdate());
			pstmt.setString(4, roomDTO.getId());
			rowChange = pstmt.executeUpdate();
			JDBCUtil.getInstance().closeConnection(c);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rowChange;
	}

	// - Hàm khoá một phòng
	@Override
	public int lock(RoomDTO roomDTO) {
		// - Biến chứa số dòng đã được thêm
		int rowChange = 0;

		// - Kết nối đến CSDL để truy vấn
		Connection c = JDBCUtil.getInstance().getConnection();
		try {
			String sql = "UPDATE Karaoke.Phong" + "\nSET trangThai = ?, thoiGianCapNhat = ?" + "\nWHERE maPhong = ?";
			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setBoolean(1, roomDTO.getStatus());
			pstmt.setString(2, roomDTO.getTimeUpdate());
			pstmt.setString(3, roomDTO.getId());
			rowChange = pstmt.executeUpdate();
			JDBCUtil.getInstance().closeConnection(c);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rowChange;
	}

	// - Hàm lấy ra danh sách các phòng
	@Override
	public ArrayList<RoomDTO> selectAll() {
		// - Khai báo biến chứa danh sách trả về
		ArrayList<RoomDTO> list = new ArrayList<>();

		// - Kết nối đến CSDL để truy vấn
		Connection c = JDBCUtil.getInstance().getConnection();
		try {
			String sql = "SELECT * FROM Karaoke.Phong;";
			PreparedStatement pstmt = c.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				RoomDTO roomDTO = new RoomDTO(rs.getString("maPhong"), rs.getString("tenPhong"),
						rs.getString("maLoaiPhong"), rs.getBoolean("trangThai"), rs.getString("thoiGianCapNhat"));
				list.add(roomDTO);
			}
			JDBCUtil.getInstance().closeConnection(c);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	// - Hàm lấy ra danh sách các phòng dựa trên 1 điều kiện
	@Override
	public ArrayList<RoomDTO> selectAllByCondition(String[] join, String condition, String order) {
		// - Khai báo biến chứa danh sách trả về
		ArrayList<RoomDTO> list = new ArrayList<>();

		// - Kết nối đến CSDL để truy vấn
		Connection c = JDBCUtil.getInstance().getConnection();
		try {
			String sql = String.format("SELECT * FROM Karaoke.Phong%s%s%s;",
					join != null ? CommonDAL.getJoinValues(join) : "", condition != null ? "\nWHERE " + condition : "",
					order != null ? "\nORDER BY " + order : "");
			PreparedStatement pstmt = c.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				RoomDTO roomDTO = new RoomDTO(rs.getString("maPhong"), rs.getString("tenPhong"),
						rs.getString("maLoaiPhong"), rs.getBoolean("trangThai"), rs.getString("thoiGianCapNhat"));
				list.add(roomDTO);
			}
			JDBCUtil.getInstance().closeConnection(c);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	// - Hàm lấy ra một phòng dựa trên mã phòng đó
	@Override
	public RoomDTO selectOneById(String id) {
		// - Khai báo biến chứa danh sách trả về
		RoomDTO roomDTO = null;

		// - Kết nối đến CSDL để truy vấn
		Connection c = JDBCUtil.getInstance().getConnection();
		try {
			String sql = String.format("SELECT * FROM Karaoke.Phong \nWHERE maPhong = '%s';", id);
			PreparedStatement pstmt = c.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				roomDTO = new RoomDTO(rs.getString("maPhong"), rs.getString("tenPhong"), rs.getString("maLoaiPhong"),
						rs.getBoolean("trangThai"), rs.getString("thoiGianCapNhat"));
			}
			JDBCUtil.getInstance().closeConnection(c);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return roomDTO;
	}

}
