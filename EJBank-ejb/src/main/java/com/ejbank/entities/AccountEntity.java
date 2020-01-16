package com.ejbank.entities;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ejbank_account")
public class AccountEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "balance") 
	private float balance;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "account_type_id")
	private AccountTypeEntity accountType;
	
	@NotNull
	@ManyToOne
	private CustomerEntity customer;
	
	public int getId() {
		return id;
	}
	
	public float getBalance() {
		return balance;
	}
	
	public AccountTypeEntity getAccountType() {
		return accountType;
	}
	
	public CustomerEntity getCustomer() {
		return customer;
	}

	public void setBalance(float balance) {
		this.balance = balance;
	}
}
