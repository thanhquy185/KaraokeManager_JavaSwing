package DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import DTO.InputTicketDTO;

public class InputTicketDAL implements DAL<InputTicketDTO> {
    @Override
    public int insert(InputTicketDTO dto) {
        if (dto == null) return 0;
        int rowsAffected = 0;
        String sql = "INSERT INTO karaoke.phieunhap (maPhieuNhap, ngayLapPN, maNCC, tongTien, trangThai, ngayCapNhat) " +
                     "VALUES (?, ?, ?, ?, ?, ?);";
        try (Connection c = JDBCUtil.getInstance().getConnection();
             PreparedStatement pstmt = c.prepareStatement(sql)) {
            pstmt.setInt(1, dto.getId());
            pstmt.setString(2, dto.getDateCreate());
            pstmt.setString(3, dto.getSupplierId());
            pstmt.setLong(4, dto.getCost());
            pstmt.setBoolean(5, dto.getStatus());
            pstmt.setString(6, dto.getDateUpdate());
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    @Override
    public int update(InputTicketDTO dto) {
        if (dto == null || dto.getId() == null) return 0;
        int rowsAffected = 0;
        String sql = "UPDATE karaoke.phieunhap SET ngayLapPN = ?, maNCC = ?, tongTien = ?, trangThai = ?, ngayCapNhat = ? " +
                     "WHERE maPhieuNhap = ?;";
        try (Connection c = JDBCUtil.getInstance().getConnection();
             PreparedStatement pstmt = c.prepareStatement(sql)) {
            pstmt.setString(1, dto.getDateCreate());
            pstmt.setString(2, dto.getSupplierId());
            pstmt.setLong(3, dto.getCost());
            pstmt.setBoolean(4, dto.getStatus());
            pstmt.setString(5, dto.getDateUpdate());
            pstmt.setInt(6, dto.getId());
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    @Override
    public int lock(InputTicketDTO dto) {
        if (dto == null || dto.getId() == null) return 0;
        int rowsAffected = 0;
        String sql = "UPDATE karaoke.phieunhap SET trangThai = ?, ngayCapNhat = ? WHERE maPhieuNhap = ?;";
        try (Connection c = JDBCUtil.getInstance().getConnection();
             PreparedStatement pstmt = c.prepareStatement(sql)) {
            pstmt.setBoolean(1, !dto.getStatus()); // Đảo ngược trạng thái
            pstmt.setString(2, dto.getDateUpdate());
            pstmt.setInt(3, dto.getId());
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    // Các phương thức select giữ nguyên vì không liên quan đến employeeId
    @Override
    public ArrayList<InputTicketDTO> selectAll() {
        ArrayList<InputTicketDTO> list = new ArrayList<>();
        String sql = "SELECT maPhieuNhap, ngayLapPN, maNCC, tongTien, trangThai, ngayCapNhat " +
                     "FROM karaoke.phieunhap;";
        try (Connection c = JDBCUtil.getInstance().getConnection();
             PreparedStatement pstmt = c.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                InputTicketDTO dto = new InputTicketDTO(
                    rs.getInt("maPhieuNhap"),
                    rs.getString("ngayLapPN"),
                    rs.getString("maNCC"),
                    rs.getLong("tongTien"),
                    rs.getBoolean("trangThai"),
                    rs.getString("ngayCapNhat")
                );
                list.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public ArrayList<InputTicketDTO> selectAllByCondition(String[] join, String condition, String order) {
        ArrayList<InputTicketDTO> list = new ArrayList<>();
        String sql = "SELECT maPhieuNhap, ngayLapPN, maNCC, tongTien, trangThai, ngayCapNhat " +
                     "FROM karaoke.phieunhap " +
                     (condition != null ? " WHERE " + condition : "") +
                     (order != null ? " ORDER BY " + order : "") + ";";
        try (Connection c = JDBCUtil.getInstance().getConnection();
             PreparedStatement pstmt = c.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                InputTicketDTO dto = new InputTicketDTO(
                    rs.getInt("maPhieuNhap"),
                    rs.getString("ngayLapPN"),
                    rs.getString("maNCC"),
                    rs.getLong("tongTien"),
                    rs.getBoolean("trangThai"),
                    rs.getString("ngayCapNhat")
                );
                list.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public InputTicketDTO selectOneById(String id) {
        InputTicketDTO dto = null;
        String sql = "SELECT maPhieuNhap, ngayLapPN, maNCC, tongTien, trangThai, ngayCapNhat " +
                     "FROM karaoke.phieunhap " +
                     "WHERE maPhieuNhap = ?;";
        try (Connection c = JDBCUtil.getInstance().getConnection();
             PreparedStatement pstmt = c.prepareStatement(sql)) {
            pstmt.setInt(1, Integer.parseInt(id));
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    dto = new InputTicketDTO(
                        rs.getInt("maPhieuNhap"),
                        rs.getString("ngayLapPN"),
                        rs.getString("maNCC"),
                        rs.getLong("tongTien"),
                        rs.getBoolean("trangThai"),
                        rs.getString("ngayCapNhat")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dto;
    }
}