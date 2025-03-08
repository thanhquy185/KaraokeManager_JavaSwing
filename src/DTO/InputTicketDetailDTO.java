package DTO;

public class InputTicketDetailDTO {
	// Properties
	private Integer inputTicketId;
	private String suppliesId;
	private String ingredientId;
	private Float inputPrice;
	private Integer inputQuantity;

	// Constructors
	public InputTicketDetailDTO(Integer inputTicketId, String suppliesId, String ingredientId, Float inputPrice,
			Integer inputQuantity) {
		super();
		this.inputTicketId = inputTicketId;
		this.suppliesId = suppliesId;
		this.ingredientId = ingredientId;
		this.inputPrice = inputPrice;
		this.inputQuantity = inputQuantity;
	}

	// Setter - Getter
	public void setInputTicketId(Integer inputTicketId) {
		this.inputTicketId = inputTicketId;
	}

	public void setSuppliesId(String suppliesId) {
		this.suppliesId = suppliesId;
	}

	public void setIngredientId(String ingredientId) {
		this.ingredientId = ingredientId;
	}

	public void setInputPrice(Float inputPrice) {
		this.inputPrice = inputPrice;
	}

	public void setInputQuantity(Integer inputQuantity) {
		this.inputQuantity = inputQuantity;
	}

	public Integer getInputTicketId() {
		return inputTicketId;
	}

	public String getSuppliesId() {
		return suppliesId;
	}

	public String getIngredientId() {
		return ingredientId;
	}

	public Float getInputPrice() {
		return inputPrice;
	}

	public Integer getInputQuantity() {
		return inputQuantity;
	}

}
