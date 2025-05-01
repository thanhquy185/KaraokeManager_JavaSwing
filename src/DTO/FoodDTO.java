package DTO;

public class FoodDTO {
	// Properties
	private String id;
	private String name;
	private String category;
	private Long inventory;
	private Long price;
	private String image;
	private Boolean status;
	private String timeUpdate;
	
	// Constructors
	public FoodDTO(String id, String name, String category, Long inventory, Long price, String image, Boolean status,
			String timeUpdate) {
		this.id = id;
		this.name = name;
		this.category = category;
		this.inventory = inventory;
		this.price = price;
		this.image = image;
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Long getInventory() {
		return inventory;
	}

	public void setInventory(Long inventory) {
		this.inventory = inventory;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
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
