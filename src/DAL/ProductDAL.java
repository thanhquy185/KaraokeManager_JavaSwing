package DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.ProductDTO;

public class ProductDAL implements DAL<ProductDTO>{
	// Methods
	
	// - Hàm thêm một sản phẩm
	@Override
	public int insert(ProductDTO productDTO) {

		// - Biến chứa số dòng được thêm
        int rowChange = 0;

        //Kết nối CSDL để truy vấn
        try(Connection c = JDBCUtil.getInstance().getConnection();
                PreparedStatement pstmt = c.prepareStatement("INSERT INTO Karaoke.Sanpham(maSanPham, tenSanPham, maLoaiSanPham, giaBan, hinhAnh, trangThai, ngayCapNhat)"
                        + "\nVALUES(?,?,?,?,?,?,?)"))
        {
            pstmt.setString(1,productDTO.getId());
            pstmt.setString(2,productDTO.getName());
            pstmt.setString(3,productDTO.getProductTypeId());
            pstmt.setLong(4,productDTO.getPrice());
            pstmt.setString(5,productDTO.getImage());
            pstmt.setBoolean(6,productDTO.getStatus());
            pstmt.setString(7,productDTO.getDateUpdate());
            rowChange = pstmt.executeUpdate();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return rowChange;
	}

	// - Hàm thay đổi một sản phẩm
	@Override
	public int update(ProductDTO productDTO) {

		int rowChange = 0;

        //Kết nối CSDL để truy vấn
        try(Connection c = JDBCUtil.getInstance().getConnection();
                PreparedStatement pstmt = c.prepareStatement("UPDATE Karaoke.Sanpham" 
                        + "\nSET tenSanPham = ?, maLoaiSanPham = ?, giaBan = ?, hinhAnh = ?, trangThai = ?, ngayCapNhat = ?"
                        + "\n WHERE maSanPham = ?"))
        {
            
            pstmt.setString(1,productDTO.getName());
            pstmt.setString(2,productDTO.getProductTypeId());
            pstmt.setLong(3,productDTO.getPrice());
            pstmt.setString(4,productDTO.getImage());
            pstmt.setBoolean(5,productDTO.getStatus());
            pstmt.setString(6,productDTO.getDateUpdate());
            pstmt.setString(7,productDTO.getId());
            rowChange = pstmt.executeUpdate();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return rowChange;
	}

	// - Hàm khoá một sản phẩm
	@Override
	public int lock(ProductDTO productDTO) {

		int rowChange = 0;

        //Kết nối CSDL để truy vấn
        try(Connection c = JDBCUtil.getInstance().getConnection();
                PreparedStatement pstmt = c.prepareStatement("UPDATE Karaoke.SanPham" 
                        +"\nSET trangThai = ?, ngayCapNhat = ?" 
                        + "\nWHERE maSanPham = ?"))
        {
            pstmt.setBoolean(1,productDTO.getStatus());
            pstmt.setString(2,productDTO.getDateUpdate());
            pstmt.setString(3,productDTO.getId());
            rowChange = pstmt.executeUpdate();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return rowChange;
	}

	// - Hàm lấy ra danh sách các sản phẩm
	@Override
	public ArrayList<ProductDTO> selectAll() {
		// - Khai báo biến chứa danh sách trả về
		ArrayList<ProductDTO> list = new ArrayList<>();

		// - Kết nối đến CSDL để truy vấn
		String sql = String.format("SELECT * FROM Karaoke.SanPham;");
		try(Connection c = JDBCUtil.getInstance().getConnection();
				PreparedStatement pstmt = c.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) 
		{
			while (rs.next()) {
				ProductDTO productDTO = new ProductDTO(rs.getString("maSanPham"), rs.getString("tenSanPham"),
						rs.getString("maLoaiSanPham"), rs.getLong("giaBan"), rs.getString("hinhAnh"),
						rs.getBoolean("trangThai"), rs.getString("ngayCapNhat"));
				list.add(productDTO);
			}
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
		String sql = String.format("SELECT * FROM Karaoke.SanPham %s %s %s;",
					join != null ? CommonDAL.getJoinValues(join) : "", 
					condition != null ? "\nWHERE " + condition : "",
					order != null ? "\nORDER BY " + order : "");
		try(Connection c = JDBCUtil.getInstance().getConnection();
				PreparedStatement pstmt = c.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) 
		{
			while (rs.next()) {
				ProductDTO productDTO = new ProductDTO(rs.getString("maSanPham"), rs.getString("tenSanPham"),
						rs.getString("maLoaiSanPham"), rs.getLong("giaBan"), rs.getString("hinhAnh"),
						rs.getBoolean("trangThai"), rs.getString("ngayCapNhat"));
				list.add(productDTO);
			}
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
		String sql = "SELECT * FROM Karaoke.SanPham WHERE maSanPham = ?";
		try (Connection c = JDBCUtil.getInstance().getConnection();
     			PreparedStatement pstmt = c.prepareStatement(sql)) 
		{
    		pstmt.setString(1, id);
    		try (ResultSet rs = pstmt.executeQuery()) {
        		if (rs.next()) {
        			productDTO = new ProductDTO(
        			    rs.getString("maSanPham"),
        			    rs.getString("tenSanPham"),
        			    rs.getString("maLoaiSanPham"),
        			    rs.getLong("giaBan"),
        			    rs.getString("hinhAnh"),
        			    rs.getBoolean("trangThai"),
        			    rs.getString("ngayCapNhat")
        			);
        		}
    		}
		} catch (SQLException e) {
    		e.printStackTrace();
		}
		return productDTO;
	}
}
