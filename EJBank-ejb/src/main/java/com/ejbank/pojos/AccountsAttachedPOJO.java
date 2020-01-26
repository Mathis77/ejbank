package com.ejbank.pojos;

public class AccountsAttachedPOJO implements AccountPOJO {
	private String id;
	private String user;
	private String type;
	private float amount;
	private long validation;
	
	public AccountsAttachedPOJO(String id, String user, String type, float amount, long validation) {
		this.id = id;
		this.user = user;
		this.type = type;
		this.amount = amount;
		this.validation = validation;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public long getValidation() {
		return validation;
	}

	public void setValidation(long validation) {
		this.validation = validation;
	}
	
	
	
}
