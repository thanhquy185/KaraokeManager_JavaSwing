package DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.PrivilegeDTO;

public class PrivilegeDAL implements DAL<PrivilegeDTO> {
	// Methods
	// - Hàm thêm một quyền
	@Override
	public int insert(PrivilegeDTO privilegeDTO) {
		// - Biến chứa số dòng đã được thêm
		int rowChange = 0;

		// - Kết nối đến CSDL để truy vấn
		Connection c = JDBCUtil.getInstance().getConnection();
		try {
			String sql = "INSERT INTO Karaoke.Quyen(maQuyen, tenQuyen, trangThai, ngayCapNhat)"
					+ "\nVALUES(?, ?, ?, ?);";
			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setString(1, privilegeDTO.getId());
			pstmt.setString(2, privilegeDTO.getName());
			pstmt.setBoolean(3, privilegeDTO.getStatus());
			pstmt.setString(4, privilegeDTO.getDateUpdate());
			rowChange = pstmt.executeUpdate();
			System.out.println(sql);
			JDBCUtil.getInstance().closeConnection(c);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rowChange;
	}

	// - Hàm thay đổi một quyền
	@Override
	public int update(PrivilegeDTO privilegeDTO) {
		// - Biến chứa số dòng đã được thêm
		int rowChange = 0;

		// - Kết nối đến CSDL để truy vấn
		Connection c = JDBCUtil.getInstance().getConnection();
		try {
			String sql = "UPDATE Karaoke.Quyen" + "\nSET tenQuyen = ?, trangThai = ?, ngayCapNhat = ?"
					+ "\nWHERE maNguoiDung = ?";
			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setString(1, privilegeDTO.getName());
			pstmt.setBoolean(2, privilegeDTO.getStatus());
			pstmt.setString(3, privilegeDTO.getDateUpdate());
			pstmt.setString(4, privilegeDTO.getId());
			rowChange = pstmt.executeUpdate();
			JDBCUtil.getInstance().closeConnection(c);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rowChange;
	}

	// - Hàm khoá một quyền
	@Override
	public int lock(PrivilegeDTO privilegeDTO) {
		// - Biến chứa số dòng đã được thêm
		int rowChange = 0;

		// - Kết nối đến CSDL để truy vấn
		Connection c = JDBCUtil.getInstance().getConnection();
		try {
			String sql = "UPDATE Karaoke.Quyen" + "\nSET trangThai = ?, ngayCapNhat = ?" + "\nWHERE maNguoiDung = ?";
			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setBoolean(1, privilegeDTO.getStatus());
			pstmt.setString(2, privilegeDTO.getDateUpdate());
			pstmt.setString(3, privilegeDTO.getId());
			rowChange = pstmt.executeUpdate();
			JDBCUtil.getInstance().closeConnection(c);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rowChange;
	}

	// - Hàm lấy ra danh sách các quyền
	@Override
	public ArrayList<PrivilegeDTO> selectAll() {
		// - Khai báo biến chứa danh sách trả về
		ArrayList<PrivilegeDTO> list = new ArrayList<>();

		// - Kết nối đến CSDL để truy vấn
		Connection c = JDBCUtil.getInstance().getConnection();
		try {
			String sql = "SELECT * FROM Karaoke.Quyen;";
			PreparedStatement pstmt = c.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				PrivilegeDTO privilegeDTO = new PrivilegeDTO(rs.getString("maQuyen"), rs.getString("tenQuyen"),
						rs.getBoolean("trangThai"), rs.getString("ngayCapNhat"));
				list.add(privilegeDTO);
			}
			JDBCUtil.getInstance().closeConnection(c);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	// - Hàm lấy ra danh sách các quyền dựa trên 1 điều kiện
	@Override
	public ArrayList<PrivilegeDTO> selectAllByCondition(String[] join, String condition, String order) {
		// - Khai báo biến chứa danh sách trả về
		ArrayList<PrivilegeDTO> list = new ArrayList<>();

		// - Kết nối đến CSDL để truy vấn
		Connection c = JDBCUtil.getInstance().getConnection();
		try {
			String sql = String.format("SELECT * FROM Karaoke.Quyen %s %s %s;",
					join != null ? CommonDAL.getJoinValues(join) : "", condition != null ? "\nWHERE " + condition : "",
					order != null ? "\nORDER BY " + order : "");
			PreparedStatement pstmt = c.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				PrivilegeDTO privilegeDTO = new PrivilegeDTO(rs.getString("maQuyen"), rs.getString("tenQuyen"),
						rs.getBoolean("trangThai"), rs.getString("ngayCapNhat"));
				list.add(privilegeDTO);
			}
			JDBCUtil.getInstance().closeConnection(c);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	// - Hàm lấy ra một quyền dựa trên mã quyền đó
	@Override
	public PrivilegeDTO selectOneById(String id) {
		// - Khai báo biến chứa danh sách trả về
		PrivilegeDTO privilegeDTO = null;

		// - Kết nối đến CSDL để truy vấn
		Connection c = JDBCUtil.getInstance().getConnection();
		try {
			String sql = String.format("SELECT * FROM Karaoke.Quyen \nWHERE maQuyen = '%s';", id);
			PreparedStatement pstmt = c.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				privilegeDTO = new PrivilegeDTO(rs.getString("maQuyen"), rs.getString("tenQuyen"),
						rs.getBoolean("trangThai"), rs.getString("ngayCapNhat"));
			}
			JDBCUtil.getInstance().closeConnection(c);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return privilegeDTO;
	}
}
