package DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.CustomerDTO;

public class CustomerDAL implements DAL<CustomerDTO> {
	// - Thêm một khách hàng
	@Override
	public int insert(CustomerDTO customerDTO) {
		// - Biến chưa số dòng được thêm
		int rowChange = 0;

		// - Kết nối CSDL để truy vấn
		try (Connection c = JDBCUtil.getInstance().getConnection();
				PreparedStatement pstmt = c.prepareStatement(
						"INSERT INTO Karaoke.KhachHang(cccd, maLoaiKhachHang, hoVaTen, ngaySinh, gioiTinh, soDienThoai, email, diaChi, trangThai, ngayCapNhat)"
								+ "\nVALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?);")) {
			pstmt.setString(1, customerDTO.getIdCard());
			pstmt.setString(2, customerDTO.getType());
			pstmt.setString(3, customerDTO.getFullname());
			pstmt.setString(4, customerDTO.getBirthday());
			pstmt.setString(5, customerDTO.getGender());
			pstmt.setString(6, customerDTO.getPhone());
			pstmt.setString(7, customerDTO.getEmail());
			pstmt.setString(8, customerDTO.getAddress());
			pstmt.setBoolean(9, customerDTO.getStatus());
			pstmt.setString(10, customerDTO.getDateUpdate());
			rowChange = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rowChange;
	}

	// - Hàm cập nhật thông tin khách hàng
	@Override
	public int update(CustomerDTO customerDTO) {
		int rowChange = 0;

		// - Kết nối cơ sở dữ liệu để truy vấn
		try (Connection c = JDBCUtil.getInstance().getConnection();
				PreparedStatement pstmt = c.prepareStatement("UPDATE Karaoke.KhachHang"
						+ "\nSET maLoaiKhachHang = ?, hoVaTen = ?, ngaySinh = ?, gioiTinh = ?, soDienThoai = ?, email = ?, diaChi = ?, trangThai = ?, ngayCapNHat = ?"
						+ "\nWHERE cccd = ?")) {
			pstmt.setString(1, customerDTO.getType());
			pstmt.setString(2, customerDTO.getFullname());
			pstmt.setString(3, customerDTO.getBirthday());
			pstmt.setString(4, customerDTO.getGender());
			pstmt.setString(5, customerDTO.getPhone());
			pstmt.setString(6, customerDTO.getEmail());
			pstmt.setString(7, customerDTO.getAddress());
			pstmt.setBoolean(8, customerDTO.getStatus());
			pstmt.setString(9, customerDTO.getDateUpdate());
			pstmt.setString(10, customerDTO.getIdCard());
			rowChange = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rowChange;
	}

	// - Khóa khách hàng
	@Override
	public int lock(CustomerDTO customerDTO) {
		int rowChange = 0;

		// - Kết nối CSDL để truy vấn
		try (Connection c = JDBCUtil.getInstance().getConnection();
				PreparedStatement pstmt = c.prepareStatement(
						"UPDATE Karaoke.KhachHang" + "\nSET trangThai = ?, ngayCapNhat = ?" + "\nWHERE cccd = ?")) {
			pstmt.setBoolean(1, customerDTO.getStatus());
			pstmt.setString(2, customerDTO.getDateUpdate());
			pstmt.setString(3, customerDTO.getIdCard());
			rowChange = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rowChange;
	}

	// - Lấy danh sách khách hàng
	@Override
	public ArrayList<CustomerDTO> selectAll() {
		// Sử dụng ArrayList để lưu danh sách
		ArrayList<CustomerDTO> list = new ArrayList<>();

		// - Kết nối CSDL để truy vấn
		try (Connection c = JDBCUtil.getInstance().getConnection();
				PreparedStatement pstmt = c.prepareStatement("SELECT * FROM Karaoke.KhachHang;");
				ResultSet rs = pstmt.executeQuery()) {
			while (rs.next()) {
				CustomerDTO customerDTO = new CustomerDTO(rs.getString("cccd"), rs.getString("maLoaiKhachHang"),
						rs.getString("hoVaTen"), rs.getString("ngaySinh"), rs.getString("gioiTinh"),
						rs.getString("soDienThoai"), rs.getString("email"), rs.getString("diaChi"),
						rs.getBoolean("trangThai"), rs.getString("ngayCapNhat"));
				list.add(customerDTO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// - Hàm lọc danh sách dựa trên điều kiện
	@Override
	public ArrayList<CustomerDTO> selectAllByCondition(String[] join, String condition, String order) {
		// Sử dụng ArrayList để lưu danh sách
		ArrayList<CustomerDTO> list = new ArrayList<>();
		String sql = String.format("SELECT * FROM Karaoke.KhachHang%s%s%s;",
				join != null ? CommonDAL.getJoinValues(join) : "", condition != null ? "\nWHERE " + condition : "",
				order != null ? "\nORDER BY " + order : "");

		// - Kết nối CSDL để truy vấn
		try (Connection c = JDBCUtil.getInstance().getConnection();
				PreparedStatement pstmt = c.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {
			while (rs.next()) {
				CustomerDTO customerDTO = new CustomerDTO(rs.getString("cccd"), rs.getString("maLoaiKhachHang"),
						rs.getString("hoVaTen"), rs.getString("ngaySinh"), rs.getString("gioiTinh"),
						rs.getString("soDienThoai"), rs.getString("email"), rs.getString("diaChi"),
						rs.getBoolean("trangThai"), rs.getString("ngayCapNhat"));
				list.add(customerDTO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// - Hàm lọc ra khách hàng dựa trên số CCCD
	@Override
	public CustomerDTO selectOneById(String id) {
		if (id == null)
			return null;
		CustomerDTO customerDTO = null;

		// - Kết nối đến CSDL để truy vấn
		try (Connection c = JDBCUtil.getInstance().getConnection();
				PreparedStatement pstmt = c.prepareStatement("SELECT * FROM Karaoke.KhachHang \nWHERE cccd= ?")) {
			pstmt.setString(1, id);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					customerDTO = new CustomerDTO(rs.getString("cccd"), rs.getString("maLoaiKhachHang"),
							rs.getString("hoVaTen"), rs.getString("ngaySinh"), rs.getString("gioiTinh"),
							rs.getString("soDienThoai"), rs.getString("email"), rs.getString("diaChi"),
							rs.getBoolean("trangThai"), rs.getString("ngayCapNhat"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customerDTO;
	}
}
