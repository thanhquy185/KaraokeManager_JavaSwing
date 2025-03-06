package DTO;

import java.math.BigInteger;

public class DiscountDTO {
	// Properties
	private String id;
	private String name;
	private Integer percent;
	private Long roomCost;
	private String dateStart;
	private String dateEnd;
	private Boolean status;
	private String dateUpdate;

	// Constructors
	public DiscountDTO(String id, String name, Integer percent, Long roomCost, String dateStart, String dateEnd,
			Boolean status, String dateUpdate) {
		super();
		this.id = id;
		this.name = name;
		this.percent = percent;
		this.roomCost = roomCost;
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

	public void setPercent(Integer percent) {
		this.percent = percent;
	}

	public void setRoomCost(Long roomCost) {
		this.roomCost = roomCost;
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

	public Integer getPercent() {
		return percent;
	}

	public Long getRoomCost() {
		return roomCost;
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
