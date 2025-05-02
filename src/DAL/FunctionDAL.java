package DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.FunctionDTO;

public class FunctionDAL implements DAL<FunctionDTO> {
	// Methods
	// - Hàm thêm một chức năng
	@Override
	public int insert(FunctionDTO functionDTO) {
		// - Biến chứa số dòng đã được thêm
		int rowChange = 0;

		// - Kết nối đến CSDL để truy vấn
		Connection c = JDBCUtil.getInstance().getConnection();
		try {
			String sql = "INSERT INTO Karaoke.ChucNang(maChucNang, tenChucNang)"
					+ "\nVALUES(?, ?);";
			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setString(1, functionDTO.getId());
			pstmt.setString(2, functionDTO.getName());
			rowChange = pstmt.executeUpdate();
			JDBCUtil.getInstance().closeConnection(c);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rowChange;
	}

	// - Hàm cập nhật một chức năng
	@Override
	public int update(FunctionDTO functionDTO) {
		// - Biến chứa số dòng đã được thêm
		int rowChange = 0;

		// - Kết nối đến CSDL để truy vấn
		Connection c = JDBCUtil.getInstance().getConnection();
		try {
			String sql = "UPDATE Karaoke.ChucNang" + "\nSET tenChucNang = ?"
					+ "\nWHERE maChucNang = ?";
			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setString(1, functionDTO.getName());
			pstmt.setString(2, functionDTO.getId());
			rowChange = pstmt.executeUpdate();
			JDBCUtil.getInstance().closeConnection(c);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rowChange;
	}

	// - Hàm khoá một chức năng
	@Override
	public int lock(FunctionDTO functionDTO) {
		// // - Biến chứa số dòng đã được thêm
		// int rowChange = 0;

		// // - Kết nối đến CSDL để truy vấn
		// Connection c = JDBCUtil.getInstance().getConnection();
		// try {
		// String sql = "UPDATE Karaoke.ChucNang" + "\nSET trangThai = ?, ngayCapNhat =
		// ?" + "\nWHERE maChucNang = ?";
		// PreparedStatement pstmt = c.prepareStatement(sql);
		// pstmt.setString(2, functionDTO.getId());
		// rowChange = pstmt.executeUpdate();
		// JDBCUtil.getInstance().closeConnection(c);
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }

		return 0;
	}

	// - Hàm lấy ra danh sách các chức năng
	@Override
	public ArrayList<FunctionDTO> selectAll() {
		// - Khai báo biến chứa danh sách trả về
		ArrayList<FunctionDTO> list = new ArrayList<>();

		// - Kết nối đến CSDL để truy vấn
		Connection c = JDBCUtil.getInstance().getConnection();
		try {
			String sql = "SELECT * FROM Karaoke.ChucNang;";
			PreparedStatement pstmt = c.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				FunctionDTO functionDTO = new FunctionDTO(rs.getString("maChucNang"), rs.getString("tenChucNang"));
				list.add(functionDTO);
			}
			JDBCUtil.getInstance().closeConnection(c);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	// - Hàm lấy ra danh sách các chức năng dựa trên 1 điều kiện
	@Override
	public ArrayList<FunctionDTO> selectAllByCondition(String[] join, String condition, String order) {
		// - Khai báo biến chứa danh sách trả về
		ArrayList<FunctionDTO> list = new ArrayList<>();

		// - Kết nối đến CSDL để truy vấn
		Connection c = JDBCUtil.getInstance().getConnection();
		try {
			String sql = String.format("SELECT * FROM Karaoke.ChucNang%s%s%s;",
					join != null ? CommonDAL.getJoinValues(join) : "", condition != null ? "\nWHERE " + condition : "",
					order != null ? "\nORDER BY " + order : "");
			PreparedStatement pstmt = c.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				FunctionDTO functionDTO = new FunctionDTO(rs.getString("maChucNang"), rs.getString("tenChucNang"));
				list.add(functionDTO);
			}
			JDBCUtil.getInstance().closeConnection(c);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	// - Hàm lấy ra một chức năng dựa trên mã chức năng đó
	@Override
	public FunctionDTO selectOneById(String id) {
		// - Khai báo biến chứa danh sách trả về
		FunctionDTO functionDTO = null;

		// - Kết nối đến CSDL để truy vấn
		Connection c = JDBCUtil.getInstance().getConnection();
		try {
			String sql = String.format("SELECT * FROM Karaoke.ChucNang \nWHERE maChucNang = '%s';", id);
			PreparedStatement pstmt = c.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				functionDTO = new FunctionDTO(rs.getString("maChucNang"), rs.getString("tenChucNang"));
			}
			JDBCUtil.getInstance().closeConnection(c);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return functionDTO;
	}

}
