package DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.DiscountTypeDTO;

public class DiscountTypeDAL implements DAL<DiscountTypeDTO> {
	// Methods
	// - Hàm thêm một loại khuyến mãi
	@Override
	public int insert(DiscountTypeDTO DiscountTypeDTO) {
		// - Biến chứa số dòng đã được thêm
		int rowChange = 0;

		// - Kết nối đến CSDL để truy vấn
		Connection c = JDBCUtil.getInstance().getConnection();
		try {
			String sql = "INSERT INTO Karaoke.LoaiKhuyenMai(maLoaiKhuyenMai, tenLoaiKhuyenMai, trangThai, ngayCapNhat)"
					+ "\nVALUES(?, ?, ?, ?);";
			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setString(1, DiscountTypeDTO.getId());
			pstmt.setString(2, DiscountTypeDTO.getName());
			pstmt.setBoolean(3, DiscountTypeDTO.getStatus());
			pstmt.setString(4, DiscountTypeDTO.getDateUpdate());
			rowChange = pstmt.executeUpdate();
			JDBCUtil.getInstance().closeConnection(c);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rowChange;
	}

	// - Hàm cập nhật một loại khuyến mãi
	@Override
	public int update(DiscountTypeDTO DiscountTypeDTO) {
		// - Biến chứa số dòng đã được thêm
		int rowChange = 0;

		// - Kết nối đến CSDL để truy vấn
		Connection c = JDBCUtil.getInstance().getConnection();
		try {
			String sql = "UPDATE Karaoke.LoaiKhuyenMai" + "\nSET tenLoaiKhuyenMai = ?, trangThai = ?, ngayCapNhat = ?"
					+ "\nWHERE maLoaiKhuyenMai = ?";
			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setString(1, DiscountTypeDTO.getName());
			pstmt.setBoolean(2, DiscountTypeDTO.getStatus());
			pstmt.setString(3, DiscountTypeDTO.getDateUpdate());
			pstmt.setString(4, DiscountTypeDTO.getId());
			rowChange = pstmt.executeUpdate();
			JDBCUtil.getInstance().closeConnection(c);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rowChange;
	}

	// - Hàm khoá một loại khuyến mãi
	@Override
	public int lock(DiscountTypeDTO DiscountTypeDTO) {
		// - Biến chứa số dòng đã được thêm
		int rowChange = 0;

		// - Kết nối đến CSDL để truy vấn
		Connection c = JDBCUtil.getInstance().getConnection();
		try {
			String sql = "UPDATE Karaoke.LoaiKhuyenMai" + "\nSET trangThai = ?, ngayCapNhat = ?"
					+ "\nWHERE maLoaiKhuyenMai = ?";
			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setBoolean(1, DiscountTypeDTO.getStatus());
			pstmt.setString(2, DiscountTypeDTO.getDateUpdate());
			pstmt.setString(3, DiscountTypeDTO.getId());
			rowChange = pstmt.executeUpdate();
			JDBCUtil.getInstance().closeConnection(c);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rowChange;
	}

	// - Hàm lấy ra danh sách các loại khuyến mãi
	@Override
	public ArrayList<DiscountTypeDTO> selectAll() {
		// - Khai báo biến chứa danh sách trả về
		ArrayList<DiscountTypeDTO> list = new ArrayList<>();

		// - Kết nối đến CSDL để truy vấn
		Connection c = JDBCUtil.getInstance().getConnection();
		try {
			String sql = "SELECT * FROM Karaoke.LoaiKhuyenMai;";
			PreparedStatement pstmt = c.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				DiscountTypeDTO discountTypeDTO = new DiscountTypeDTO(rs.getString("maLoaiKhuyenMai"),
						rs.getString("tenLoaiKhuyenMai"), rs.getBoolean("trangThai"), rs.getString("ngayCapNhat"));
				list.add(discountTypeDTO);
			}
			JDBCUtil.getInstance().closeConnection(c);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	// - Hàm lấy ra danh sách các loại khuyến mãi dựa trên 1 điều kiện
	@Override
	public ArrayList<DiscountTypeDTO> selectAllByCondition(String[] join, String condition, String order) {
		// - Khai báo biến chứa danh sách trả về
		ArrayList<DiscountTypeDTO> list = new ArrayList<>();

		// - Kết nối đến CSDL để truy vấn
		Connection c = JDBCUtil.getInstance().getConnection();
		try {
			String sql = String.format("SELECT * FROM Karaoke.LoaiKhuyenMai%s%s%s;",
					join != null ? CommonDAL.getJoinValues(join) : "", condition != null ? "\nWHERE " + condition : "",
					order != null ? "\nORDER BY " + order : "");
			PreparedStatement pstmt = c.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				DiscountTypeDTO discountTypeDTO = new DiscountTypeDTO(rs.getString("maLoaiKhuyenMai"),
						rs.getString("tenLoaiKhuyenMai"), rs.getBoolean("trangThai"), rs.getString("ngayCapNhat"));
				list.add(discountTypeDTO);
			}
			JDBCUtil.getInstance().closeConnection(c);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	// - Hàm lấy ra một loại khuyến mãi dựa trên mã loại khuyến mãi đó
	@Override
	public DiscountTypeDTO selectOneById(String id) {
		// - Khai báo biến chứa danh sách trả về
		DiscountTypeDTO discountTypeDTO = null;

		// - Kết nối đến CSDL để truy vấn
		Connection c = JDBCUtil.getInstance().getConnection();
		try {
			String sql = String.format("SELECT * FROM Karaoke.LoaiKhuyenMai \nWHERE maLoaiKhuyenMai = '%s';", id);
			PreparedStatement pstmt = c.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				discountTypeDTO = new DiscountTypeDTO(rs.getString("maLoaiKhuyenMai"), rs.getString("tenLoaiKhuyenMai"),
						rs.getBoolean("trangThai"), rs.getString("ngayCapNhat"));
			}
			JDBCUtil.getInstance().closeConnection(c);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return discountTypeDTO;
	}
}
