package DTO;

public class ProductDTO {
	// Properties
	private String id;
	private String name;
	private String productTypeId;
	private Long price;
	private String image;
	private Boolean status;
	private String dateUpdate;

	// Constructors
	public ProductDTO(String id, String name, String productTypeId, Long price, String image, Boolean status,
			String dateUpdate) {
		super();
		this.id = id;
		this.name = name;
		this.productTypeId = productTypeId;
		this.price = price;
		this.image = image;
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

	public void setProductTypeId(String productTypeId) {
		this.productTypeId = productTypeId;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public void setImage(String image) {
		this.image = image;
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

	public String getProductTypeId() {
		return productTypeId;
	}

	public Long getPrice() {
		return price;
	}

	public String getImage() {
		return image;
	}

	public Boolean getStatus() {
		return status;
	}

	public String getDateUpdate() {
		return dateUpdate;
	}

}
