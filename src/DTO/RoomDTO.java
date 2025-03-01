package DTO;

public class RoomDTO {
	// Properties
	private String idRoom;
	private String name;
	private String idRoomType;
	private String status;
	private String dateUpdate;

	// Constructors
	public RoomDTO(String idRoom, String name, String idRoomType, String status, String dateUpdate) {
		super();
		this.idRoom = idRoom;
		this.name = name;
		this.idRoomType = idRoomType;
		this.status = status;
		this.dateUpdate = dateUpdate;
	}

	// Setter - Getter
	public String getIdRoom() {
		return idRoom;
	}

	public void setIdRoom(String idRoom) {
		this.idRoom = idRoom;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdRoomType() {
		return idRoomType;
	}

	public void setIdRoomType(String idRoomType) {
		this.idRoomType = idRoomType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDateUpdate() {
		return dateUpdate;
	}

	public void setDateUpdate(String dateUpdate) {
		this.dateUpdate = dateUpdate;
	}
}
