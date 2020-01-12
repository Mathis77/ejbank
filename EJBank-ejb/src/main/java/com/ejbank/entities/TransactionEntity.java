package com.ejbank.entities;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ejbank_transaction")
@NamedQueries(value = { 
		@NamedQuery(name = "AllTransactionsFromUserId",
					query = "SELECT t FROM TransactionEntity t WHERE t.author.id = :userId ORDER BY t.date DESC"),
		@NamedQuery(name = "CountAllAccountTransaction",
					query = "SELECT COUNT(t.id) FROM TransactionEntity t")
})
public class TransactionEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "amount")
	private float amount;
	
	@NotNull
	@Column(name = "comment")
	private String comment;
	
	@Column(name = "applied")
	private int applied;

	@Column(name = "date")
	private LocalDate date;
	
	@ManyToOne
	@JoinColumn(name = "author")
	private UserEntity author;
	
	@ManyToOne
	@JoinColumn(name = "account_id_from")
	private AccountEntity accountFrom;
	
	@ManyToOne
	@JoinColumn(name = "account_id_to")
	private AccountEntity accountTo;

	public int getId() {
		return id;
	}

	public float getAmount() {
		return amount;
	}

	public String getComment() {
		return comment;
	}

	public int getApplied() {
		return applied;
	}

	public UserEntity getAuthor() {
		return author;
	}

	public LocalDate getDate() {
		return date;
	}

	public AccountEntity getAccountFrom() {
		return accountFrom;
	}

	public AccountEntity getAccountTo() {
		return accountTo;
	}

}
