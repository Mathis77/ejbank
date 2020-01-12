package com.ejbank.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.ejbank.TransactionBean;
import com.ejbank.entities.TransactionEntity;
import com.ejbank.mappers.TransactionMapper;
import com.ejbank.pojos.TransactionPOJO;
import com.ejbank.pojos.TransactionsPOJO;

@Stateless
@LocalBean
public class TransactionBeanImpl implements TransactionBean {
	
	private final static int LIMIT = 3;
	
	@PersistenceContext(unitName = "EJBankPU")
	private EntityManager em;

	private TransactionMapper mapper;

	@Override
	public TransactionsPOJO getAllTransactionsFromAnAccount(long account_id, int offset, int user_id) {
		long count = (long)em.createNamedQuery("CountAllAccountTransaction").getSingleResult();
		
		@SuppressWarnings("unchecked")
		List<TransactionEntity> transactions = (List<TransactionEntity>)em.createNamedQuery("AllTransactionsFromUserId")
				.setParameter("userId", user_id)
				.setFirstResult(offset) // OFFSET
				.setMaxResults(LIMIT) // LIMIT
				.getResultList();

		List<TransactionPOJO> transactionsPOJO = new ArrayList<>();
		for(TransactionEntity te : transactions) {
			transactionsPOJO.add(mapper.getTransactionDestinationPOJOFromTransactionEntity(te));
		}
		
		return new TransactionsPOJO(count, transactionsPOJO, "");
	}

}
