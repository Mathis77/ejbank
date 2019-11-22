package com.ejbank.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ejbank_account_type")
public class AccountTypeEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@NotNull
	@Column(name = "name")
	private String name;
	
	@Column(name = "rate")
	private float rate;
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public float getRate() {
		return rate;
	}

}
