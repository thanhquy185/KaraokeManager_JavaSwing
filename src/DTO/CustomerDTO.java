package DTO;

public class CustomerDTO {
	// Properties
	private String id;
	private String idCard;
	private String fullname;
	private String birthday;
	private Boolean gender;
	private String phone;
	private String email;
	private String address;
	private Boolean status;
	private String dateUpdate;

	// Constructors
	public CustomerDTO(String id, String idCard, String fullname, String birthday, Boolean gender, String phone,
			String email, String address, Boolean status, String dateUpdate) {
		this.id = id;
		this.idCard = idCard;
		this.fullname = fullname;
		this.birthday = birthday;
		this.gender = gender;
		this.phone = phone;
		this.email = email;
		this.address = address;
		this.status = status;
		this.dateUpdate = dateUpdate;
	}

	// Setter - Getter
	public void setId(String id) {
		this.id = id;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public void setGender(Boolean gender) {
		this.gender = gender;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public String getIdCard() {
		return idCard;
	}

	public String getFullname() {
		return fullname;
	}

	public String getBirthday() {
		return birthday;
	}

	public Boolean getGender() {
		return gender;
	}

	public String getPhone() {
		return phone;
	}

	public String getEmail() {
		return email;
	}

	public String getAddress() {
		return address;
	}

	public Boolean getStatus() {
		return status;
	}

	public String getDateUpdate() {
		return dateUpdate;
	}

	public void setDateUpdate(String dateUpdate) {
		this.dateUpdate = dateUpdate;
	}
}
