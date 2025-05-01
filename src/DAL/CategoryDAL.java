package DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.CategoryDTO;

public class CategoryDAL implements DAL<CategoryDTO> {
	// Methods
	// - Hàm thêm một loại món ăn
	@Override
	public int insert(CategoryDTO categoryDTO) {
		// - Biến chứa số dòng đã được thêm
		int rowChange = 0;

		// - Kết nối đến CSDL để truy vấn
		Connection c = JDBCUtil.getInstance().getConnection();
		try {
			String sql = "INSERT INTO Karaoke.LoaiMonAn(maLoaiMonAn, tenLoaiMonAn, trangThai, thoiGianCapNhat)"
					+ "\nVALUES(?, ?, ?, ?);";
			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setString(1, categoryDTO.getId());
			pstmt.setString(2, categoryDTO.getName());
			pstmt.setBoolean(3, categoryDTO.getStatus());
			pstmt.setString(4, categoryDTO.getTimeUpdate());
			rowChange = pstmt.executeUpdate();
			JDBCUtil.getInstance().closeConnection(c);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rowChange;
	}

	// - Hàm cập nhật một loại món ăn
	@Override
	public int update(CategoryDTO categoryDTO) {
		// - Biến chứa số dòng đã được thêm
		int rowChange = 0;

		// - Kết nối đến CSDL để truy vấn
		Connection c = JDBCUtil.getInstance().getConnection();
		try {
			String sql = "UPDATE Karaoke.LoaiMonAn" + "\nSET tenLoaiMonAn = ?, thoiGianCapNhat = ?"
					+ "\nWHERE maLoaiMonAn = ?";
			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setString(1, categoryDTO.getName());
			pstmt.setString(2, categoryDTO.getTimeUpdate());
			pstmt.setString(3, categoryDTO.getId());
			rowChange = pstmt.executeUpdate();
			JDBCUtil.getInstance().closeConnection(c);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rowChange;
	}

	// - Hàm khoá một loại món ăn
	@Override
	public int lock(CategoryDTO categoryDTO) {
		// - Biến chứa số dòng đã được thêm
		int rowChange = 0;

		// - Kết nối đến CSDL để truy vấn
		Connection c = JDBCUtil.getInstance().getConnection();
		try {
			String sql = "UPDATE Karaoke.LoaiMonAn" + "\nSET trangThai = ?, thoiGianCapNhat = ?"
					+ "\nWHERE maLoaiMonAn = ?";
			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setBoolean(1, categoryDTO.getStatus());
			pstmt.setString(2, categoryDTO.getTimeUpdate());
			pstmt.setString(3, categoryDTO.getId());
			rowChange = pstmt.executeUpdate();
			JDBCUtil.getInstance().closeConnection(c);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rowChange;
	}

	// - Hàm lấy ra danh sách các loại món ăn
	@Override
	public ArrayList<CategoryDTO> selectAll() {
		// - Khai báo biến chứa danh sách trả về
		ArrayList<CategoryDTO> list = new ArrayList<>();

		// - Kết nối đến CSDL để truy vấn
		Connection c = JDBCUtil.getInstance().getConnection();
		try {
			String sql = "SELECT * FROM Karaoke.LoaiMonAn;";
			PreparedStatement pstmt = c.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				CategoryDTO categoryDTO = new CategoryDTO(rs.getString("maLoaiMonAn"), rs.getString("tenLoaiMonAn"),
						rs.getBoolean("trangThai"), rs.getString("thoiGianCapNhat"));
				list.add(categoryDTO);
			}
			JDBCUtil.getInstance().closeConnection(c);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	// - Hàm lấy ra danh sách các loại món ăn dựa trên 1 điều kiện
	@Override
	public ArrayList<CategoryDTO> selectAllByCondition(String[] join, String condition, String order) {
		// - Khai báo biến chứa danh sách trả về
		ArrayList<CategoryDTO> list = new ArrayList<>();

		// - Kết nối đến CSDL để truy vấn
		Connection c = JDBCUtil.getInstance().getConnection();
		try {
			String sql = String.format("SELECT * FROM Karaoke.LoaiMonAn%s%s%s;",
					join != null ? CommonDAL.getJoinValues(join) : "", condition != null ? "\nWHERE " + condition : "",
					order != null ? "\nORDER BY " + order : "");
			PreparedStatement pstmt = c.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				CategoryDTO categoryDTO = new CategoryDTO(rs.getString("maLoaiMonAn"), rs.getString("tenLoaiMonAn"),
						rs.getBoolean("trangThai"), rs.getString("thoiGianCapNhat"));
				list.add(categoryDTO);
			}
			JDBCUtil.getInstance().closeConnection(c);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	// - Hàm lấy ra một loại món ăn dựa trên mã loại món ăn đó
	@Override
	public CategoryDTO selectOneById(String id) {
		// - Khai báo biến chứa danh sách trả về
		CategoryDTO categoryDTO = null;

		// - Kết nối đến CSDL để truy vấn
		Connection c = JDBCUtil.getInstance().getConnection();
		try {
			String sql = String.format("SELECT * FROM Karaoke.LoaiMonAn \nWHERE maLoaiMonAn = '%s';", id);
			PreparedStatement pstmt = c.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				categoryDTO = new CategoryDTO(rs.getString("maLoaiMonAn"), rs.getString("tenLoaiMonAn"),
						rs.getBoolean("trangThai"), rs.getString("thoiGianCapNhat"));
			}
			JDBCUtil.getInstance().closeConnection(c);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return categoryDTO;
	}

}
