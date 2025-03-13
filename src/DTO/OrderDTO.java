package DTO;

public class OrderDTO {
	// Properties
	private Integer id;
	private String dateOrder;
	private String roomId;
	private Integer employeeId;
	private String customerId;
	private String discountId;
	private Integer time;
	private Long cost;
	private Boolean status;
	private String dateUpdate;

	// Constructors
	public OrderDTO(Integer id, String dateOrder, String roomId, Integer employeeId, String customerId,
			String discountId, Integer time, Long cost, Boolean status, String dateUpdate) {
		super();
		this.id = id;
		this.dateOrder = dateOrder;
		this.roomId = roomId;
		this.employeeId = employeeId;
		this.customerId = customerId;
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

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public void setDiscountId(String discountId) {
		this.discountId = discountId;
	}

	public void setTime(Integer time) {
		this.time = time;
	}

	public void setCost(Long cost) {
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

	public Integer getEmployeeId() {
		return employeeId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public String getDiscountId() {
		return discountId;
	}

	public Integer getTime() {
		return time;
	}

	public Long getCost() {
		return cost;
	}

	public Boolean getStatus() {
		return status;
	}

	public String getDateUpdate() {
		return dateUpdate;
	}

}
