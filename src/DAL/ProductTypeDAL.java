package DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.ProductTypeDTO;

public class ProductTypeDAL implements DAL<ProductTypeDTO> {
    // Methods
    // - Hàm thêm một loại sản phẩm
    @Override
	public int insert(ProductTypeDTO productTypeDTO) {
		// - Biến chứa số dòng đã được thêm
		int rowChange = 0;

		// - Kết nối đến CSDL để truy vấn
		String sql = "INSERT INTO Karaoke.LoaiSanPham(maLoaiSanPham, tenLoaiSanPham, trangThai, ngayCapNhat) VALUES(?, ?, ?, ?);";
        try (Connection c = JDBCUtil.getInstance().getConnection();
             PreparedStatement pstmt = c.prepareStatement(sql)) 
		{     
            pstmt.setString(1, productTypeDTO.getId());
            pstmt.setString(2, productTypeDTO.getName());
            pstmt.setBoolean(3, productTypeDTO.getStatus());
            pstmt.setString(4, productTypeDTO.getDateUpdate());

            rowChange = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return rowChange;
	}

	// - Hàm cập nhật một loại sản phẩm
	@Override
	public int update(ProductTypeDTO productTypeDTO) {
		// - Biến chứa số dòng đã được thêm
		int rowChange = 0;

		// - Kết nối đến CSDL để truy vấn
		String sql = "UPDATE Karaoke.LoaiSanPham SET tenLoaiSanPham = ?, trangThai = ?, ngayCapNhat = ? WHERE maLoaiSanPham = ?";
        try (Connection c = JDBCUtil.getInstance().getConnection();
             PreparedStatement pstmt = c.prepareStatement(sql)) {
             
            pstmt.setString(1, productTypeDTO.getName());
            pstmt.setBoolean(2, productTypeDTO.getStatus());
            pstmt.setString(3, productTypeDTO.getDateUpdate());
            pstmt.setString(4, productTypeDTO.getId());

            rowChange = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return rowChange;
	}

	// - Hàm khoá một loại sản phẩm
	@Override
	public int lock(ProductTypeDTO productTypeDTO) {
		// - Biến chứa số dòng đã được thêm
		int rowChange = 0;

		// - Kết nối đến CSDL để truy vấn
		String sql = "UPDATE Karaoke.LoaiSanPham SET trangThai = ?, ngayCapNhat = ? WHERE maLoaiSanPham = ?";

        try (Connection c = JDBCUtil.getInstance().getConnection();
             PreparedStatement pstmt = c.prepareStatement(sql)) {
             
            pstmt.setBoolean(1, productTypeDTO.getStatus());
            pstmt.setString(2, productTypeDTO.getDateUpdate());
            pstmt.setString(3, productTypeDTO.getId());

            rowChange = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return rowChange;
	}

	// - Hàm lấy ra danh sách các loại sản phẩm 
	@Override
	public ArrayList<ProductTypeDTO> selectAll() {
		// - Khai báo biến chứa danh sách trả về
		ArrayList<ProductTypeDTO> list = new ArrayList<>();

		// - Kết nối đến CSDL để truy vấn
		String sql = "SELECT * FROM Karaoke.LoaiSanPham;";
		try (Connection c = JDBCUtil.getInstance().getConnection();
             PreparedStatement pstmt = c.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
             
            while (rs.next()) {
                ProductTypeDTO productTypeDTO = new ProductTypeDTO(
                    rs.getString("maLoaiSanPham"),
                    rs.getString("tenLoaiSanPham"),
                    rs.getBoolean("trangThai"),
                    rs.getString("ngayCapNhat")
                );
                list.add(productTypeDTO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return list;
	}

	// - Hàm lấy ra danh sách các loại sản phẩm dựa trên 1 điều kiện
	@Override
	public ArrayList<ProductTypeDTO> selectAllByCondition(String[] join, String condition, String order) {
		// - Khai báo biến chứa danh sách trả về
		ArrayList<ProductTypeDTO> list = new ArrayList<>();

		// - Kết nối đến CSDL để truy vấn
		Connection c = JDBCUtil.getInstance().getConnection();
		try {
			String sql = String.format("SELECT * FROM Karaoke.LoaiSanPham%s%s%s;",
					join != null ? CommonDAL.getJoinValues(join) : "", condition != null ? "\nWHERE " + condition : "",
					order != null ? "\nORDER BY " + order : "");
			PreparedStatement pstmt = c.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				ProductTypeDTO productTypeDTO = new ProductTypeDTO(rs.getString("maLoaiSanPham"),
						rs.getString("tenLoaiSanPham"), rs.getBoolean("trangThai"), rs.getString("ngayCapNhat"));
				list.add(productTypeDTO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.getInstance().closeConnection(c);
		}
		return list;
	}

	// - Hàm lấy ra một loại sản phẩm dựa trên mã loại sản phẩm đó
	@Override
	public ProductTypeDTO selectOneById(String id) {
		// - Khai báo biến chứa danh sách trả về
		ProductTypeDTO productTypeDTO = null;

		// - Kết nối đến CSDL để truy vấn
		String sql = "SELECT * FROM Karaoke.LoaiSanPham WHERE maLoaiSanPham = ?";
		try (Connection c = JDBCUtil.getInstance().getConnection();
     			PreparedStatement pstmt = c.prepareStatement(sql)) 
		{
    		pstmt.setString(1, id);
    		try (ResultSet rs = pstmt.executeQuery()) {
        		if (rs.next()) {
            		productTypeDTO = new ProductTypeDTO(
                		rs.getString("maLoaiSanPham"),
                		rs.getString("tenLoaiSanPham"),
                		rs.getBoolean("trangThai"),
                		rs.getString("ngayCapNhat")
            		);
        		}
    		}
		} catch (SQLException e) {
    		e.printStackTrace();
		}
		return productTypeDTO;
	}

	// - Hàm lấy ra ProductTypeId thông qua ProductTypeName
	public String selectOneId(String name)
	{
		// - Khai báo biến chứa danh sách trả về
		String id = "";

		// - Kết nối đến CSDL để truy vấn
		String sql = "SELECT * FROM Karaoke.LoaiSanPham WHERE tenLoaiSanPham = ?";
		try (Connection c = JDBCUtil.getInstance().getConnection();
     			PreparedStatement pstmt = c.prepareStatement(sql)) 
		{
    		pstmt.setString(1, name);
    		try (ResultSet rs = pstmt.executeQuery()) {
        		if (rs.next()) {
            		id = rs.getString("maLoaiSanPham");
        		}
    		}
		} catch (SQLException e) {
    		e.printStackTrace();
		}
		return id;
	}
}
