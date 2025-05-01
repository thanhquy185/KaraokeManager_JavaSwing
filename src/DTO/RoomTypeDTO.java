package DTO;

public class RoomTypeDTO {
	// Properties
	private String id;
	private String name;
	private Long cost;
	
	// Constructors
	public RoomTypeDTO(String id, String name, Long cost) {
		super();
		this.id = id;
		this.name = name;
		this.cost = cost;
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
	
	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Long getCost() {
		return cost;
	}
}
