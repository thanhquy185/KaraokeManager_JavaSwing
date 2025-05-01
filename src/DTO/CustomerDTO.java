package DTO;

public class CustomerDTO {
	// Properties
	private String idCard;
	private String type;
	private String fullname;
	private String birthday;
	private String gender;
	private String phone;
	private String email;
	private String address;
	private Boolean status;
	private String timeUpdate;

	// Constructors
	public CustomerDTO(String idCard, String type, String fullname, String birthday, String gender, String phone,
			String email, String address, Boolean status, String timeUpdate) {
		this.idCard = idCard;
		this.type = type;
		this.fullname = fullname;
		this.birthday = birthday;
		this.gender = gender;
		this.phone = phone;
		this.email = email;
		this.address = address;
		this.status = status;
		this.timeUpdate = timeUpdate;
	}

	// Setter - Getter

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	
	public void setType(String type) {
		this.type = type;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public void setGender(String gender) {
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


	public String getIdCard() {
		return idCard;
	}

	public String getType() {
		return type;
	}
	
	public String getFullname() {
		return fullname;
	}

	public String getBirthday() {
		return birthday;
	}

	public String getGender() {
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

	public String getTimeUpdate() {
		return timeUpdate;
	}

	public void setTimeUpdate(String timeUpdate) {
		this.timeUpdate = timeUpdate;
	}
}
