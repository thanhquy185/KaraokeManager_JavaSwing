package DTO;

public class PrivilegeDetailDTO {
	// Properties
	private int accountId;
	private String privilegeId;
	private String functionId;
	private Boolean status;

	// Constructors
	public PrivilegeDetailDTO(Integer accountId, String privilegeId, String functionId, Boolean status) {
		super();
		this.accountId = accountId;
		this.privilegeId = privilegeId;
		this.functionId = functionId;
		this.status = status;
	}

	// Setter - Getter
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public void setPrivilegeId(String privilegeId) {
		this.privilegeId = privilegeId;
	}

	public void setFunctionId(String functionId) {
		this.functionId = functionId;
	}

	public Boolean getStatus() {
		return status;
	}

	public int getAccountId() {
		return accountId;
	}

	public String getPrivilegeId() {
		return privilegeId;
	}

	public String getFunctionId() {
		return functionId;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "PrivilegeDetailDTO [accountId=" + accountId + ", privilegeId=" + privilegeId + ", functionId="
				+ functionId + ", status=" + status + "]";
	}
}
