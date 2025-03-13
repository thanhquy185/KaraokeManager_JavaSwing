package DTO;

public class InputTicketDetailDTO {
	// Properties
	private Integer id;
	private String ingredientId;
	private Long inputPrice;
	private Integer inputQuantity;
	
	// Constructors
	public InputTicketDetailDTO(Integer id, String ingredientId, Long inputPrice, Integer inputQuantity) {
		super();
		this.id = id;
		this.ingredientId = ingredientId;
		this.inputPrice = inputPrice;
		this.inputQuantity = inputQuantity;
	}

	// Setter - Getter
	public void setId(Integer id) {
		this.id = id;
	}

	public void setIngredientId(String ingredientId) {
		this.ingredientId = ingredientId;
	}

	public void setInputPrice(Long inputPrice) {
		this.inputPrice = inputPrice;
	}

	public void setInputQuantity(Integer inputQuantity) {
		this.inputQuantity = inputQuantity;
	}
	
	public Integer getId() {
		return id;
	}

	public String getIngredientId() {
		return ingredientId;
	}

	public Long getInputPrice() {
		return inputPrice;
	}

	public Integer getInputQuantity() {
		return inputQuantity;
	}

}
