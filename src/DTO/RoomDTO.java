package DTO;

public class RoomDTO {
	// Properties
	private String id;
	private String name;
	private String roomTypeId;
	private Boolean status;
	private String dateUpdate;

	// Constructors
	public RoomDTO(String id, String name, String roomTypeId, Boolean status, String dateUpdate) {
		super();
		this.id = id;
		this.name = name;
		this.roomTypeId = roomTypeId;
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

	public void setRoomTypeId(String roomTypeId) {
		this.roomTypeId = roomTypeId;
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

	public String getRoomTypeId() {
		return roomTypeId;
	}

	public Boolean getStatus() {
		return status;
	}

	public String getDateUpdate() {
		return dateUpdate;
	}

	@Override
	public String toString() {
		return "RoomDTO [id=" + id + ", name=" + name + ", roomTypeId=" + roomTypeId + ", status=" + status
				+ ", dateUpdate=" + dateUpdate + "]";
	}
}
