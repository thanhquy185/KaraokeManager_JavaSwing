package DTO;

public class SupplierDTO {
    // Properties
    private String id;
    private String name;
    private String phone;
    private String email;
    private String address;
    private Boolean status;
    private String timeUpdate;

    // Constructors
    public SupplierDTO(String id, String name, String phone, String email, String address, Boolean status,
            String timeUpdate) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.status = status;
        this.timeUpdate = timeUpdate;
    }

    // Setter - Getter
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public void setTimeUpdate(String timeUpdate) {
        this.timeUpdate = timeUpdate;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public Boolean getStatus() {
        return status;
    }

    public String getTimeUpdate() {
        return timeUpdate;
    }
}