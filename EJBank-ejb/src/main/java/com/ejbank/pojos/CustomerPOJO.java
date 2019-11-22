package com.ejbank.pojos;

import java.util.Objects;

public class CustomerPOJO {
	
	private final String firstname;
	private final String lastname;
	
	public CustomerPOJO(String firstname, String lastname) {
		this.firstname = Objects.requireNonNull(firstname);
		this.lastname = Objects.requireNonNull(lastname);
	}
	
	public String getFirstname() {
		return firstname;
	}
	
	public String getLastname() {
		return lastname;
	}

}
