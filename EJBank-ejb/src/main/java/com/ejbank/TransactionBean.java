package com.ejbank;

import javax.ejb.Local;

import com.ejbank.pojos.TransactionsPOJO;

@Local
public interface TransactionBean {
	
	TransactionsPOJO getAllTransactionsFromAnAccount(int account_id, int offset, int user_id);

}
