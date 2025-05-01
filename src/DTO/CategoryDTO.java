package DTO;

public class CategoryDTO {
    // Properties
    private String id;
    private String name;
    private Boolean status;
    private String timeUpdate;

    // Constructors
    public CategoryDTO(String id, String name, Boolean status, String timeUpdate) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.timeUpdate = timeUpdate;
    }

    // Getter - Setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getTimeUpdate() {
        return timeUpdate;
    }

    public void setTimeUpdate(String timeUpdate) {
        this.timeUpdate = timeUpdate;
    }

    
}
