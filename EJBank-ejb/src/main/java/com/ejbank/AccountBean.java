package com.ejbank;

import javax.ejb.Local;

import com.ejbank.pojos.AccountDetailsPOJO;
import com.ejbank.pojos.AccountsPOJO;

@Local
public interface AccountBean {
	
	AccountsPOJO getAccountsById(int id);

	AccountsPOJO getAllAccountsById(int id);
	
	AccountDetailsPOJO getAccountDetails(int accountId, int userId);

}
