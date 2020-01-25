package com.ejbank.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "ejbank_customer")
@DiscriminatorValue("customer")
@NamedQueries(value = { 
		@NamedQuery(name = "AllClientsFromAdvisorId",
					query = "SELECT t FROM CustomerEntity t WHERE t.advisor.id = :advisorId")
})
public class CustomerEntity extends UserEntity {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	// FetchType.EAGER car on a besoin a chaque fois d'afficher tous les comptes des customers
	@OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
	private List<AccountEntity> accounts;
	
	@ManyToOne
	private AdvisorEntity advisor;
	
	public int getId() {
		return id;
	}
	
	public List<AccountEntity> getAccounts() {
		return accounts;
	}
	
	public AdvisorEntity getAdvisor() {
		return advisor;
	}

}
