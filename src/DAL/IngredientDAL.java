package DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.IngredientDTO;

public class IngredientDAL implements DAL<IngredientDTO> {
    @Override
    public int insert(IngredientDTO ingredientDTO) {
        if (ingredientDTO == null) return 0;
        int rowChange = 0;
        try (Connection c = JDBCUtil.getInstance().getConnection();
             PreparedStatement pstmt = c.prepareStatement(
                 "INSERT INTO karaoke.NguyenLieu(maNguyenLieu, tenNguyenLieu, donVi, tonKho, trangThai, ngayCapNhat)" +
                 "\nVALUES(?, ?, ?, ?, ?, ?);")) {
            pstmt.setString(1, ingredientDTO.getId());
            pstmt.setString(2, ingredientDTO.getName());
            pstmt.setString(3, ingredientDTO.getUnit());
            pstmt.setInt(4, ingredientDTO.getInventory());
            pstmt.setBoolean(5, ingredientDTO.getStatus());
            pstmt.setString(6, ingredientDTO.getDateUpdate());
            rowChange = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowChange;
    }

    @Override
    public int update(IngredientDTO ingredientDTO) {
        if (ingredientDTO == null || ingredientDTO.getId() == null) return 0;
        int rowChange = 0;
        try (Connection c = JDBCUtil.getInstance().getConnection();
             PreparedStatement pstmt = c.prepareStatement(
                 "UPDATE karaoke.NguyenLieu" +
                 "\nSET tenNguyenLieu = ?, donVi = ?, tonKho = ?, trangThai = ?, ngayCapNhat = ?" +
                 "\nWHERE maNguyenLieu = ?")) {
            pstmt.setString(1, ingredientDTO.getName());
            pstmt.setString(2, ingredientDTO.getUnit());
            pstmt.setInt(3, ingredientDTO.getInventory());
            pstmt.setBoolean(4, ingredientDTO.getStatus());
            pstmt.setString(5, ingredientDTO.getDateUpdate());
            pstmt.setString(6, ingredientDTO.getId());
            rowChange = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowChange;
    }

    @Override
    public int lock(IngredientDTO ingredientDTO) {
        if (ingredientDTO == null || ingredientDTO.getId() == null) return 0;
        int rowChange = 0;
        try (Connection c = JDBCUtil.getInstance().getConnection();
             PreparedStatement pstmt = c.prepareStatement(
                 "UPDATE karaoke.NguyenLieu" +
                 "\nSET trangThai = ?, ngayCapNhat = ?" +
                 "\nWHERE maNguyenLieu = ?")) {
            pstmt.setBoolean(1, ingredientDTO.getStatus());
            pstmt.setString(2, ingredientDTO.getDateUpdate());
            pstmt.setString(3, ingredientDTO.getId());
            rowChange = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowChange;
    }

    @Override
    public ArrayList<IngredientDTO> selectAll() {
        ArrayList<IngredientDTO> list = new ArrayList<>();
        try (Connection c = JDBCUtil.getInstance().getConnection();
             PreparedStatement pstmt = c.prepareStatement("SELECT * FROM karaoke.NguyenLieu;");
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                IngredientDTO ingredientDTO = new IngredientDTO(
                        rs.getString("maNguyenLieu"),
                        rs.getString("tenNguyenLieu"),
                        rs.getString("donVi"),
                        rs.getInt("tonKho"),
                        rs.getBoolean("trangThai"),
                        rs.getString("ngayCapNhat")
                );
                list.add(ingredientDTO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public ArrayList<IngredientDTO> selectAllByCondition(String[] join, String condition, String order) {
        ArrayList<IngredientDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM karaoke.NguyenLieu" +
                    (join != null ? CommonDAL.getJoinValues(join) : "") +
                    (condition != null ? " WHERE " + condition : "") +
                    (order != null ? " ORDER BY " + order : "") + ";";
        try (Connection c = JDBCUtil.getInstance().getConnection();
             PreparedStatement pstmt = c.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                IngredientDTO ingredientDTO = new IngredientDTO(
                		rs.getString("maNguyenLieu"),
                        rs.getString("tenNguyenLieu"),
                        rs.getString("donVi"),
                        rs.getInt("tonKho"),
                        rs.getBoolean("trangThai"),
                        rs.getString("ngayCapNhat")
                );
                list.add(ingredientDTO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public IngredientDTO selectOneById(String id) {
        if (id == null) return null;
        IngredientDTO ingredientDTO = null;
        try (Connection c = JDBCUtil.getInstance().getConnection();
             PreparedStatement pstmt = c.prepareStatement("SELECT * FROM karaoke.NguyenLieu WHERE maNguyenLieu = ?")) {
            pstmt.setString(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    ingredientDTO = new IngredientDTO(
                            rs.getString("maNguyenLieu"),
                            rs.getString("tenNguyenLieu"),
                            rs.getString("donVi"),
                            rs.getInt("tonKho"),
                            rs.getBoolean("trangThai"),
                            rs.getString("ngayCapNhat")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ingredientDTO;
    }
}