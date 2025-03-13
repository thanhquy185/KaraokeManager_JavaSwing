package DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import DTO.InputTicketDetailDTO;

public class InputTicketDetailDAL implements DAL<InputTicketDetailDTO> {
	// Methods
		// - Hàm thêm một chi tiết phiếu nhập
		@Override
		public int insert(InputTicketDetailDTO InputTicketDetailDTO) {

			return 0;
		}

		// - Hàm thay đổi một chi tiết phiếu nhập
		@Override
		public int update(InputTicketDetailDTO InputTicketDetailDTO) {

			return 0;
		}

		// - Hàm khoá một chi tiết phiếu nhập
		@Override
		public int lock(InputTicketDetailDTO InputTicketDetailDTO) {

			return 0;
		}

		// - Hàm lấy ra danh sách chi tiết các phiếu nhập
		@Override
		public ArrayList<InputTicketDetailDTO> selectAll() {
			// - Khai báo biến chứa danh sách trả về
			ArrayList<InputTicketDetailDTO> list = new ArrayList<>();

			// - Kết nối đến CSDL để truy vấn
			Connection c = JDBCUtil.getInstance().getConnection();
			try {
				String sql = "SELECT * FROM Karaoke.CTPN;";
				PreparedStatement pstmt = c.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					InputTicketDetailDTO inputTicketDetailDTO = new InputTicketDetailDTO(rs.getInt("maPhieuNhap"),
							rs.getString("maNguyenLieu"), rs.getLong("giaNhap"), rs.getInt("soLuong"));
					list.add(inputTicketDetailDTO);
				}
				JDBCUtil.getInstance().closeConnection(c);
			} catch (Exception e) {
				e.printStackTrace();
			}

			return list;
		}

		// - Hàm lấy ra danh sách các chi tiết phiếu nhập dựa trên 1 điều kiện
		@Override
		public ArrayList<InputTicketDetailDTO> selectAllByCondition(String[] join, String condition, String order) {
			// - Khai báo biến chứa danh sách trả về
			ArrayList<InputTicketDetailDTO> list = new ArrayList<>();

			// - Kết nối đến CSDL để truy vấn
			Connection c = JDBCUtil.getInstance().getConnection();
			try {
				String sql = String.format("SELECT * FROM Karaoke.CTPN %s %s %s;",
						join != null ? CommonDAL.getJoinValues(join) : "", condition != null ? "\nWHERE " + condition : "",
						order != null ? "\nORDER BY " + order : "");
				PreparedStatement pstmt = c.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					InputTicketDetailDTO inputTicketDetailDTO = new InputTicketDetailDTO(rs.getInt("maPhieuNhap"),
							rs.getString("maNguyenLieu"), rs.getLong("giaNhap"), rs.getInt("soLuong"));
					list.add(inputTicketDetailDTO);
				}
				JDBCUtil.getInstance().closeConnection(c);
			} catch (Exception e) {
				e.printStackTrace();
			}

			return list;
		}

		// - Hàm lấy ra một phiếu nhập dựa trên mã phiếu nhập đó
		@Override
		public InputTicketDetailDTO selectOneById(String id) {
			return null;
		}
}
