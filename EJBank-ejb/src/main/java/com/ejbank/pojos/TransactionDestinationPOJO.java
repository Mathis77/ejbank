package com.ejbank.pojos;

import java.time.LocalDate;

public class TransactionDestinationPOJO implements TransactionPOJO {

	private final int id;
	private final LocalDate date;
	private final String source;
	private final String destination;
	private final String destination_user;
	private final float amount;
	private final String author;
	private final String comment;
	private final String state;
	
	public TransactionDestinationPOJO(int id, LocalDate date, String source, String destination, String destination_user,
									  float amount, String author, String comment, String state) {
		this.id = id;
		this.date = date;
		this.source = source;
		this.destination = destination;
		this.destination_user = destination_user;
		this.amount = amount;
		this.author = author;
		this.comment = comment;
		this.state = state;
	}

	public int getId() {
		return id;
	}

	public LocalDate getDate() {
		return date;
	}

	public String getSource() {
		return source;
	}

	public String getDestination() {
		return destination;
	}

	public String getDestination_user() {
		return destination_user;
	}

	public float getAmount() {
		return amount;
	}

	public String getAuthor() {
		return author;
	}

	public String getComment() {
		return comment;
	}

	public String getState() {
		return state;
	}

}
