package DTO;

public class RoomDTO {
	// Properties
	private String id;
	private String name;
	private String type;
	private Boolean status;
	private String timeUpdate;

	// Constructors
	public RoomDTO(String id, String name, String type, Boolean status, String timeUpdate) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
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

	public void setType(String type) {
		this.type = type;
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

	public String getType() {
		return type;
	}

	public Boolean getStatus() {
		return status;
	}

	public String getTimeUpdate() {
		return timeUpdate;
	}

	@Override
	public String toString() {
		return "RoomDTO [id=" + id + ", name=" + name + ", type=" + type + ", status=" + status
				+ ", timeUpdate=" + timeUpdate + "]";
	}
}
