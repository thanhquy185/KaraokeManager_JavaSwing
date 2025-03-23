package DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import DTO.OrderDetailDTO;
import DTO.OrderDetailDTO;

public class OrderDetailDAL implements DAL<OrderDetailDTO> {
	// Methods
		// - Hàm thêm một loại hóa đơn
		@Override
		public int insert(OrderDetailDTO t) {
			// TODO Auto-generated method stub
			return 0;
		}

		// - Hàm thay đổi một loại hóa đơn
		@Override
		public int update(OrderDetailDTO t) {
			// TODO Auto-generated method stub
			return 0;
		}

		// - Hàm khoá một loại hóa đơn
		@Override
		public int lock(OrderDetailDTO t) {
			// TODO Auto-generated method stub
			return 0;
		}

		// - Hàm lấy ra danh sách các loại hóa đơn
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
					OrderDetailDTO orderDetailDTO = new OrderDetailDTO(rs.getInt("maHoaDon"), rs.getString("maSanPham"), rs.getInt("soLuong"));
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
					OrderDetailDTO orderDetailDTO = new OrderDetailDTO(rs.getInt("maHoaDon"), rs.getString("maSanPham"), rs.getInt("soLuong"));
					list.add(orderDetailDTO);
				}
				JDBCUtil.getInstance().closeConnection(c);
			} catch (Exception e) {
				e.printStackTrace();
			}

			return list;
		}

		// // - Hàm lấy ra một chi tiết hoá đơn dựa trên mã loại hóa đơn đó
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
					OrderDetailDTO = new OrderDetailDTO(rs.getInt("maHoaDon"), rs.getString("maSanPham"), rs.getInt("soLuong"));
				}
				JDBCUtil.getInstance().closeConnection(c);
			} catch (Exception e) {
				e.printStackTrace();
			}

			return OrderDetailDTO;
		}
}
