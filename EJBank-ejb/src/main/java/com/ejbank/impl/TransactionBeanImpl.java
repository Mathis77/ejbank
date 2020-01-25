package com.ejbank.impl;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.ejbank.TransactionBean;
import com.ejbank.entities.CustomerEntity;
import com.ejbank.entities.AccountEntity;
import com.ejbank.entities.AdvisorEntity;
import com.ejbank.entities.TransactionEntity;
import com.ejbank.entities.UserEntity;
import com.ejbank.mappers.TransactionMapper;
import com.ejbank.pojos.*;

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
	
	/**
	 * 
	 */
	@Override
	public long getAllTansactionsForAdvisorID(long advisorId) {
		long result = 0;
		
		//find all clients of advisor
		@SuppressWarnings("unchecked")
		List<CustomerEntity> clients = (List<CustomerEntity>)em.createNamedQuery("AllClientsFromAdvisorId")
				.setParameter("advisorId", advisorId)
				.getResultList();
		
		//adding number of transactions to result for each client
		for(CustomerEntity client : clients) {
			result += (long) em.createNamedQuery("CountAllTransactionFromUserId")
					.setParameter("userId", client.getId())
					.setParameter("paramApplied",1)					
					.getSingleResult();
		}
		
		return result;
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

	@Override
	public OutputCommitTransactionPOJO commit(InputCommitTransactionPOJO ict) {
		// Search for the src account and dst one
		AccountEntity src = em.find(AccountEntity.class, ict.getSource());
		AccountEntity dst = em.find(AccountEntity.class, ict.getDestination());

		// Search the author account
		UserEntity author = em.find(UserEntity.class, ict.getAuthor());

		// Update the balance of each account if the amount is less than 1000 euros
		if(ict.getAmount() < 1000) {
			src.setBalance(src.getBalance() - ict.getAmount());
			dst.setBalance(dst.getBalance() + ict.getAmount());
		}

		// Create a transaction in DB
		int applied; // code 0 : OK / code 1 : To_approve. @see #TransactionStateEnum
		if(author instanceof AdvisorEntity) applied = 0; // If the author is an advisor, he does not need permission to make virement with an amount
		else applied = ict.getAmount() > 1000 ? 1 : 0;

		// Persist the transaction
		Date date = Date.valueOf(LocalDate.now());
		TransactionEntity newTransaction = new TransactionEntity(ict.getAmount(), ict.getComment(), applied, date, author, src, dst);
		em.persist(newTransaction);

		String message;
		if(applied == 1) message = "La somme excédant 1000€, votre virement est en attente de validation par votre conseiller.";
		else message = "Le virement a bien été pris en compte.";
		return new OutputCommitTransactionPOJO(true, message); // Use implicit flush to update values for both account src and dst
	}

}
