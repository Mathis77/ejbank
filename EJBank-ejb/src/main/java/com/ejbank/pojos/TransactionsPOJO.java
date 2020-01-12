package com.ejbank.pojos;

import java.util.List;

public class TransactionsPOJO {
	
	private final long total;
	private final List<TransactionPOJO> transactions;
	private final String error;
	
	public TransactionsPOJO(long total, List<TransactionPOJO> transactions, String error) {
		this.total = total;
		this.transactions = transactions;
		this.error = error;
	}
	
	public long getTotal() {
		return total;
	}
	public List<TransactionPOJO> getTransactions() {
		return transactions;
	}
	public String getError() {
		return error;
	}

}
