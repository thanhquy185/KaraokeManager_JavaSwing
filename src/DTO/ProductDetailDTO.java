package DTO;

public class ProductDetailDTO {
	// Properties
	private String productId;
	private String ingredientId;
	private String quantity;

	// Constructors
	public ProductDetailDTO(String productId, String ingredientId, String quantity) {
		super();
		this.productId = productId;
		this.ingredientId = ingredientId;
		this.quantity = quantity;
	}

	// Setter - Getter
	public void setProductId(String productId) {
		this.productId = productId;
	}

	public void setIngredientId(String ingredientId) {
		this.ingredientId = ingredientId;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getProductId() {
		return productId;
	}

	public String getIngredientId() {
		return ingredientId;
	}

	public String getQuantity() {
		return quantity;
	}

}
