package com.ejbank.pojos;

import java.util.Objects;

public class AccountWithoutUserPOJO implements AccountPOJO{
	
	private final int id;
	private final String type;
	private final float amount;
	
	public AccountWithoutUserPOJO(int id, String type, float amount) {
		this.id = id;
		this.type = Objects.requireNonNull(type);
		this.amount = amount;
	}
	
	public int getId() {
		return id;
	}
	
	public String getType() {
		return type;
	}
	
	public float getAmount() {
		return amount;
	}

}
