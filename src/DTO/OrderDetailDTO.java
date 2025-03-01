package DTO;

public class OrderDetailDTO {
	// Properties
	private Integer orderId;
	private String productId;
	private Integer quantity;

	// Constructors
	public OrderDetailDTO(Integer orderId, String productId, Integer quantity) {
		super();
		this.orderId = orderId;
		this.productId = productId;
		this.quantity = quantity;
	}

	// Setter - Getter
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public String getProductId() {
		return productId;
	}

	public Integer getQuantity() {
		return quantity;
	}

}
