package com.ejbank.impl;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.ejbank.UserBean;
import com.ejbank.entities.CustomerEntity;
import com.ejbank.pojos.CustomerPOJO;

@Stateless
@LocalBean
public class UserBeanImpl implements UserBean {
	
	@PersistenceContext(unitName = "EJBankPU")
	private EntityManager em;

	@Override
	public CustomerPOJO getUserById(int id) {
		CustomerEntity result = em.find(CustomerEntity.class, id);
		return new CustomerPOJO(result.getFirstname(), result.getLastname());
	}
	
	
}
