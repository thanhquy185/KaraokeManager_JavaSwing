package DTO;

public class CustomerTypeDTO {
	// Properties
	private String id;
	private String name;
	private Integer discount;

	// Constructors
	public CustomerTypeDTO(String id, String name, Integer discount) {
		super();
		this.id = id;
		this.name = name;
		this.discount = discount;
	}

	// Setter - Getter
	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDiscount(Integer discount) {
		this.discount = discount;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Integer getDiscount() {
		return discount;
	}
}
