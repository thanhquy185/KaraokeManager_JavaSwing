package DTO;

public class OrderDTO {
	// Properties
	private Integer id;
	private String dateOrder;
	private String roomId;
	private String customerId;
	private String employeeId;
	private String discountId;
	private Integer time;
	private Integer cost;
	private Boolean status;
	private String dateUpdate;

	// Constructors
	public OrderDTO(Integer id, String dateOrder, String roomId, String customerId, String employeeId,
			String discountId, Integer time, Integer cost, Boolean status, String dateUpdate) {
		super();
		this.id = id;
		this.dateOrder = dateOrder;
		this.roomId = roomId;
		this.customerId = customerId;
		this.employeeId = employeeId;
		this.discountId = discountId;
		this.time = time;
		this.cost = cost;
		this.status = status;
		this.dateUpdate = dateUpdate;
	}

	// Setter - Getter
	public void setId(Integer id) {
		this.id = id;
	}

	public void setDateOrder(String dateOrder) {
		this.dateOrder = dateOrder;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public void setDiscountId(String discountId) {
		this.discountId = discountId;
	}

	public void setTime(Integer time) {
		this.time = time;
	}

	public void setCost(Integer cost) {
		this.cost = cost;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public void setDateUpdate(String dateUpdate) {
		this.dateUpdate = dateUpdate;
	}

	public Integer getId() {
		return id;
	}

	public String getDateOrder() {
		return dateOrder;
	}

	public String getRoomId() {
		return roomId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public String getDiscountId() {
		return discountId;
	}

	public Integer getTime() {
		return time;
	}

	public Integer getCost() {
		return cost;
	}

	public Boolean getStatus() {
		return status;
	}

	public String getDateUpdate() {
		return dateUpdate;
	}
}
