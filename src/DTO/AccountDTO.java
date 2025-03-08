package DTO;

public class AccountDTO {
	// Properties
	private int id;
	private String fullname;
	private String phone;
	private String email;
	private String address;
	private String username;
	private String password;
	private String privilegeId;
	private boolean status;
	private String dateUpdate;

	// Constructors
	public AccountDTO(Integer id, String fullname, String phone, String email, String address, String username,
			String password, String privilegeId, Boolean status, String dateUpdate) {
		super();
		this.id = id;
		this.fullname = fullname;
		this.phone = phone;
		this.email = email;
		this.address = address;
		this.username = username;
		this.password = password;
		this.privilegeId = privilegeId;
		this.status = status;
		this.dateUpdate = dateUpdate;
	}

	// Setter - Getter
	public void setId(Integer id) {
		this.id = id;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPrivilegeId(String privilegeId) {
		this.privilegeId = privilegeId;
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

	public String getFullname() {
		return fullname;
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

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getPrivilegeId() {
		return privilegeId;
	}

	public Boolean getStatus() {
		return status;
	}

	public String getDateUpdate() {
		return dateUpdate;
	}

	@Override
	public String toString() {
		return "AccountDTO [id=" + id + ", fullname=" + fullname + ", phone=" + phone + ", email=" + email
				+ ", address=" + address + ", username=" + username + ", password=" + password + ", privilegeId="
				+ privilegeId + ", status=" + status + ", dateUpdate=" + dateUpdate + "]";
	}

}
