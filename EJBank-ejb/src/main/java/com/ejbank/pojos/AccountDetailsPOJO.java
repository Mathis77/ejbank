package com.ejbank.pojos;

public class AccountDetailsPOJO {
	
	private final String owner;
	private final String advisor;
	private final float rate;
	private final float interest;
	private final float amount;
	private final String error;
	
	public AccountDetailsPOJO(String owner, String advisor, float rate, float interest, float amount, String error) {
		this.owner = owner;
		this.advisor = advisor;
		this.rate = rate;
		this.interest = interest;
		this.amount = amount;
		this.error = error;
	}
	
	public AccountDetailsPOJO(String error) {
		this(null, null, 0, 0, 0, error);
	}

	public String getOwner() {
		return owner;
	}

	public String getAdvisor() {
		return advisor;
	}

	public float getRate() {
		return rate;
	}

	public float getInterest() {
		return interest;
	}

	public float getAmount() {
		return amount;
	}
	
	public String getError() {
		return error;
	}
	
}
