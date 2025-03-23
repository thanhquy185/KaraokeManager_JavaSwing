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
			String sql = "INSERT INTO Karaoke.HoaDon(maHoaDon, ngayLapHD, maPhong, maNhanVien, maKhachHang, maKhuyenMai, tongGio, tongTien, trangThai, ngayCapNhat)"
					+ "\nVALUES(?, ?, ?, ?, ?, ?, ?, ?, ?);";
			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, orderDTO.getId());
			pstmt.setString(2, orderDTO.getDateOrder());
			pstmt.setString(3, orderDTO.getRoomId());
			pstmt.setString(4, orderDTO.getCustomerId());
			pstmt.setInt(5, orderDTO.getEmployeeId());
			pstmt.setString(6, orderDTO.getDiscountId());
			pstmt.setInt(7, orderDTO.getTime());
			pstmt.setLong(8, orderDTO.getCost());
			pstmt.setBoolean(9, orderDTO.getStatus());
			pstmt.setString(10, orderDTO.getDateUpdate());
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
					+ "\nSET ngayLapHD = ?, maPhong = ?, maNhanVien = ?, maKhachHang = ?, maKhuyenMai = ?, tongGio = ?, tongTien = ?, trangThai = ?, ngayCapNhat = ?"
					+ "\nWHERE maHoaDon = ?";
			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setString(1, orderDTO.getDateOrder());
			pstmt.setString(2, orderDTO.getRoomId());
			pstmt.setInt(3, orderDTO.getEmployeeId());
			pstmt.setString(4, orderDTO.getCustomerId());
			pstmt.setString(5, orderDTO.getDiscountId());
			pstmt.setInt(6, orderDTO.getTime());
			pstmt.setLong(7, orderDTO.getCost());
			pstmt.setBoolean(8, orderDTO.getStatus());
			pstmt.setString(9, orderDTO.getDateUpdate());
			pstmt.setInt(10, orderDTO.getId());
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
			String sql = "UPDATE Karaoke.HoaDon" + "\nSET trangThai = ?, ngayCapNhat = ?" + "\nWHERE maHoaDon = ?";
			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setBoolean(1, orderDTO.getStatus());
			pstmt.setString(2, orderDTO.getDateUpdate());
			pstmt.setInt(3, orderDTO.getId());
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
				OrderDTO orderDTO = new OrderDTO(rs.getInt("maHoaDon"), rs.getString("ngayLapHD"),
						rs.getString("maPhong"), rs.getInt("maNhanVien"), rs.getString("maKhachHang"),
						rs.getString("maKhuyenMai"), rs.getInt("tongGio"), rs.getLong("tongTien"),
						rs.getBoolean("trangThai"), rs.getString("ngayCapNhat"));
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
				OrderDTO orderDTO =  new OrderDTO(rs.getInt("maHoaDon"), rs.getString("ngayLapHD"),
						rs.getString("maPhong"), rs.getInt("maNhanVien"), rs.getString("maKhachHang"),
						rs.getString("maKhuyenMai"), rs.getInt("tongGio"), rs.getLong("tongTien"),
						rs.getBoolean("trangThai"), rs.getString("ngayCapNhat"));
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
			String sql = String.format("SELECT * FROM Karaoke.HoaDon \nWHERE maPhong = %s;", id);
			PreparedStatement pstmt = c.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				orderDTO =  new OrderDTO(rs.getInt("maHoaDon"), rs.getString("ngayLapHD"),
						rs.getString("maPhong"), rs.getInt("maNhanVien"), rs.getString("maKhachHang"),
						rs.getString("maKhuyenMai"), rs.getInt("tongGio"), rs.getLong("tongTien"),
						rs.getBoolean("trangThai"), rs.getString("ngayCapNhat"));
			}
			JDBCUtil.getInstance().closeConnection(c);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return orderDTO;
	}

}
