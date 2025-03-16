package DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.ProductDetailDTO;


public class ProductDetailDAL implements DAL<ProductDetailDTO> {
    //Methods
    // - Hàm thêm một CTSP
    @Override
    public int insert(ProductDetailDTO productDetailDTO)
    {
        // - Biến chứa số dòng đã được thêm
		int rowChange = 0;

		// - Kết nối đến CSDL để truy vấn
		String sql = "INSERT INTO Karaoke.CTSP(maSanPham, maNguyenLieu, dinhLuong)"
                    + "\nVALUES(?, ?, ?);";
        try(Connection c = JDBCUtil.getInstance().getConnection();
			PreparedStatement pstmt = c.prepareStatement(sql))
        {
            pstmt.setString(1, productDetailDTO.getProductId());
            pstmt.setString(2, productDetailDTO.getIngredientId());
            pstmt.setString(3, productDetailDTO.getQuantity());
            rowChange = pstmt.executeUpdate();
        } catch (SQLException e)
        {
			e.printStackTrace();
        }
        return rowChange;
    }

    // - Hàm cập nhật CTSP
    @Override
    public int update(ProductDetailDTO productDetailDTO)
    {
        // - Biến chứa số dòng đã được thêm
		int rowChange = 0;

		// - Kết nối đến CSDL để truy vấn
		String sql = "UPDATE Karaoke.CTSP" + "\nSET dinhLuong = ?"
					+ "\nWHERE maSanPham = ? AND maNguyenLieu = ?";
		try(Connection c = JDBCUtil.getInstance().getConnection();
			PreparedStatement pstmt = c.prepareStatement(sql)) 
		{
			pstmt.setString(1, productDetailDTO.getQuantity());
			pstmt.setString(2, productDetailDTO.getProductId());
			pstmt.setString(3, productDetailDTO.getIngredientId());
			rowChange = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rowChange;
    }

    // - Hàm khóa một CTSP
    public int lock(ProductDetailDTO productDetailDTO)
    {
        // TODO Auto-generated method stub
        return 0; 
    }

	// - Hàm xóa một CTSP
	public void delete(String productId, String ingredientId) {
		// Câu lệnh SQL để xóa bản ghi trong bảng CTSP
		String sql = "DELETE FROM Karaoke.CTSP WHERE maSanPham = ? AND maNguyenLieu = ?";
		
		try (Connection c = JDBCUtil.getInstance().getConnection();
			 PreparedStatement pstmt = c.prepareStatement(sql)) 
		{	
			// Thiết lập giá trị cho các tham số trong câu lệnh SQL
			pstmt.setString(1, productId);
			pstmt.setString(2, ingredientId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

    // - Hàm lấy ra danh sách các CTSP
    @Override
    public ArrayList<ProductDetailDTO> selectAll()
    {
        // - Khai báo thư viện chứa danh sách trả về
        ArrayList<ProductDetailDTO> list = new ArrayList<>();

        // - Kết nối đến CSDL để truy vấn
		String sql = "SELECT * FROM Karaoke.CTSP;";
		
		try(Connection c = JDBCUtil.getInstance().getConnection();
			PreparedStatement pstmt = c.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery())
		{
			while (rs.next()) {
				ProductDetailDTO productDetailDTO = new ProductDetailDTO(rs.getString("maSanPham"),
						rs.getString("maNguyenLieu"), rs.getString("dinhLuong"));
				list.add(productDetailDTO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
        return list;
    }

    // - Hàm lọc CTSP dựa trên điều kiện
    @Override
    public ArrayList<ProductDetailDTO> selectAllByCondition(String[] join, String condition, String order) {
		// - Khai báo biến chứa danh sách trả về
		ArrayList<ProductDetailDTO> list = new ArrayList<>();

		// - Kết nối đến CSDL để truy vấn
		Connection c = JDBCUtil.getInstance().getConnection();
		try {
			String sql = String.format("SELECT * FROM Karaoke.CTSP %s %s %s;",
				join != null ? CommonDAL.getJoinValues(join) : "", condition != null ? "\nWHERE " + condition : "",
				order != null ? "\nORDER BY " + order : "");
			PreparedStatement pstmt = c.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				ProductDetailDTO productDetailDTO = new ProductDetailDTO(rs.getString("maSanPham"),
						rs.getString("maNguyenLieu"), rs.getString("dinhLuong"));
				list.add(productDetailDTO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.getInstance().closeConnection(c);
		}
		return list;
	}

	// - Hàm lấy ra một CTSP dựa trên mã sản phẩm đó
	@Override
	public ProductDetailDTO selectOneById(String id) {
		// ODO Auto-generated method stub
        return null; 
	}

	// - Hàm lấy ra tất cả CTSP dựa trên mã sản phẩm đó
	public ArrayList<ProductDetailDTO> selectAllProductDetailById(String id) {
		// - Khai báo biến chứa danh sách trả về
		ArrayList<ProductDetailDTO> list = new ArrayList<>();

		// - Kết nối đến CSDL để truy vấn
		String sql = "SELECT * FROM Karaoke.CTSP WHERE maSanPham = ?";

		try (Connection c = JDBCUtil.getInstance().getConnection();
			 PreparedStatement pstmt = c.prepareStatement(sql)) {
			 
			pstmt.setString(1, id);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					ProductDetailDTO productDetailDTO = new ProductDetailDTO(
						rs.getString("maSanPham"),
						rs.getString("maNguyenLieu"),
						rs.getString("dinhLuong")
					);
					list.add(productDetailDTO);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}
