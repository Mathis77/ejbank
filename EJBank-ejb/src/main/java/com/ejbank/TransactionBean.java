package com.ejbank;

import javax.ejb.Local;

import com.ejbank.pojos.*;

@Local
public interface TransactionBean {
	
	TransactionsPOJO getAllTransactionsFromAnAccount(long account_id, int offset, int user_id);

	long getAllTansactionsForAdvisorID(long advisor_id);

	OutputPreviewTransactionPOJO preview(InputPreviewTransactionPOJO ipt);

	OutputCommitTransactionPOJO commit(InputCommitTransactionPOJO ict);

}
