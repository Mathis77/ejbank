package com.ejbank.pojos;

import java.util.List;

public class TransactionsPOJO {
	
	private final int total;
	private final List<TransactionDestinationPOJO> transactions;
	private final String error;
	
	public TransactionsPOJO(int total, List<TransactionDestinationPOJO> transactions, String error) {
		super();
		this.total = total;
		this.transactions = transactions;
		this.error = error;
	}
	
	public int getTotal() {
		return total;
	}
	public List<TransactionDestinationPOJO> getTransactions() {
		return transactions;
	}
	public String getError() {
		return error;
	}

}
