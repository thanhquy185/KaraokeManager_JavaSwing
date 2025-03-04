package DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.AccountDTO;

public class AccountDAL implements DAL<AccountDTO> {
	// Methods
	// - Hàm thêm một người dùng
	@Override
	public int insert(AccountDTO accountDTO) {
		// - Biến chứa số dòng đã được thêm
		int rowChange = 0;

		// - Kết nối đến CSDL để truy vấn
		Connection c = JDBCUtil.getInstance().getConnection();
		try {
			String sql = "INSERT INTO Karaoke.NguoiDung(maNguoiDung, hoVaTen, soDienThoai, email, diaChi, tenTaiKhoan, matKhau, maQuyen, trangThai, ngayCapNhat)"
					+ "\nVALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, accountDTO.getId());
			pstmt.setString(2, accountDTO.getFullname());
			pstmt.setString(3, accountDTO.getPhone());
			pstmt.setString(4, accountDTO.getEmail());
			pstmt.setString(5, accountDTO.getAddress());
			pstmt.setString(6, accountDTO.getUsername());
			pstmt.setString(7, accountDTO.getPassword());
			pstmt.setString(8, accountDTO.getPrivilegeId());
			pstmt.setBoolean(9, accountDTO.getStatus());
			pstmt.setString(10, accountDTO.getDateUpdate());
			rowChange = pstmt.executeUpdate();
			JDBCUtil.getInstance().closeConnection(c);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rowChange;
	}

	// - Hàm cập nhật một người dùng
	@Override
	public int update(AccountDTO accountDTO) {
		// - Biến chứa số dòng đã được thêm
		int rowChange = 0;

		// - Kết nối đến CSDL để truy vấn
		Connection c = JDBCUtil.getInstance().getConnection();
		try {
			String sql = "UPDATE Karaoke.NguoiDung"
					+ "\nSET hoVaTen = ?, soDienThoai = ?, email = ?, diaChi = ?, tenTaiKhoan = ?, matKhau = ?, maQuyen = ?, trangThai = ?, ngayCapNhat = ?"
					+ "\nWHERE maNguoiDung = ?";
			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setString(1, accountDTO.getFullname());
			pstmt.setString(2, accountDTO.getPhone());
			pstmt.setString(3, accountDTO.getEmail());
			pstmt.setString(4, accountDTO.getAddress());
			pstmt.setString(5, accountDTO.getUsername());
			pstmt.setString(6, accountDTO.getPassword());
			pstmt.setString(7, accountDTO.getPrivilegeId());
			pstmt.setBoolean(8, accountDTO.getStatus());
			pstmt.setString(9, accountDTO.getDateUpdate());
			pstmt.setInt(10, accountDTO.getId());
			rowChange = pstmt.executeUpdate();
			JDBCUtil.getInstance().closeConnection(c);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rowChange;
	}

	// - Hàm khoá một người dùng
	@Override
	public int lock(AccountDTO accountDTO) {
		// - Biến chứa số dòng đã được thêm
		int rowChange = 0;

		// - Kết nối đến CSDL để truy vấn
		Connection c = JDBCUtil.getInstance().getConnection();
		try {
			String sql = "UPDATE Karaoke.NguoiDung" + "\nSET trangThai = ?, ngayCapNhat = ?"
					+ "\nWHERE maNguoiDung = ?";
			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setBoolean(1, accountDTO.getStatus());
			pstmt.setString(2, accountDTO.getDateUpdate());
			pstmt.setInt(3, accountDTO.getId());
			rowChange = pstmt.executeUpdate();
			JDBCUtil.getInstance().closeConnection(c);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rowChange;
	}

	// - Hàm lấy ra danh sách các người dùng
	@Override
	public ArrayList<AccountDTO> selectAll() {
		// - Khai báo biến chứa danh sách trả về
		ArrayList<AccountDTO> list = new ArrayList<>();

		// - Kết nối đến CSDL để truy vấn
		Connection c = JDBCUtil.getInstance().getConnection();
		try {
			String sql = "SELECT * FROM Karaoke.NguoiDung;";
			PreparedStatement pstmt = c.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				AccountDTO accountDTO = new AccountDTO(rs.getInt("maNguoiDung"), rs.getString("hoVaTen"),
						rs.getString("soDienThoai"), rs.getString("email"), rs.getString("diaChi"),
						rs.getString("tenTaiKhoan"), rs.getString("matKhau"), rs.getString("maQuyen"),
						rs.getBoolean("trangThai"), rs.getString("ngayCapNhat"));
				list.add(accountDTO);
			}
			JDBCUtil.getInstance().closeConnection(c);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	// - Hàm lấy ra danh sách các người dùng dựa trên 1 điều kiện
	@Override
	public ArrayList<AccountDTO> selectAllByCondition(String[] join, String condition, String order) {
		// - Khai báo biến chứa danh sách trả về
		ArrayList<AccountDTO> list = new ArrayList<>();

		// - Kết nối đến CSDL để truy vấn
		Connection c = JDBCUtil.getInstance().getConnection();
		try {
			String sql = String.format("SELECT * FROM Karaoke.NguoiDung%s%s%s;",
					join != null ? CommonDAL.getJoinValues(join) : "", condition != null ? "\nWHERE " + condition : "",
					order != null ? "\nORDER BY " + order : "");
			PreparedStatement pstmt = c.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				AccountDTO accountDTO = new AccountDTO(rs.getInt("maNguoiDung"), rs.getString("hoVaTen"),
						rs.getString("soDienThoai"), rs.getString("email"), rs.getString("diaChi"),
						rs.getString("tenTaiKhoan"), rs.getString("matKhau"), rs.getString("maQuyen"),
						rs.getBoolean("trangThai"), rs.getString("ngayCapNhat"));
				list.add(accountDTO);
			}

			JDBCUtil.getInstance().closeConnection(c);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	// - Hàm lấy ra một người dùng dựa trên mã người dùng đó
	@Override
	public AccountDTO selectOneById(String id) {
		// - Khai báo biến chứa danh sách trả về
		AccountDTO accountDTO = null;

		// - Kết nối đến CSDL để truy vấn
		Connection c = JDBCUtil.getInstance().getConnection();
		try {
			String sql = String.format("SELECT * FROM Karaoke.NguoiDung \nWHERE maNguoiDung = %s;",
					Integer.parseInt(id));
			PreparedStatement pstmt = c.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				accountDTO = new AccountDTO(rs.getInt("maNguoiDung"), rs.getString("hoVaTen"),
						rs.getString("soDienThoai"), rs.getString("email"), rs.getString("diaChi"),
						rs.getString("tenTaiKhoan"), rs.getString("matKhau"), rs.getString("maQuyen"),
						rs.getBoolean("trangThai"), rs.getString("ngayCapNhat"));
			}
			JDBCUtil.getInstance().closeConnection(c);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return accountDTO;
	}

}
