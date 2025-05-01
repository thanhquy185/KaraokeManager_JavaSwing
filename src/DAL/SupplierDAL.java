package DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.SupplierDTO;

public class SupplierDAL implements DAL<SupplierDTO> {
    @Override
    public int insert(SupplierDTO supplierDTO) {
        if (supplierDTO == null) {
            System.err.println("SupplierDTO không được null");
            return 0;
        }
        int rowsAffected = 0;
        String sql = "INSERT INTO karaoke.nhacungcap (maNCC, tenNCC, soDienThoai, email, diaChi, trangThai, thoiGianCapNhat) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?);";
        try (Connection c = JDBCUtil.getInstance().getConnection();
             PreparedStatement pstmt = c.prepareStatement(sql)) {
            pstmt.setString(1, supplierDTO.getId());
            pstmt.setString(2, supplierDTO.getName());
            pstmt.setString(3, supplierDTO.getPhone());
            pstmt.setString(4, supplierDTO.getEmail());
            pstmt.setString(5, supplierDTO.getAddress());
            pstmt.setBoolean(6, supplierDTO.getStatus());
            pstmt.setString(7, supplierDTO.getTimeUpdate());
            rowsAffected = pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm nhà cung cấp: " + e.getMessage());
            e.printStackTrace();
        }
        return rowsAffected;
    }

    @Override
    public int update(SupplierDTO supplierDTO) {
        if (supplierDTO == null || supplierDTO.getId() == null) {
            System.err.println("SupplierDTO hoặc ID không được null");
            return 0;
        }
        int rowsAffected = 0;
        String sql = "UPDATE karaoke.nhacungcap " +
                     "SET tenNCC = ?, soDienThoai = ?, email = ?, diaChi = ?, thoiGianCapNhat = ? " +
                     "WHERE maNCC = ?;";
        try (Connection c = JDBCUtil.getInstance().getConnection();
             PreparedStatement pstmt = c.prepareStatement(sql)) {
            pstmt.setString(1, supplierDTO.getName());
            pstmt.setString(2, supplierDTO.getPhone());
            pstmt.setString(3, supplierDTO.getEmail());
            pstmt.setString(4, supplierDTO.getAddress());
            pstmt.setString(5, supplierDTO.getTimeUpdate());
            pstmt.setString(6, supplierDTO.getId());
            rowsAffected = pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật nhà cung cấp: " + e.getMessage());
            e.printStackTrace();
        }
        return rowsAffected;
    }

    @Override
    public int lock(SupplierDTO supplierDTO) {
        if (supplierDTO == null || supplierDTO.getId() == null) {
            System.err.println("SupplierDTO hoặc ID không được null");
            return 0;
        }
        int rowsAffected = 0;
        String sql = "UPDATE karaoke.nhacungcap SET trangThai = ?, thoiGianCapNhat = ? WHERE maNCC = ?;";
        try (Connection c = JDBCUtil.getInstance().getConnection();
             PreparedStatement pstmt = c.prepareStatement(sql)) {
            pstmt.setBoolean(1, supplierDTO.getStatus()); // Sử dụng trạng thái từ supplierDTO
            pstmt.setString(2, supplierDTO.getTimeUpdate());
            pstmt.setString(3, supplierDTO.getId());
            rowsAffected = pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Lỗi khi thay đổi trạng thái nhà cung cấp: " + e.getMessage());
            e.printStackTrace();
        }
        return rowsAffected;
    }

    @Override
    public ArrayList<SupplierDTO> selectAll() {
        ArrayList<SupplierDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM karaoke.nhacungcap;";
        try (Connection c = JDBCUtil.getInstance().getConnection();
             PreparedStatement pstmt = c.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                list.add(createSupplierDTOFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách nhà cung cấp: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public ArrayList<SupplierDTO> selectAllByCondition(String[] join, String condition, String order) {
        ArrayList<SupplierDTO> list = new ArrayList<>();
        String sql = String.format("SELECT * FROM Karaoke.NhaCungCap%s%s%s;",
				join != null ? CommonDAL.getJoinValues(join) : "", condition != null ? "\nWHERE " + condition : "",
				order != null ? "\nORDER BY " + order : "");
        try (Connection c = JDBCUtil.getInstance().getConnection();
             PreparedStatement pstmt = c.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                list.add(createSupplierDTOFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách nhà cung cấp theo điều kiện: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public SupplierDTO selectOneById(String id) {
        if (id == null) return null;
        SupplierDTO supplierDTO = null;
        String sql = "SELECT * FROM karaoke.nhacungcap WHERE maNCC = ?;";
        try (Connection c = JDBCUtil.getInstance().getConnection();
             PreparedStatement pstmt = c.prepareStatement(sql)) {
            pstmt.setString(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    supplierDTO = createSupplierDTOFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy nhà cung cấp theo ID: " + e.getMessage());
            e.printStackTrace();
        }
        return supplierDTO;
    }

    private SupplierDTO createSupplierDTOFromResultSet(ResultSet rs) throws SQLException {
        return new SupplierDTO(
            rs.getString("maNCC"),
            rs.getString("tenNCC"),
            rs.getString("soDienThoai"),
            rs.getString("email"),
            rs.getString("diaChi"),
            rs.getBoolean("trangThai"),
            rs.getString("thoiGianCapNhat")
        );
    }
}