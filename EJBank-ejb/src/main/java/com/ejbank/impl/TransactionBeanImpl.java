package com.ejbank.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.ejbank.TransactionBean;
import com.ejbank.entities.AccountEntity;
import com.ejbank.entities.TransactionEntity;
import com.ejbank.mappers.TransactionMapper;
import com.ejbank.pojos.InputPreviewTransactionPOJO;
import com.ejbank.pojos.OutputPreviewTransactionPOJO;
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

	@Override
	public OutputPreviewTransactionPOJO preview(InputPreviewTransactionPOJO ipt) {
		// Search for the src account and dst one
		AccountEntity src = em.find(AccountEntity.class, ipt.getSource());
		AccountEntity dst = em.find(AccountEntity.class, ipt.getDestination());

		// Do some process
		float before = src.getBalance() - ipt.getAmount();
		float after = dst.getBalance() + ipt.getAmount();
		boolean result = true;
		String message = "";

		// Check
		if(before < 0) {
			result = false;
			message = "Vous ne disposez pas d'un solde suffisant.";
		}

		return new OutputPreviewTransactionPOJO(result, before, after, message, "");
	}

}
