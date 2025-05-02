package DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.OrderDetailDTO;

public class OrderDetailDAL implements DAL<OrderDetailDTO> {
	// Methods
	// - Hàm thêm một CTHD
	@Override
	public int insert(OrderDetailDTO orderDetailDTO) {
		// - Biến chứa số dòng đã được thêm
		int rowChange = 0;

		// - Kết nối đến CSDL để truy vấn
		String sql = "INSERT INTO Karaoke.CTHD(maHoaDon, maMonAn, giaBan, soLuong)"
				+ "\nVALUES(?, ?, ?, ?);";
		try (Connection c = JDBCUtil.getInstance().getConnection();
				PreparedStatement pstmt = c.prepareStatement(sql)) {
			pstmt.setInt(1, orderDetailDTO.getOrderId());
			pstmt.setString(2, orderDetailDTO.getFoodId());
			pstmt.setLong(3, orderDetailDTO.getPrice());
			pstmt.setLong(4, orderDetailDTO.getQuantity());
			rowChange = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rowChange;
	}

	// - Hàm thay đổi một CTHD
	@Override
	public int update(OrderDetailDTO orderDetailDTO) {
		// - Biến chứa số dòng đã được thêm
		int rowChange = 0;

		// - Kết nối đến CSDL để truy vấn
		String sql = "UPDATE Karaoke.CTHD" + "\nSET giaBan = ?, soLuong = ?"
				+ "\nWHERE maHoaDon = ? AND maMonAn = ?";
		try (Connection c = JDBCUtil.getInstance().getConnection();
				PreparedStatement pstmt = c.prepareStatement(sql)) {
			pstmt.setLong(1, orderDetailDTO.getPrice());
			pstmt.setLong(2, orderDetailDTO.getQuantity());
			pstmt.setInt(3, orderDetailDTO.getOrderId());
			pstmt.setString(4, orderDetailDTO.getFoodId());
			rowChange = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rowChange;
	}

	// - Hàm khoá một CTHD
	@Override
	public int lock(OrderDetailDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	// - Hàm lấy ra danh sách các CTHD
	@Override
	public ArrayList<OrderDetailDTO> selectAll() {
		// - Khai báo biến chứa danh sách trả về
		ArrayList<OrderDetailDTO> list = new ArrayList<>();

		// - Kết nối đến CSDL để truy vấn
		Connection c = JDBCUtil.getInstance().getConnection();
		try {
			String sql = "SELECT * FROM Karaoke.CTHD;";
			PreparedStatement pstmt = c.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				OrderDetailDTO orderDetailDTO = new OrderDetailDTO(rs.getInt("maHoaDon"), rs.getString("maMonAn"),
						rs.getLong("giaBan"), rs.getLong("soLuong"));
				list.add(orderDetailDTO);
			}
			JDBCUtil.getInstance().closeConnection(c);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	// - Hàm lấy ra danh sách các hóa đơn dựa trên 1 điều kiện
	@Override
	public ArrayList<OrderDetailDTO> selectAllByCondition(String[] join, String condition, String order) {
		// - Khai báo biến chứa danh sách trả về
		ArrayList<OrderDetailDTO> list = new ArrayList<>();

		// - Kết nối đến CSDL để truy vấn
		Connection c = JDBCUtil.getInstance().getConnection();
		try {
			String sql = String.format("SELECT * FROM Karaoke.CTHD%s%s%s;",
					join != null ? CommonDAL.getJoinValues(join) : "", condition != null ? "\nWHERE " + condition : "",
					order != null ? "\nORDER BY " + order : "");
			PreparedStatement pstmt = c.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				OrderDetailDTO orderDetailDTO = new OrderDetailDTO(rs.getInt("maHoaDon"), rs.getString("maMonAn"),
						rs.getLong("giaBan"), rs.getLong("soLuong"));
				list.add(orderDetailDTO);
			}
			JDBCUtil.getInstance().closeConnection(c);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	// - Hàm lấy ra danh sách các chi tiết hoá đơn theo mã hoá đơn
	public ArrayList<OrderDetailDTO> selectAllByOrderId(String OrderId) {
		ArrayList<OrderDetailDTO> list = new ArrayList<>();
		String sql = "SELECT maHoaDon, maMonAn, giaBan, soLuong FROM karaoke.cthd"
				+ (OrderId != null ? " WHERE maHoaDon = " + OrderId + ";" : ";");
		try (Connection c = JDBCUtil.getInstance().getConnection();
				PreparedStatement pstmt = c.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {
			while (rs.next()) {
				OrderDetailDTO dto = new OrderDetailDTO(
						rs.getInt("maHoaDon"),
						rs.getString("maMonAn"),
						rs.getLong("giaBan"),
						rs.getLong("soLuong"));
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// - Hàm lấy ra một chi tiết hoá đơn dựa trên mã hóa đơn đó
	@Override
	public OrderDetailDTO selectOneById(String id) {
		// - Khai báo biến chứa danh sách trả về
		OrderDetailDTO OrderDetailDTO = null;

		// - Kết nối đến CSDL để truy vấn
		Connection c = JDBCUtil.getInstance().getConnection();
		try {
			String sql = String.format("SELECT * FROM Karaoke.CTHD \nWHERE maHoaDon = %s;", id);
			PreparedStatement pstmt = c.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				OrderDetailDTO = new OrderDetailDTO(rs.getInt("maHoaDon"), rs.getString("maMonAn"),
						rs.getLong("giaBan"), rs.getLong("soLuong"));
			}
			JDBCUtil.getInstance().closeConnection(c);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return OrderDetailDTO;
	}
}
