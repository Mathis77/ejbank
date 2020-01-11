package com.ejbank.pojos;

import java.util.List;

public class AccountsPOJO {
	
	private final List<AccountPOJO> accounts;
	private final String error;
	
	public AccountsPOJO(List<AccountPOJO> accounts, String error) {
		this.accounts = accounts;
		this.error = error;
	}
	
	public List<AccountPOJO> getAccounts() {
		return accounts;
	}
	
	public String getError() {
		return error;
	}

}
