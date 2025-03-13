package DTO;

public class RoomTypeDTO {
	// Properties
	private String id;
	private String name;
	private Long cost;
	private Boolean status;
	private String dateUpdate;
	
	// Constructors
	public RoomTypeDTO(String id, String name, Long cost, Boolean status, String dateUpdate) {
		super();
		this.id = id;
		this.name = name;
		this.cost = cost;
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

	public void setCost(Long cost) {
		this.cost = cost;
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

	public Long getCost() {
		return cost;
	}

	public Boolean getStatus() {
		return status;
	}

	public String getDateUpdate() {
		return dateUpdate;
	}
}
