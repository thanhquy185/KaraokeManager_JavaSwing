package DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.OrderDTO;
import DTO.OrderDTO;

public class OrderDAL implements DAL<OrderDTO> {
	// Methods
	// - Hàm thêm một hóa đơn
	@Override
	public int insert(OrderDTO orderDTO) {
		// - Biến chứa số dòng đã được thêm
		int rowChange = 0;

		// - Kết nối đến CSDL để truy vấn
		Connection c = JDBCUtil.getInstance().getConnection();
		try {
			String sql = "INSERT INTO Karaoke.HoaDon(maHoaDon, thoiGianTaoDon, thoiGianBatDau, thoiGianKetThuc, maPhong, maNhanVien, maKhachHang, tongTien, trangThai)"
					+ "\nVALUES(?, ?, ?, ?, ?, ?, ?, ?, ?);";
			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, orderDTO.getId());
			pstmt.setString(2, orderDTO.getTimeCreate());
			pstmt.setString(3, orderDTO.getTimeStart());
			pstmt.setString(4, orderDTO.getTimeEnd());
			pstmt.setString(5, orderDTO.getRoomId());
			pstmt.setInt(6, orderDTO.getEmployeeId());
			pstmt.setString(7, orderDTO.getCustomerId());
			pstmt.setLong(8, orderDTO.getTotalPrice());
			pstmt.setInt(9, orderDTO.getStatus());
			rowChange = pstmt.executeUpdate();
			JDBCUtil.getInstance().closeConnection(c);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rowChange;
	}

	// - Hàm cập nhật một hóa đơn
	@Override
	public int update(OrderDTO orderDTO) {
		// - Biến chứa số dòng đã được thêm
		int rowChange = 0;

		// - Kết nối đến CSDL để truy vấn
		Connection c = JDBCUtil.getInstance().getConnection();
		try {
			String sql = "UPDATE Karaoke.HoaDon"
					+ "\nSET thoiGianKetThuc = ?, tongTien = ?, trangThai = ?"
					+ "\nWHERE maHoaDon = ?";
			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setString(1, orderDTO.getTimeEnd());
			pstmt.setLong(2, orderDTO.getTotalPrice());
			pstmt.setInt(3, orderDTO.getStatus());
			pstmt.setInt(4, orderDTO.getId());
			rowChange = pstmt.executeUpdate();
			JDBCUtil.getInstance().closeConnection(c);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rowChange;
	}

	// - Hàm khoá một hóa đơn
	@Override
	public int lock(OrderDTO orderDTO) {
		// - Biến chứa số dòng đã được thêm
		int rowChange = 0;

		// - Kết nối đến CSDL để truy vấn
		Connection c = JDBCUtil.getInstance().getConnection();
		try {
			String sql = "UPDATE Karaoke.HoaDon" + "\nSET trangThai = ?" + "\nWHERE maHoaDon = ?";
			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, orderDTO.getStatus());
			pstmt.setInt(2, orderDTO.getId());
			rowChange = pstmt.executeUpdate();
			JDBCUtil.getInstance().closeConnection(c);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rowChange;
	}

	// - Hàm lấy ra danh sách các hóa đơn
	@Override
	public ArrayList<OrderDTO> selectAll() {
		// - Khai báo biến chứa danh sách trả về
		ArrayList<OrderDTO> list = new ArrayList<>();

		// - Kết nối đến CSDL để truy vấn
		Connection c = JDBCUtil.getInstance().getConnection();
		try {
			String sql = "SELECT * FROM Karaoke.HoaDon;";
			PreparedStatement pstmt = c.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				OrderDTO orderDTO = new OrderDTO(rs.getInt("maHoaDon"), rs.getString("thoiGianTaoDon"),
						rs.getString("thoiGianBatDau"), rs.getString("thoiGianKetThuc"),
						rs.getString("maPhong"), rs.getInt("maNhanVien"), rs.getString("maKhachHang"),
						rs.getLong("tongTien"),
						rs.getInt("trangThai"));
				list.add(orderDTO);
			}
			JDBCUtil.getInstance().closeConnection(c);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	// - Hàm lấy ra danh sách các hóa đơn dựa trên 1 điều kiện
	@Override
	public ArrayList<OrderDTO> selectAllByCondition(String[] join, String condition, String order) {
		// - Khai báo biến chứa danh sách trả về
		ArrayList<OrderDTO> list = new ArrayList<>();

		// - Kết nối đến CSDL để truy vấn
		Connection c = JDBCUtil.getInstance().getConnection();
		try {
			String sql = String.format("SELECT * FROM Karaoke.HoaDon%s%s%s;",
					join != null ? CommonDAL.getJoinValues(join) : "", condition != null ? "\nWHERE " + condition : "",
					order != null ? "\nORDER BY " + order : "");
			PreparedStatement pstmt = c.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				OrderDTO orderDTO = new OrderDTO(rs.getInt("maHoaDon"), rs.getString("thoiGianTaoDon"),
						rs.getString("thoiGianBatDau"), rs.getString("thoiGianKetThuc"),
						rs.getString("maPhong"), rs.getInt("maNhanVien"), rs.getString("maKhachHang"),
						rs.getLong("tongTien"),
						rs.getInt("trangThai"));
				list.add(orderDTO);
			}
			JDBCUtil.getInstance().closeConnection(c);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	// - Hàm lấy ra một hóa đơn dựa trên mã hóa đơn đó
	@Override
	public OrderDTO selectOneById(String id) {
		// - Khai báo biến chứa danh sách trả về
		OrderDTO orderDTO = null;

		// - Kết nối đến CSDL để truy vấn
		Connection c = JDBCUtil.getInstance().getConnection();
		try {
			String sql = String.format("SELECT * FROM Karaoke.HoaDon \nWHERE maHoaDon = %s;", id);
			PreparedStatement pstmt = c.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				orderDTO = new OrderDTO(rs.getInt("maHoaDon"), rs.getString("thoiGianTaoDon"),
						rs.getString("thoiGianBatDau"), rs.getString("thoiGianKetThuc"),
						rs.getString("maPhong"), rs.getInt("maNhanVien"), rs.getString("maKhachHang"),
						rs.getLong("tongTien"),
						rs.getInt("trangThai"));
			}
			JDBCUtil.getInstance().closeConnection(c);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return orderDTO;
	}

}
