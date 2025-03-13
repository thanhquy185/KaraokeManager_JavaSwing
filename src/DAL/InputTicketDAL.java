package DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import DTO.InputTicketDTO;

public class InputTicketDAL implements DAL<InputTicketDTO> {
	// Methods
	// - Hàm thêm một phiếu nhập
	@Override
	public int insert(InputTicketDTO inputTicketDTO) {

		return 0;
	}

	// - Hàm thay đổi một phiếu nhập
	@Override
	public int update(InputTicketDTO inputTicketDTO) {

		return 0;
	}

	// - Hàm khoá một phiếu nhập
	@Override
	public int lock(InputTicketDTO inputTicketDTO) {

		return 0;
	}

	// - Hàm lấy ra danh sách các phiếu nhập
	@Override
	public ArrayList<InputTicketDTO> selectAll() {
		// - Khai báo biến chứa danh sách trả về
		ArrayList<InputTicketDTO> list = new ArrayList<>();

		// - Kết nối đến CSDL để truy vấn
		Connection c = JDBCUtil.getInstance().getConnection();
		try {
			String sql = "SELECT * FROM Karaoke.PhieuNhap;";
			PreparedStatement pstmt = c.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				InputTicketDTO inputTicketDTO = new InputTicketDTO(rs.getInt("maPhieuNhap"),
						rs.getString("ngayLapPN"), rs.getString("maNCC"), rs.getLong("tongTien"),
						rs.getBoolean("trangThai"), rs.getString("ngayCapNhat"));
				list.add(inputTicketDTO);
			}
			JDBCUtil.getInstance().closeConnection(c);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	// - Hàm lấy ra danh sách các phiếu nhập dựa trên 1 điều kiện
	@Override
	public ArrayList<InputTicketDTO> selectAllByCondition(String[] join, String condition, String order) {
		// - Khai báo biến chứa danh sách trả về
		ArrayList<InputTicketDTO> list = new ArrayList<>();

		// - Kết nối đến CSDL để truy vấn
		Connection c = JDBCUtil.getInstance().getConnection();
		try {
			String sql = String.format("SELECT * FROM Karaoke.PhieuNhap %s %s %s;",
					join != null ? CommonDAL.getJoinValues(join) : "", condition != null ? "\nWHERE " + condition : "",
					order != null ? "\nORDER BY " + order : "");
			PreparedStatement pstmt = c.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				InputTicketDTO inputTicketDTO = new InputTicketDTO(rs.getInt("maPhieuNhap"),
						rs.getString("ngayLapPN"), rs.getString("maNCC"), rs.getLong("tongTien"),
						rs.getBoolean("trangThai"), rs.getString("ngayCapNhat"));
				list.add(inputTicketDTO);
			}
			JDBCUtil.getInstance().closeConnection(c);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	// - Hàm lấy ra một phiếu nhập dựa trên mã phiếu nhập đó
	@Override
	public InputTicketDTO selectOneById(String id) {
		// - Khai báo biến chứa danh sách trả về
		InputTicketDTO inputTicketDTO = null;

		// - Kết nối đến CSDL để truy vấn
		Connection c = JDBCUtil.getInstance().getConnection();
		try {
			String sql = String.format("SELECT * FROM Karaoke.PhieuNhap \nWHERE maPhieuNhap = '%s';", id);
			PreparedStatement pstmt = c.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				inputTicketDTO = new InputTicketDTO(rs.getInt("maPhieuNhap"),
						rs.getString("ngayLapPN"), rs.getString("maNCC"), rs.getLong("tongTien"),
						rs.getBoolean("trangThai"), rs.getString("ngayCapNhat"));
			}
			JDBCUtil.getInstance().closeConnection(c);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return inputTicketDTO;
	}
}
