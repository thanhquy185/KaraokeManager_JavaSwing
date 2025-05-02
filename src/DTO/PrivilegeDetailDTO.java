package DTO;

public class PrivilegeDetailDTO {
	// Properties
	private int accountId;
	private String functionId;

	// Constructors
	public PrivilegeDetailDTO(int accountId, String functionId) {
		this.accountId = accountId;
		this.functionId = functionId;
	}

	// Getter - Setter
	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public String getFunctionId() {
		return functionId;
	}

	public void setFunctionId(String functionId) {
		this.functionId = functionId;
	}

}
