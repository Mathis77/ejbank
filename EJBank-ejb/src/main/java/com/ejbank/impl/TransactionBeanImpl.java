package com.ejbank.impl;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.ejbank.TransactionBean;
import com.ejbank.entities.TransactionEntity;
import com.ejbank.pojos.TransactionsPOJO;

@Stateless
@LocalBean
public class TransactionBeanImpl implements TransactionBean {
	
	private final static int LIMIT = 3;
	
	@PersistenceContext(unitName = "EJBankPU")
	private EntityManager em;

	@Override
	public TransactionsPOJO getAllTransactionsFromAnAccount(int account_id, int offset, int user_id) {
		int count = (int)em.createNamedQuery("countAll").getSingleResult();
		
		@SuppressWarnings("unchecked")
		List<TransactionEntity> transactions = (List<TransactionEntity>)em.createNamedQuery("AllTransactionsFromUserId")
				.setParameter("userId", user_id)
				.setParameter("limit", LIMIT)
				.setParameter("offset", offset)
				.getResultList();


		
		return new TransactionsPOJO(count, transactions, "");
	}

}
