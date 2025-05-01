package DTO;

public class PrivilegeDTO {
	// Properties
	private String id;
	private String name;

	// Constructors
	public PrivilegeDTO(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	// Setter - Getter
	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

}
