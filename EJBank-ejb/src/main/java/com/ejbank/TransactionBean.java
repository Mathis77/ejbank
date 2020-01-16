package com.ejbank;

import javax.ejb.Local;

import com.ejbank.pojos.InputPreviewTransactionPOJO;
import com.ejbank.pojos.OutputPreviewTransactionPOJO;
import com.ejbank.pojos.TransactionsPOJO;

@Local
public interface TransactionBean {
	
	TransactionsPOJO getAllTransactionsFromAnAccount(long account_id, int offset, int user_id);

	OutputPreviewTransactionPOJO preview(InputPreviewTransactionPOJO ipt);

}
