package DTO;

public class InputTicketDTO {
	// Properties
	private Integer id;
	private String dateCreate;
	private String suppliesId;
	private String ingredientId;
	private Integer sumQuantity;
	private Integer sumCost;
	private Boolean status;
	private String dateUpdate;

	// Constructors
	public InputTicketDTO(Integer id, String dateCreate, String suppliesId, String ingredientId, Integer sumQuantity,
			Integer sumCost, Boolean status, String dateUpdate) {
		super();
		this.id = id;
		this.dateCreate = dateCreate;
		this.suppliesId = suppliesId;
		this.ingredientId = ingredientId;
		this.sumQuantity = sumQuantity;
		this.sumCost = sumCost;
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

	public void setSuppliesId(String suppliesId) {
		this.suppliesId = suppliesId;
	}

	public void setIngredientId(String ingredientId) {
		this.ingredientId = ingredientId;
	}

	public void setSumQuantity(Integer sumQuantity) {
		this.sumQuantity = sumQuantity;
	}

	public void setSumCost(Integer sumCost) {
		this.sumCost = sumCost;
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

	public String getSuppliesId() {
		return suppliesId;
	}

	public String getIngredientId() {
		return ingredientId;
	}

	public Integer getSumQuantity() {
		return sumQuantity;
	}

	public Integer getSumCost() {
		return sumCost;
	}

	public Boolean getStatus() {
		return status;
	}

	public String getDateUpdate() {
		return dateUpdate;
	}
}
