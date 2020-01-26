package com.ejbank.pojos;

public class ValidationCommitPOJO {
	private String transaction;
	private String approve;
	private String author;
	
	public String getTransaction() {
		return transaction;
	}
	
	public void setTransaction(String transaction) {
		this.transaction = transaction;
	}
	
	public String isApprove() {
		return approve;
	}
	
	public void setApprove(String approve) {
		this.approve = approve;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}

	@Override
	public String toString() {
		return "ValidationCommitPOJO [transaction=" + transaction + ", approve=" + approve + ", author=" + author + "]";
	}
	
	
}
