package DTO;

import java.math.BigInteger;

public class DiscountDTO {
	// Properties
	private String id;
	private String name;
	private String discountType;
	private Long value;
	private Long costMin;
	private Long costMax;
	private String dateStart;
	private String dateEnd;
	private Boolean status;
	private String dateUpdate;
	
	// Constructos
	public DiscountDTO(String id, String name, String discountType, Long value, Long costMin, Long costMax,
			String dateStart, String dateEnd, Boolean status, String dateUpdate) {
		super();
		this.id = id;
		this.name = name;
		this.discountType = discountType;
		this.value = value;
		this.costMin = costMin;
		this.costMax = costMax;
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
		this.status = status;
		this.dateUpdate = dateUpdate;
	}

	// Setter - Getter
	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}

	public void setValue(Long value) {
		this.value = value;
	}

	public void setCostMin(Long costMin) {
		this.costMin = costMin;
	}

	public void setCostMax(Long costMax) {
		this.costMax = costMax;
	}

	public void setDateStart(String dateStart) {
		this.dateStart = dateStart;
	}

	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public void setDateUpdate(String dateUpdate) {
		this.dateUpdate = dateUpdate;
	}
	
	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDiscountType() {
		return discountType;
	}

	public Long getValue() {
		return value;
	}

	public Long getCostMin() {
		return costMin;
	}

	public Long getCostMax() {
		return costMax;
	}

	public String getDateStart() {
		return dateStart;
	}

	public String getDateEnd() {
		return dateEnd;
	}

	public Boolean getStatus() {
		return status;
	}

	public String getDateUpdate() {
		return dateUpdate;
	}	
}
