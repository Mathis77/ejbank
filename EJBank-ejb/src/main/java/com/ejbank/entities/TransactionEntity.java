package com.ejbank.entities;

import java.io.Serializable;
import java.sql.Date;
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
					query = "SELECT t FROM TransactionEntity t WHERE t.accountFrom.id = :accountId OR t.accountTo.id = :accountId ORDER BY t.date DESC"),
		@NamedQuery(name = "CountAllAccountTransaction",
					query = "SELECT COUNT(t.id) FROM TransactionEntity t"),
		@NamedQuery(name = "CountAllTransactionFromUserId",
				query = "SELECT COUNT(t.id) FROM TransactionEntity t WHERE t.author.id = :userId AND t.applied = :paramApplied ORDER BY t.date DESC")
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
	private Date date;
	
	@ManyToOne
	@JoinColumn(name = "author")
	private UserEntity author;
	
	@ManyToOne
	@JoinColumn(name = "account_id_from")
	private AccountEntity accountFrom;
	
	@ManyToOne
	@JoinColumn(name = "account_id_to")
	private AccountEntity accountTo;

	public TransactionEntity() {
		// Empty for JPA
	}

	public TransactionEntity(float amount, String comment, int applied, Date date, UserEntity author, AccountEntity accountFrom, AccountEntity accountTo) {
		this.amount = amount;
		this.comment = comment;
		this.applied = applied;
		this.date = date;
		this.author = author;
		this.accountFrom = accountFrom;
		this.accountTo = accountTo;
	}

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

	public Date getDate() {
		return date;
	}

	public AccountEntity getAccountFrom() {
		return accountFrom;
	}

	public AccountEntity getAccountTo() {
		return accountTo;
	}

	@Override
	public String toString() {
		return "TransactionEntity [id=" + id + ", amount=" + amount + ", comment=" + comment + ", applied=" + applied
				+ ", date=" + date + ", author=" + author + ", accountFrom=" + accountFrom + ", accountTo=" + accountTo
				+ "]";
	}
	
	

}
