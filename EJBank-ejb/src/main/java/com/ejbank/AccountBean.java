package com.ejbank;

import java.util.List;

import javax.ejb.Local;

import com.ejbank.pojos.AccountPOJO;

@Local
public interface AccountBean {
	
	List<AccountPOJO> getAccountsById(int id);

}
