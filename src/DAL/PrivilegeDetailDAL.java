package DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.PrivilegeDetailDTO;

public class PrivilegeDetailDAL implements DAL<PrivilegeDetailDTO> {
	// Methods
	// - Hàm thêm một chi tiết quyền
	@Override
	public int insert(PrivilegeDetailDTO privilegeDetailDTO) {
		// - Biến chứa số dòng đã được thêm
		int rowChange = 0;

		// - Kết nối đến CSDL để truy vấn
		Connection c = JDBCUtil.getInstance().getConnection();
		try {
			String sql = "INSERT INTO Karaoke.ChiTietQuyen(maNguoiDung, maQuyen, maChucNang, trangThai)"
					+ "\nVALUES(?, ?, ?, ?);";
			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, privilegeDetailDTO.getAccountId());
			pstmt.setString(2, privilegeDetailDTO.getPrivilegeId());
			pstmt.setString(3, privilegeDetailDTO.getFunctionId());
			pstmt.setBoolean(4, privilegeDetailDTO.getStatus());
			rowChange = pstmt.executeUpdate();
			JDBCUtil.getInstance().closeConnection(c);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rowChange;
	}

	// - Hàm thay đổi một chi tiết quyền
	@Override
	public int update(PrivilegeDetailDTO privilegeDetailDTO) {
		// - Biến chứa số dòng đã được thêm
		int rowChange = 0;

		// - Kết nối đến CSDL để truy vấn
		Connection c = JDBCUtil.getInstance().getConnection();
		try {
			String sql = "UPDATE Karaoke.ChiTietQuyen" + "\nSET maQuyen = ?, trangThai = ?"
					+ "\nWHERE maNguoiDung = ? AND maChucNang = ?";
			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setString(1, privilegeDetailDTO.getPrivilegeId());
			pstmt.setBoolean(2, privilegeDetailDTO.getStatus());
			pstmt.setInt(3, privilegeDetailDTO.getAccountId());
			pstmt.setString(4, privilegeDetailDTO.getFunctionId());
			rowChange = pstmt.executeUpdate();
			JDBCUtil.getInstance().closeConnection(c);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rowChange;
	}

	// - Hàm khoá một chi tiết quyền
	@Override
	public int lock(PrivilegeDetailDTO privilegeDetailDTO) {
		// TODO Auto-generated method stub
		return 0;
	}

	// - Hàm lấy ra danh sách các chi tiết quyền
	@Override
	public ArrayList<PrivilegeDetailDTO> selectAll() {
		// - Khai báo biến chứa danh sách trả về
		ArrayList<PrivilegeDetailDTO> list = new ArrayList<>();

		// - Kết nối đến CSDL để truy vấn
		Connection c = JDBCUtil.getInstance().getConnection();
		try {
			String sql = "SELECT * FROM Karaoke.ChiTietQuyen;";
			PreparedStatement pstmt = c.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				PrivilegeDetailDTO privilegeDetailDTO = new PrivilegeDetailDTO(rs.getInt("maNguoiDung"),
						rs.getString("maQuyen"), rs.getString("maChucNang"), rs.getBoolean("trangThai"));
				list.add(privilegeDetailDTO);
			}
			JDBCUtil.getInstance().closeConnection(c);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	// - Hàm lấy ra danh sách các chi tiết quyền dựa trên 1 điều kiện
	@Override
	public ArrayList<PrivilegeDetailDTO> selectAllByCondition(String[] join, String condition, String order) {
		// - Khai báo biến chứa danh sách trả về
		ArrayList<PrivilegeDetailDTO> list = new ArrayList<>();

		// - Kết nối đến CSDL để truy vấn
		Connection c = JDBCUtil.getInstance().getConnection();
		try {
			String sql = String.format("SELECT * FROM Karaoke.ChiTietQuyen %s %s %s;",
					join != null ? CommonDAL.getJoinValues(join) : "", condition != null ? "\nWHERE " + condition : "",
					order != null ? "\nORDER BY " + order : "");
			PreparedStatement pstmt = c.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				PrivilegeDetailDTO privilegeDetailDTO = new PrivilegeDetailDTO(rs.getInt("maNguoiDung"),
						rs.getString("maQuyen"), rs.getString("maChucNang"), rs.getBoolean("trangThai"));
				list.add(privilegeDetailDTO);
			}
			JDBCUtil.getInstance().closeConnection(c);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	// - Hàm lấy ra danh sách các chi tiết quyền dựa trên mã người dùng
	@Override
	public PrivilegeDetailDTO selectOneById(String id) {
		// - Khai báo biến chứa danh sách trả về
		PrivilegeDetailDTO privilegeDetailDTO = null;

		// - Kết nối đến CSDL để truy vấn
		Connection c = JDBCUtil.getInstance().getConnection();
		try {
			String sql = String.format("SELECT * FROM Karaoke.ChiTietQuyen \nWHERE maNguoiDung = '%s';", id);
			PreparedStatement pstmt = c.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				privilegeDetailDTO = new PrivilegeDetailDTO(rs.getInt("maNguoiDung"), rs.getString("maQuyen"),
						rs.getString("maChucNang"), rs.getBoolean("trangThai"));
			}
			JDBCUtil.getInstance().closeConnection(c);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return privilegeDetailDTO;
	}

}
