package DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.PrivilegeDTO;
import DTO.ProductDTO;

public class ProductDAL implements DAL<ProductDTO>{
	// Methods
	// - Hàm thêm một sản phẩm
	@Override
	public int insert(ProductDTO productDTO) {

		return 0;
	}

	// - Hàm thay đổi một sản phẩm
	@Override
	public int update(ProductDTO productDTO) {

		return 0;
	}

	// - Hàm khoá một sản phẩm
	@Override
	public int lock(ProductDTO productDTO) {

		return 0;
	}

	// - Hàm lấy ra danh sách các sản phẩm
	@Override
	public ArrayList<ProductDTO> selectAll() {
		// - Khai báo biến chứa danh sách trả về
		ArrayList<ProductDTO> list = new ArrayList<>();

		// - Kết nối đến CSDL để truy vấn
		Connection c = JDBCUtil.getInstance().getConnection();
		try {
			String sql = "SELECT * FROM Karaoke.Quyen;";
			PreparedStatement pstmt = c.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				ProductDTO productDTO = new ProductDTO(rs.getString("maSanPham"), rs.getString("tenSanPham"),
						rs.getString("maLoaiSanPham"), rs.getLong("giaBan"), rs.getString("hinhAnh"),
						rs.getBoolean("trangThai"), rs.getString("ngayCapNhat"));
				list.add(productDTO);
			}
			JDBCUtil.getInstance().closeConnection(c);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	// - Hàm lấy ra danh sách các sản phẩm dựa trên 1 điều kiện
	@Override
	public ArrayList<ProductDTO> selectAllByCondition(String[] join, String condition, String order) {
		// - Khai báo biến chứa danh sách trả về
		ArrayList<ProductDTO> list = new ArrayList<>();

		// - Kết nối đến CSDL để truy vấn
		Connection c = JDBCUtil.getInstance().getConnection();
		try {
			String sql = String.format("SELECT * FROM Karaoke.Quyen %s %s %s;",
					join != null ? CommonDAL.getJoinValues(join) : "", condition != null ? "\nWHERE " + condition : "",
					order != null ? "\nORDER BY " + order : "");
			PreparedStatement pstmt = c.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				ProductDTO productDTO = new ProductDTO(rs.getString("maSanPham"), rs.getString("tenSanPham"),
						rs.getString("maLoaiSanPham"), rs.getLong("giaBan"), rs.getString("hinhAnh"),
						rs.getBoolean("trangThai"), rs.getString("ngayCapNhat"));
				list.add(productDTO);
			}
			JDBCUtil.getInstance().closeConnection(c);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	// - Hàm lấy ra một sản phẩm dựa trên mã sản phẩm đó
	@Override
	public ProductDTO selectOneById(String id) {
		// - Khai báo biến chứa danh sách trả về
		ProductDTO productDTO = null;

		// - Kết nối đến CSDL để truy vấn
		Connection c = JDBCUtil.getInstance().getConnection();
		try {
			String sql = String.format("SELECT * FROM Karaoke.SanPham \nWHERE maSanPham = '%s';", id);
			PreparedStatement pstmt = c.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				productDTO = new ProductDTO(rs.getString("maSanPham"), rs.getString("tenSanPham"),
						rs.getString("maLoaiSanPham"), rs.getLong("giaBan"), rs.getString("hinhAnh"),
						rs.getBoolean("trangThai"), rs.getString("ngayCapNhat"));
			}
			JDBCUtil.getInstance().closeConnection(c);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return productDTO;
	}
}
