package DTO;

public class InputTicketDTO {
    private Integer id;          // maPhieuNhap
    private String dateCreate;   // ngayLapPN
    private String supplierId;   // maNCC
    private Long cost;           // tongTien
    private Boolean status;      // trangThai
    private String dateUpdate;   // ngayCapNhat
    private Integer employeeId;  // maNguoiDung
    private Boolean isCancelled; // isCancelled

    // Constructor đầy đủ
    public InputTicketDTO(Integer id, String dateCreate, String supplierId, Long cost, 
                         Boolean status, String dateUpdate, Integer employeeId, Boolean isCancelled) {
        this.id = id;
        this.dateCreate = dateCreate;
        this.supplierId = supplierId;
        this.cost = cost;
        this.status = status;
        this.dateUpdate = dateUpdate;
        this.employeeId = employeeId;
        this.isCancelled = isCancelled;
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

    public Boolean getStatus() { return status; }
    public void setStatus(Boolean status) { this.status = status; }

    public String getDateUpdate() { return dateUpdate; }
    public void setDateUpdate(String dateUpdate) { this.dateUpdate = dateUpdate; }

    public Integer getEmployeeId() { return employeeId; }
    public void setEmployeeId(Integer employeeId) { this.employeeId = employeeId; }

    public Boolean getIsCancelled() { return isCancelled; }
    public void setIsCancelled(Boolean isCancelled) { this.isCancelled = isCancelled; }
}