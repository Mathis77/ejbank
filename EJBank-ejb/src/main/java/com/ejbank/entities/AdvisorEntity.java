package com.ejbank.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "ejbank_advisor")
@DiscriminatorValue("advisor")
public class AdvisorEntity extends UserEntity {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@OneToMany(mappedBy = "advisor", fetch = FetchType.LAZY)
	private List<CustomerEntity> customers;
	
	public int getId() {
		return id;
	}
	
	public List<CustomerEntity> getCustomers() {
		return customers;
	}

}
