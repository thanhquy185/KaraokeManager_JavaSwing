package DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.DiscountDTO;

public class DiscountDAL implements DAL<DiscountDTO> {
	// Methods
	// - Hàm thêm một chức năng
	@Override
	public int insert(DiscountDTO discountDTO) {
		// - Biến chứa số dòng đã được thêm
		int rowChange = 0;

		// - Kết nối đến CSDL để truy vấn
		Connection c = JDBCUtil.getInstance().getConnection();
		try {
			String sql = "INSERT INTO Karaoke.KhuyenMai(maKhuyenMai, tenKhuyenMai, maLoaiKhuyenMai, giaTri, mucToiThieu, mucToiDa, ngayBatDau, ngayKetThuc, trangThai, ngayCapNhat)"
					+ "\nVALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setString(1, discountDTO.getId());
			pstmt.setString(2, discountDTO.getName());
			pstmt.setString(3, discountDTO.getDiscountType());
			pstmt.setLong(4, discountDTO.getValue());
			pstmt.setLong(5, discountDTO.getCostMin());
			pstmt.setLong(6, discountDTO.getCostMax());
			pstmt.setString(7, discountDTO.getDateStart());
			pstmt.setString(8, discountDTO.getDateEnd());
			pstmt.setBoolean(9, discountDTO.getStatus());
			pstmt.setString(10, discountDTO.getDateUpdate());
			rowChange = pstmt.executeUpdate();
			JDBCUtil.getInstance().closeConnection(c);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rowChange;
	}

	// - Hàm cập nhật một khuyến mãi
	@Override
	public int update(DiscountDTO discountDTO) {
		// - Biến chứa số dòng đã được thêm
		int rowChange = 0;

		// - Kết nối đến CSDL để truy vấn
		Connection c = JDBCUtil.getInstance().getConnection();
		try {
			String sql = "UPDATE Karaoke.KhuyenMai"
					+ "\nSET tenKhuyenMai = ?, maLoaiKhuyenMai = ?, giaTri = ?, mucToiThieu = ?, mucToiDa = ?, ngayBatDau = ?, ngayKetThuc = ?, trangThai = ?, ngayCapNhat = ?"
					+ "\nWHERE maKhuyenMai = ?";
			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setString(1, discountDTO.getName());
			pstmt.setString(2, discountDTO.getDiscountType());
			pstmt.setLong(3, discountDTO.getValue());
			pstmt.setLong(4, discountDTO.getCostMin());
			pstmt.setLong(5, discountDTO.getCostMax());
			pstmt.setString(6, discountDTO.getDateStart());
			pstmt.setString(7, discountDTO.getDateEnd());
			pstmt.setBoolean(8, discountDTO.getStatus());
			pstmt.setString(9, discountDTO.getDateUpdate());
			pstmt.setString(10, discountDTO.getId());
			rowChange = pstmt.executeUpdate();
			JDBCUtil.getInstance().closeConnection(c);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rowChange;
	}

	// - Hàm khoá một khuyến mãi
	@Override
	public int lock(DiscountDTO discountDTO) {
		// - Biến chứa số dòng đã được thêm
		int rowChange = 0;

		// - Kết nối đến CSDL để truy vấn
		Connection c = JDBCUtil.getInstance().getConnection();
		try {
			String sql = "UPDATE Karaoke.KhuyenMai" + "\nSET trangThai = ?, ngayCapNhat = ?"
					+ "\nWHERE maKhuyenMai = ?";
			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setBoolean(1, discountDTO.getStatus());
			pstmt.setString(2, discountDTO.getDateUpdate());
			pstmt.setString(3, discountDTO.getId());
			rowChange = pstmt.executeUpdate();
			JDBCUtil.getInstance().closeConnection(c);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rowChange;
	}

	// - Hàm lấy ra danh sách các khuyến mãi
	@Override
	public ArrayList<DiscountDTO> selectAll() {
		// - Khai báo biến chứa danh sách trả về
		ArrayList<DiscountDTO> list = new ArrayList<>();

		// - Kết nối đến CSDL để truy vấn
		Connection c = JDBCUtil.getInstance().getConnection();
		try {
			String sql = "SELECT * FROM Karaoke.KhuyenMai;";
			PreparedStatement pstmt = c.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				DiscountDTO discountDTO = new DiscountDTO(rs.getString("maKhuyenMai"), rs.getString("tenKhuyenMai"),
						rs.getString("maLoaiKhuyenMai"), rs.getLong("giaTri"), rs.getLong("mucToiThieu"),
						rs.getLong("mucToiDa"), rs.getString("ngayBatDau"), rs.getString("ngayKetThuc"),
						rs.getBoolean("trangThai"), rs.getString("ngayCapNhat"));
				list.add(discountDTO);
			}
			JDBCUtil.getInstance().closeConnection(c);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	// - Hàm lấy ra danh sách các khuyến mãi dựa trên 1 điều kiện
	@Override
	public ArrayList<DiscountDTO> selectAllByCondition(String[] join, String condition, String order) {
		// - Khai báo biến chứa danh sách trả về
		ArrayList<DiscountDTO> list = new ArrayList<>();

		// - Kết nối đến CSDL để truy vấn
		Connection c = JDBCUtil.getInstance().getConnection();
		try {
			String sql = String.format("SELECT * FROM Karaoke.KhuyenMai%s%s%s;",
					join != null ? CommonDAL.getJoinValues(join) : "", condition != null ? "\nWHERE " + condition : "",
					order != null ? "\nORDER BY " + order : "");
			PreparedStatement pstmt = c.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				DiscountDTO discountDTO = new DiscountDTO(rs.getString("maKhuyenMai"), rs.getString("tenKhuyenMai"),
						rs.getString("maLoaiKhuyenMai"), rs.getLong("giaTri"), rs.getLong("mucToiThieu"),
						rs.getLong("mucToiDa"), rs.getString("ngayBatDau"), rs.getString("ngayKetThuc"),
						rs.getBoolean("trangThai"), rs.getString("ngayCapNhat"));
				list.add(discountDTO);
			}
			JDBCUtil.getInstance().closeConnection(c);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	// - Hàm lấy ra một chức năng dựa trên mã chức năng đó
	@Override
	public DiscountDTO selectOneById(String id) {
		// - Khai báo biến chứa danh sách trả về
		DiscountDTO discountDTO = null;

		// - Kết nối đến CSDL để truy vấn
		Connection c = JDBCUtil.getInstance().getConnection();
		try {
			String sql = String.format("SELECT * FROM Karaoke.KhuyenMai \nWHERE maKhuyenMai = '%s';", id);
			PreparedStatement pstmt = c.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				discountDTO = new DiscountDTO(rs.getString("maKhuyenMai"), rs.getString("tenKhuyenMai"),
						rs.getString("maLoaiKhuyenMai"), rs.getLong("giaTri"), rs.getLong("mucToiThieu"),
						rs.getLong("mucToiDa"), rs.getString("ngayBatDau"), rs.getString("ngayKetThuc"),
						rs.getBoolean("trangThai"), rs.getString("ngayCapNhat"));
			}
			JDBCUtil.getInstance().closeConnection(c);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return discountDTO;
	}

}
