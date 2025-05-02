package DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.CustomerTypeDTO;

public class CustomerTypeDAL implements DAL<CustomerTypeDTO> {
	// Methods
	// - Hàm thêm một loại khách hàng
	@Override
	public int insert(CustomerTypeDTO customerTypeDTO) {
		// - Biến chứa số dòng đã được thêm
		int rowChange = 0;

		// - Kết nối đến CSDL để truy vấn
		Connection c = JDBCUtil.getInstance().getConnection();
		try {
			String sql = "INSERT INTO Karaoke.LoaiKhachHang(maLoaiKhachHang, tenLoaiKhachHang, uuDai)"
					+ "\nVALUES(?, ?, ?, ?);";
			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setString(1, customerTypeDTO.getId());
			pstmt.setString(2, customerTypeDTO.getName());
			pstmt.setInt(3, customerTypeDTO.getDiscount());
			rowChange = pstmt.executeUpdate();
			JDBCUtil.getInstance().closeConnection(c);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rowChange;
	}

	// - Hàm cập nhật một loại khách hàng
	@Override
	public int update(CustomerTypeDTO customerTypeDTO) {
		// - Biến chứa số dòng đã được thêm
		int rowChange = 0;

		// - Kết nối đến CSDL để truy vấn
		Connection c = JDBCUtil.getInstance().getConnection();
		try {
			String sql = "UPDATE Karaoke.LoaiKhachHang" + "\nSET tenLoaiKhachHang = ?, uuDai = ?"
					+ "\nWHERE maLoaiKhachHang = ?";
			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setString(1, customerTypeDTO.getName());
			pstmt.setInt(2, customerTypeDTO.getDiscount());
			pstmt.setString(3, customerTypeDTO.getId());
			rowChange = pstmt.executeUpdate();
			JDBCUtil.getInstance().closeConnection(c);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rowChange;
	}

	// - Hàm khoá một loại khách hàng
	@Override
	public int lock(CustomerTypeDTO customerTypeDTO) {
		// // - Biến chứa số dòng đã được thêm
		// int rowChange = 0;

		// // - Kết nối đến CSDL để truy vấn
		// Connection c = JDBCUtil.getInstance().getConnection();
		// try {
		// String sql = "UPDATE Karaoke.LoaiKhachHang" + "\nSET trangThai = ?,
		// ngayCapNhat = ?"
		// + "\nWHERE maLoaiKhachHang = ?";
		// PreparedStatement pstmt = c.prepareStatement(sql);
		// pstmt.setString(1, customerTypeDTO.getId());
		// rowChange = pstmt.executeUpdate();
		// JDBCUtil.getInstance().closeConnection(c);
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }

		return 0;
	}

	// - Hàm lấy ra danh sách các loại khách hàng
	@Override
	public ArrayList<CustomerTypeDTO> selectAll() {
		// - Khai báo biến chứa danh sách trả về
		ArrayList<CustomerTypeDTO> list = new ArrayList<>();

		// - Kết nối đến CSDL để truy vấn
		Connection c = JDBCUtil.getInstance().getConnection();
		try {
			String sql = "SELECT * FROM Karaoke.LoaiKhachHang;";
			PreparedStatement pstmt = c.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				CustomerTypeDTO customerTypeDTO = new CustomerTypeDTO(rs.getString("maLoaiKhachHang"),
						rs.getString("tenLoaiKhachHang"), rs.getInt("uuDai"));
				list.add(customerTypeDTO);
			}
			JDBCUtil.getInstance().closeConnection(c);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	// - Hàm lấy ra danh sách các loại khách hàng dựa trên 1 điều kiện
	@Override
	public ArrayList<CustomerTypeDTO> selectAllByCondition(String[] join, String condition, String order) {
		// - Khai báo biến chứa danh sách trả về
		ArrayList<CustomerTypeDTO> list = new ArrayList<>();

		// - Kết nối đến CSDL để truy vấn
		Connection c = JDBCUtil.getInstance().getConnection();
		try {
			String sql = String.format("SELECT * FROM Karaoke.LoaiKhachHang%s%s%s;",
					join != null ? CommonDAL.getJoinValues(join) : "", condition != null ? "\nWHERE " + condition : "",
					order != null ? "\nORDER BY " + order : "");
			PreparedStatement pstmt = c.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				CustomerTypeDTO customerTypeDTO = new CustomerTypeDTO(rs.getString("maLoaiKhachHang"),
						rs.getString("tenLoaiKhachHang"), rs.getInt("uuDai"));
				list.add(customerTypeDTO);
			}
			JDBCUtil.getInstance().closeConnection(c);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	// - Hàm lấy ra một loại khách hàng dựa trên mã loại khách hàng đó
	@Override
	public CustomerTypeDTO selectOneById(String id) {
		// - Khai báo biến chứa danh sách trả về
		CustomerTypeDTO customerTypeDTO = null;

		// - Kết nối đến CSDL để truy vấn
		Connection c = JDBCUtil.getInstance().getConnection();
		try {
			String sql = String.format("SELECT * FROM Karaoke.LoaiKhachHang \nWHERE maLoaiKhachHang = '%s';", id);
			PreparedStatement pstmt = c.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				customerTypeDTO = new CustomerTypeDTO(rs.getString("maLoaiKhachHang"),
						rs.getString("tenLoaiKhachHang"), rs.getInt("uuDai"));
			}
			JDBCUtil.getInstance().closeConnection(c);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return customerTypeDTO;
	}
}
