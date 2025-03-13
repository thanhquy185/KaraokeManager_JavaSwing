package DTO;

public class InputTicketDTO {
	// Properties
	private Integer id;
	private String dateCreate;
	private String supplierId;
	private Long cost;
	private Boolean status;
	private String dateUpdate;
	
	// Constructors
	public InputTicketDTO(Integer id, String dateCreate, String supplierId, Long cost, Boolean status,
			String dateUpdate) {
		super();
		this.id = id;
		this.dateCreate = dateCreate;
		this.supplierId = supplierId;
		this.cost = cost;
		this.status = status;
		this.dateUpdate = dateUpdate;
	}

	// Setter - Getter
	public void setId(Integer id) {
		this.id = id;
	}

	public void setDateCreate(String dateCreate) {
		this.dateCreate = dateCreate;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
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

	public String getDateCreate() {
		return dateCreate;
	}

	public String getSupplierId() {
		return supplierId;
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
