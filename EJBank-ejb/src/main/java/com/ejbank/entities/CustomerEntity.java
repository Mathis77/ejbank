package com.ejbank.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "ejbank_customer")
public class CustomerEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	// FetchType.EAGER car on a besoin a chaque fois d'afficher tous les comptes des customers
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "id")
	private List<AccountEntity> accounts;
	
	public List<AccountEntity> getAccounts() {
		return accounts;
	}

}
