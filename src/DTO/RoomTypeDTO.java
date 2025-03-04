package DTO;

public class RoomTypeDTO {
	// Properties
	private String id;
	private String name;
	private Boolean status;
	private String dateUpdate;

	// Constructors
	public RoomTypeDTO(String id, String name, Boolean status, String dateUpdate) {
		super();
		this.id = id;
		this.name = name;
		this.status = status;
		this.dateUpdate = dateUpdate;
	}

	// Setter - Getter
	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public void setDateUpdate(String dateUpdate) {
		this.dateUpdate = dateUpdate;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Boolean getStatus() {
		return status;
	}

	public String getDateUpdate() {
		return dateUpdate;
	}

	@Override
	public String toString() {
		return "RoomTypeDTO [id=" + id + ", name=" + name + ", status=" + status + ", dateUpdate=" + dateUpdate + "]";
	}
}
