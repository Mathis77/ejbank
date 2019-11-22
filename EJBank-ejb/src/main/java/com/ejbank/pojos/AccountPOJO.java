package com.ejbank.pojos;

import java.util.Objects;

public class AccountPOJO {
	
	private final int id;
	private final String type;
	private final float amount;
	
	public AccountPOJO(int id, String type, float amount) {
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
