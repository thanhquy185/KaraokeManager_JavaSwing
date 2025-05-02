package DTO;

public class OrderDTO {
	// Properties
	private Integer id;
	private String timeCreate;
	private String timeStart;
	private String timeEnd;
	private String roomId;
	private Integer employeeId;
	private String customerId;
	private Long totalPrice;
	private Integer status;

	// Constructors
	public OrderDTO(Integer id, String timeCreate, String timeStart, String timeEnd, String roomId, Integer employeeId,
			String customerId, Long totalPrice, Integer status) {
		this.id = id;
		this.timeCreate = timeCreate;
		this.timeStart = timeStart;
		this.timeEnd = timeEnd;
		this.roomId = roomId;
		this.employeeId = employeeId;
		this.customerId = customerId;
		this.totalPrice = totalPrice;
		this.status = status;
	}

	// Getter - Setter
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTimeCreate() {
		return timeCreate;
	}

	public void setTimeCreate(String timeCreate) {
		this.timeCreate = timeCreate;
	}

	public String getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(String timeStart) {
		this.timeStart = timeStart;
	}

	public String getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(String timeEnd) {
		this.timeEnd = timeEnd;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public Long getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Long totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
