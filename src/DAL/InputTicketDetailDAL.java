package DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import DTO.InputTicketDetailDTO;

public class InputTicketDetailDAL implements DAL<InputTicketDetailDTO> {
    @Override
    public int insert(InputTicketDetailDTO dto) {
        if (dto == null)
            return 0;
        int rowsAffected = 0;
        String sql = "INSERT INTO karaoke.ctpn (maPhieuNhap, maMonAn, giaNhap, soLuong) VALUES (?, ?, ?, ?);";
        try (Connection c = JDBCUtil.getInstance().getConnection();
                PreparedStatement pstmt = c.prepareStatement(sql)) {
            pstmt.setInt(1, dto.getInputTicketId());
            pstmt.setString(2, dto.getFoodId());
            pstmt.setLong(3, dto.getPrice());
            pstmt.setLong(4, dto.getQuantity());
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    @Override
    public int update(InputTicketDetailDTO dto) {
        if (dto == null || dto.getInputTicketId() == null || dto.getFoodId() == null)
            return 0;
        int rowsAffected = 0;
        String sql = "UPDATE karaoke.ctpn SET giaNhap = ?, soLuong = ? WHERE maPhieuNhap = ? AND maMonAn = ?;";
        try (Connection c = JDBCUtil.getInstance().getConnection();
                PreparedStatement pstmt = c.prepareStatement(sql)) {
            pstmt.setLong(1, dto.getPrice());
            pstmt.setLong(2, dto.getQuantity());
            pstmt.setInt(3, dto.getInputTicketId());
            pstmt.setString(4, dto.getFoodId());
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    @Override
    public int lock(InputTicketDetailDTO dto) {
        return 0;
    }

    @Override
    public ArrayList<InputTicketDetailDTO> selectAll() {
        ArrayList<InputTicketDetailDTO> list = new ArrayList<>();
        String sql = "SELECT maPhieuNhap, maMonAn, giaNhap, soLuong FROM karaoke.ctpn;";
        try (Connection c = JDBCUtil.getInstance().getConnection();
                PreparedStatement pstmt = c.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                InputTicketDetailDTO dto = new InputTicketDetailDTO(
                        rs.getInt("maPhieuNhap"),
                        rs.getString("maMonAn"),
                        rs.getLong("giaNhap"),
                        rs.getLong("soLuong"));
                list.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public ArrayList<InputTicketDetailDTO> selectAllByCondition(String[] join, String condition, String order) {
        ArrayList<InputTicketDetailDTO> list = new ArrayList<>();
        String sql = "SELECT maPhieuNhap, maMonAn, giaNhap, soLuong FROM karaoke.ctpn " +
                (condition != null ? " WHERE " + condition : "") +
                (order != null ? " ORDER BY " + order : "") + ";";
        try (Connection c = JDBCUtil.getInstance().getConnection();
                PreparedStatement pstmt = c.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                InputTicketDetailDTO dto = new InputTicketDetailDTO(
                        rs.getInt("maPhieuNhap"),
                        rs.getString("maMonAn"),
                        rs.getLong("giaNhap"),
                        rs.getLong("soLuong"));
                list.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public InputTicketDetailDTO selectOneById(String id) {
        String[] parts = id.split("-");
        if (parts.length != 2)
            return null;

        InputTicketDetailDTO dto = null;
        String sql = "SELECT maPhieuNhap, maMonAn, giaNhap, soLuong FROM karaoke.ctpn " +
                "WHERE maPhieuNhap = ? AND maMonAn = ?;";
        try (Connection c = JDBCUtil.getInstance().getConnection();
                PreparedStatement pstmt = c.prepareStatement(sql)) {
            pstmt.setInt(1, Integer.parseInt(parts[0]));
            pstmt.setString(2, parts[1]);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    dto = new InputTicketDetailDTO(
                            rs.getInt("maPhieuNhap"),
                            rs.getString("maMonAn"),
                            rs.getLong("giaNhap"),
                            rs.getLong("soLuong"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dto;
    }
}