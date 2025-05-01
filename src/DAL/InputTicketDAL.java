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
        String sql = "INSERT INTO karaoke.phieunhap (maPhieuNhap, thoiGianTaoPhieu, maNhanVien, maNCC, tongTien, trangThai)" +
                     "VALUES (?, ?, ?, ?, ?, ?);";
        try (Connection c = JDBCUtil.getInstance().getConnection();
            PreparedStatement pstmt = c.prepareStatement(sql)) {
            pstmt.setInt(1, dto.getId());
            pstmt.setString(2, dto.getTimeCreate());
            pstmt.setInt(3, dto.getEmployeeId());
            pstmt.setString(4, dto.getSupplierId());
            pstmt.setLong(5, dto.getTotalPrice());
            pstmt.setInt(6, dto.getStatus());
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
        String sql = "UPDATE karaoke.phieunhap SET thoiGianTaoPhieu = ?, maNCC = ?, tongTien = ?, trangThai = ?" +
                     "WHERE maPhieuNhap = ?;";
        try (Connection c = JDBCUtil.getInstance().getConnection();
             PreparedStatement pstmt = c.prepareStatement(sql)) {
            pstmt.setString(1, dto.getTimeCreate());
            pstmt.setString(2, dto.getSupplierId());
            pstmt.setLong(3, dto.getTotalPrice());
            pstmt.setInt(4, dto.getStatus());
            pstmt.setInt(5, dto.getId());
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    @Override
    public int lock(InputTicketDTO dto) {
        // if (dto == null || dto.getId() == null) return 0;
        // int rowsAffected = 0;
        // String sql = "UPDATE karaoke.phieunhap SET trangThai = ?, WHERE maPhieuNhap = ?;";
        // try (Connection c = JDBCUtil.getInstance().getConnection();
        //      PreparedStatement pstmt = c.prepareStatement(sql)) {
        //     pstmt.setInt(1, dto.getStatus());
        //     pstmt.setInt(2, dto.getId());
        //     rowsAffected = pstmt.executeUpdate();
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }
        // return rowsAffected;

        return 0;
    }

    public int updateStatus(InputTicketDTO dto) {
        if (dto == null || dto.getId() == null) return 0;
        int rowsAffected = 0;
        String sql = "UPDATE karaoke.phieunhap SET trangThai = ? WHERE maPhieuNhap = ?;";
        try (Connection c = JDBCUtil.getInstance().getConnection();
            PreparedStatement pstmt = c.prepareStatement(sql)) {
            pstmt.setInt(1, dto.getStatus());
            pstmt.setInt(2, dto.getId());
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    @Override
    public ArrayList<InputTicketDTO> selectAll() {
        ArrayList<InputTicketDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM karaoke.phieunhap";
        try (Connection c = JDBCUtil.getInstance().getConnection();
             PreparedStatement pstmt = c.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                InputTicketDTO dto = new InputTicketDTO(
                    rs.getInt("maPhieuNhap"),
                    rs.getString("thoiGianTaoPhieu"),
                    rs.getInt("maNhanVien"),
                    rs.getString("maNCC"),
                    rs.getLong("tongTien"),
                    rs.getInt("trangThai")
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
        String sql = "SELECT * FROM karaoke.phieunhap " +
                     (condition != null ? " WHERE " + condition : "") +
                     (order != null ? " ORDER BY " + order : "") + ";";
        try (Connection c = JDBCUtil.getInstance().getConnection();
             PreparedStatement pstmt = c.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                InputTicketDTO dto = new InputTicketDTO(
                    rs.getInt("maPhieuNhap"),
                    rs.getString("thoiGianTaoPhieu"),
                    rs.getInt("maNhanVien"),
                    rs.getString("maNCC"),
                    rs.getLong("tongTien"),
                    rs.getInt("trangThai")
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
        String sql = "SELECT * FROM karaoke.phieunhap WHERE maPhieuNhap = ?;";
        try (Connection c = JDBCUtil.getInstance().getConnection();
             PreparedStatement pstmt = c.prepareStatement(sql)) {
            pstmt.setInt(1, Integer.parseInt(id));
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    dto = new InputTicketDTO(
                        rs.getInt("maPhieuNhap"),
                        rs.getString("thoiGianTaoPhieu"),
                        rs.getInt("maNhanVien"),
                        rs.getString("maNCC"),
                        rs.getLong("tongTien"),
                        rs.getInt("trangThai")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dto;
    }
}