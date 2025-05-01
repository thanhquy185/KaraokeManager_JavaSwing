package DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.FoodDTO;

public class FoodDAL implements DAL<FoodDTO> {
    @Override
    public int insert(FoodDTO foodDTO) {
        if (foodDTO == null)
            return 0;
        int rowChange = 0;
        try (Connection c = JDBCUtil.getInstance().getConnection();
                PreparedStatement pstmt = c.prepareStatement(
                        "INSERT INTO karaoke.MonAn(maMonAn, tenMonAn, maLoaiMonAn, tonKho, giaBan, hinhAnh, trangThai, thoiGianCapNhat)"
                                + "\nVALUES(?, ?, ?, ?, ?, ?, ?, ?);")) {
            pstmt.setString(1, foodDTO.getId());
            pstmt.setString(2, foodDTO.getName());
            pstmt.setString(3, foodDTO.getCategory());
            pstmt.setLong(4, foodDTO.getInventory());
            pstmt.setLong(5, foodDTO.getPrice());
            pstmt.setString(6, foodDTO.getImage());
            pstmt.setBoolean(7, foodDTO.getStatus());
            pstmt.setString(8, foodDTO.getTimeUpdate());
            rowChange = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowChange;
    }

    @Override
    public int update(FoodDTO foodDTO) {
        if (foodDTO == null || foodDTO.getId() == null)
            return 0;
        int rowChange = 0;
        try (Connection c = JDBCUtil.getInstance().getConnection();
                PreparedStatement pstmt = c.prepareStatement(
                        "UPDATE karaoke.MonAn" +
                                "\nSET tenMonAn = ?, maLoaiMonAn = ?, giaBan = ?, hinhAnh = ?, thoiGianCapNhat = ?" +
                                "\nWHERE maMonAn = ?")) {
            pstmt.setString(1, foodDTO.getName());
            pstmt.setString(2, foodDTO.getCategory());
            pstmt.setLong(3, foodDTO.getPrice());
            pstmt.setString(4, foodDTO.getImage());
            pstmt.setString(5, foodDTO.getTimeUpdate());
            pstmt.setString(6, foodDTO.getId());
            rowChange = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowChange;
    }

    public int updateInventory(FoodDTO foodDTO) {
        if (foodDTO == null || foodDTO.getId() == null)
            return 0;
        int rowChange = 0;
        try (Connection c = JDBCUtil.getInstance().getConnection();
                PreparedStatement pstmt = c.prepareStatement(
                        "UPDATE karaoke.MonAn" +
                                "\nSET tonKho = ?, trangThai = ?, thoiGianCapNhat = ?" +
                                "\nWHERE maMonAn = ?")) {
            pstmt.setLong(1, foodDTO.getInventory());
            pstmt.setBoolean(2, foodDTO.getStatus());
            pstmt.setString(3, foodDTO.getTimeUpdate());
            pstmt.setString(4, foodDTO.getId());
            rowChange = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowChange;
    }

    @Override
    public int lock(FoodDTO foodDTO) {
        if (foodDTO == null || foodDTO.getId() == null)
            return 0;
        int rowChange = 0;
        try (Connection c = JDBCUtil.getInstance().getConnection();
                PreparedStatement pstmt = c.prepareStatement(
                        "UPDATE karaoke.MonAn" +
                                "\nSET trangThai = ?, thoiGianCapNhat = ?" +
                                "\nWHERE maMonAn = ?")) {
            pstmt.setBoolean(1, foodDTO.getStatus());
            pstmt.setString(2, foodDTO.getTimeUpdate());
            pstmt.setString(3, foodDTO.getId());
            rowChange = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowChange;
    }

    @Override
    public ArrayList<FoodDTO> selectAll() {
        ArrayList<FoodDTO> list = new ArrayList<>();
        try (Connection c = JDBCUtil.getInstance().getConnection();
                PreparedStatement pstmt = c.prepareStatement("SELECT * FROM karaoke.MonAn;");
                ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                FoodDTO foodDTO = new FoodDTO(
                        rs.getString("maMonAn"),
                        rs.getString("tenMonAn"),
                        rs.getString("maLoaiMonAn"),
                        rs.getLong("tonKho"),
                        rs.getLong("giaBan"),
                        rs.getString("hinhAnh"),
                        rs.getBoolean("trangThai"),
                        rs.getString("thoiGianCapNhat"));
                list.add(foodDTO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public ArrayList<FoodDTO> selectAllByCondition(String[] join, String condition, String order) {
        ArrayList<FoodDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM karaoke.MonAn" +
                (join != null ? CommonDAL.getJoinValues(join) : "") +
                (condition != null ? " WHERE " + condition : "") +
                (order != null ? " ORDER BY " + order : "") + ";";
        try (Connection c = JDBCUtil.getInstance().getConnection();
                PreparedStatement pstmt = c.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                FoodDTO foodDTO = new FoodDTO(
                        rs.getString("maMonAn"),
                        rs.getString("tenMonAn"),
                        rs.getString("maLoaiMonAn"),
                        rs.getLong("tonKho"),
                        rs.getLong("giaBan"),
                        rs.getString("hinhAnh"),
                        rs.getBoolean("trangThai"),
                        rs.getString("thoiGianCapNhat"));
                list.add(foodDTO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public FoodDTO selectOneById(String id) {
        if (id == null)
            return null;
        FoodDTO foodDTO = null;
        try (Connection c = JDBCUtil.getInstance().getConnection();
                PreparedStatement pstmt = c.prepareStatement("SELECT * FROM karaoke.MonAn WHERE maMonAn = ?")) {
            pstmt.setString(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    foodDTO = new FoodDTO(
                            rs.getString("maMonAn"),
                            rs.getString("tenMonAn"),
                            rs.getString("maLoaiMonAn"),
                            rs.getLong("tonKho"),
                            rs.getLong("giaBan"),
                            rs.getString("hinhAnh"),
                            rs.getBoolean("trangThai"),
                            rs.getString("thoiGianCapNhat"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return foodDTO;
    }
}