package DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.DiscountDTO;
import DTO.FunctionDTO;

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
			String sql = "INSERT INTO Karaoke.KhuyenMai(maKhuyenMai, tenKhuyenMai, phanTram, mucApDung, ngayBatDau, ngayKetThuc, trangThai, ngayCapNhat)"
					+ "\nVALUES(?, ?, ?, ?, ?, ?, ?, ?);";
			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setString(1, discountDTO.getId());
			pstmt.setString(2, discountDTO.getName());
			pstmt.setInt(3, discountDTO.getPercent());
			pstmt.setLong(4, discountDTO.getRoomCost());
			pstmt.setString(5, discountDTO.getDateStart());
			pstmt.setString(6, discountDTO.getDateEnd());
			pstmt.setBoolean(7, discountDTO.getStatus());
			pstmt.setString(8, discountDTO.getDateUpdate());
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
					+ "\nSET tenKhuyenMai = ?, phanTram = ?, mucApDung = ?, ngayBatDau = ?, ngayKetThuc = ?, trangThai = ?, ngayCapNhat = ?"
					+ "\nWHERE maKhuyenMai = ?";
			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setString(1, discountDTO.getName());
			pstmt.setInt(2, discountDTO.getPercent());
			pstmt.setLong(3, discountDTO.getRoomCost());
			pstmt.setString(4, discountDTO.getDateStart());
			pstmt.setString(5, discountDTO.getDateEnd());
			pstmt.setBoolean(6, discountDTO.getStatus());
			pstmt.setString(7, discountDTO.getDateUpdate());
			pstmt.setString(8, discountDTO.getId());
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
						rs.getInt("phanTram"), rs.getLong("mucApDung"), rs.getString("ngayBatDau"),
						rs.getString("ngayKetThuc"), rs.getBoolean("trangThai"), rs.getString("ngayCapNhat"));
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
						rs.getInt("phanTram"), rs.getLong("mucApDung"), rs.getString("ngayBatDau"),
						rs.getString("ngayKetThuc"), rs.getBoolean("trangThai"), rs.getString("ngayCapNhat"));
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
						rs.getInt("phanTram"), rs.getLong("mucApDung"), rs.getString("ngayBatDau"),
						rs.getString("ngayKetThuc"), rs.getBoolean("trangThai"), rs.getString("ngayCapNhat"));
			}
			JDBCUtil.getInstance().closeConnection(c);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return discountDTO;
	}

}
