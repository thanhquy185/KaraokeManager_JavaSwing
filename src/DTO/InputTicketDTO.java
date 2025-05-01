package DTO;

public class InputTicketDTO {
    private Integer id;          // maPhieuNhap
    private String timeCreate;   // thoiGianTaoPhieu
    private Integer employeeId;  // maNhanVien
    private String supplierId;   // maNCC
    private Long totalPrice;     // tongTien
    private Integer status;      // trangThai (0: Đang chờ xác nhận, 1:Đã huỷ phiếu, 2: Đã hoàn thành)

    // Constructors
    public InputTicketDTO(Integer id, String timeCreate, Integer employeeId, String supplierId, Long totalPrice, Integer status) {
        this.id = id;
        this.timeCreate = timeCreate;
        this.employeeId = employeeId;
        this.supplierId = supplierId;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    // Getters và Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getTimeCreate() { return timeCreate; }
    public void setTimeCreate(String timeCreate) { this.timeCreate = timeCreate; }

    public Integer getEmployeeId() { return employeeId; }
    public void setEmployeeId(Integer employeeId) { this.employeeId = employeeId; }

    public String getSupplierId() { return supplierId; }
    public void setSupplierId(String supplierId) { this.supplierId = supplierId; }

    public Long getTotalPrice() { return totalPrice; }
    public void setTotalPrice(Long totalPrice) { this.totalPrice = totalPrice; }

    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }

}