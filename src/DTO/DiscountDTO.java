package DTO;

public class DiscountDTO {
	// Properties
	private String id;
	private String name;
	private Integer percent;
	private Integer level;
	private String dateStart;
	private String dateEnd;
	private Boolean status;
	private String dateUpdate;

	// Constructors
	public DiscountDTO(String id, String name, Integer percent, Integer level, String dateStart, String dateEnd,
			Boolean status, String dateUpdate) {
		super();
		this.id = id;
		this.name = name;
		this.percent = percent;
		this.level = level;
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

	public void setLevel(Integer level) {
		this.level = level;
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

	public Integer getLevel() {
		return level;
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
