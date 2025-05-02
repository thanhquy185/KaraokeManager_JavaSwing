package DTO;

public class OrderDetailDTO {
	// Properties
	private Integer orderId;
	private String foodId;
	private Long price;
	private Long quantity;

	// Constructors
	public OrderDetailDTO(Integer orderId, String foodId, Long price, Long quantity) {
		super();
		this.orderId = orderId;
		this.foodId = foodId;
		this.price = price;
		this.quantity = quantity;
	}

	// Getter - Setter
	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getFoodId() {
		return foodId;
	}

	public void setFoodId(String foodId) {
		this.foodId = foodId;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}
}
