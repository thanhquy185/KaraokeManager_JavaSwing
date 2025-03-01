package DTO;

public class IngredientDTO {
	// Properties
	private String id;
	private String name;
	private String unit;
	private String inventory;
	private Boolean status;
	private String dateUpdate;

	// Constructors
	public IngredientDTO(String id, String name, String unit, String inventory, Boolean status, String dateUpdate) {
		super();
		this.id = id;
		this.name = name;
		this.unit = unit;
		this.inventory = inventory;
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

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public void setInventory(String inventory) {
		this.inventory = inventory;
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

	public String getUnit() {
		return unit;
	}

	public String getInventory() {
		return inventory;
	}

	public Boolean getStatus() {
		return status;
	}

	public String getDateUpdate() {
		return dateUpdate;
	}
}
