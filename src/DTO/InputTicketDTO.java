package DTO;

public class InputTicketDTO {
    private Integer id;          // maPhieuNhap
    private String dateCreate;   // ngayLapPN
    private String supplierId;   // maNCC
    private Long cost;           // tongTien
    private Integer status;      // trangThai (0: Chờ xác nhận, 1: Đã hoàn thành, 2: Hủy)
    private String dateUpdate;   // ngayCapNhat
    private Integer employeeId;  // maNguoiDung

    // Constructor đầy đủ
    public InputTicketDTO(Integer id, String dateCreate, String supplierId, Long cost, 
                         Integer status, String dateUpdate, Integer employeeId) {
        this.id = id;
        this.dateCreate = dateCreate;
        this.supplierId = supplierId;
        this.cost = cost;
        this.status = status;
        this.dateUpdate = dateUpdate;
        this.employeeId = employeeId;
    }

    // Getters và Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getDateCreate() { return dateCreate; }
    public void setDateCreate(String dateCreate) { this.dateCreate = dateCreate; }

    public String getSupplierId() { return supplierId; }
    public void setSupplierId(String supplierId) { this.supplierId = supplierId; }

    public Long getCost() { return cost; }
    public void setCost(Long cost) { this.cost = cost; }

    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }

    public String getDateUpdate() { return dateUpdate; }
    public void setDateUpdate(String dateUpdate) { this.dateUpdate = dateUpdate; }

    public Integer getEmployeeId() { return employeeId; }
    public void setEmployeeId(Integer employeeId) { this.employeeId = employeeId; }
}