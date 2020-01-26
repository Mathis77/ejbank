package com.ejbank.impl;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.ejbank.TransactionBean;
import com.ejbank.entities.AccountEntity;
import com.ejbank.entities.AdvisorEntity;
import com.ejbank.entities.CustomerEntity;
import com.ejbank.entities.TransactionEntity;
import com.ejbank.entities.UserEntity;
import com.ejbank.pojos.InputCommitTransactionPOJO;
import com.ejbank.pojos.InputPreviewTransactionPOJO;
import com.ejbank.pojos.OutputCommitTransactionPOJO;
import com.ejbank.pojos.OutputPreviewTransactionPOJO;
import com.ejbank.pojos.ResponseValidationPOJO;
import com.ejbank.pojos.TransactionDestinationPOJO;
import com.ejbank.pojos.TransactionPOJO;
import com.ejbank.pojos.TransactionSourcePOJO;
import com.ejbank.pojos.TransactionStateEnum;
import com.ejbank.pojos.TransactionsPOJO;
import com.ejbank.pojos.ValidationCommitPOJO;

@Stateless
@LocalBean
public class TransactionBeanImpl implements TransactionBean {

	private final static int LIMIT = 3;

	@PersistenceContext(unitName = "EJBankPU")
	private EntityManager em;

	
	@Override
	public TransactionsPOJO getAllTransactionsFromAnAccount(int account_id, int offset, int user_id) {
		//long count = (long)em.createNamedQuery("CountAllAccountTransaction").getSingleResult();
		
		//get the account's customer
		int customerId = ((AccountEntity)em.find(AccountEntity.class, account_id)).getCustomer().getId();
		
		//is the advisor consulting ?
		boolean isAdvisor = customerId != user_id; // TODO fait-on quelque chose de plus sécu ??
		
		@SuppressWarnings("unchecked")
		List<TransactionEntity> transactions = (List<TransactionEntity>) em.createNamedQuery("AllTransactionsFromAccountID")
		.setParameter("accountId", account_id)
		.setFirstResult(offset) // OFFSET
		.setMaxResults(LIMIT)
		.getResultList();// LIMIT
		
		List<TransactionPOJO> transactionsPOJO = new ArrayList<>();
		for(TransactionEntity te : transactions) {
			transactionsPOJO.add(getTransactionDestinationPOJOFromTransactionEntity(te,isAdvisor,customerId));
		}

		return new TransactionsPOJO(transactions.size(), transactionsPOJO, "");
	}
	
	
	private TransactionPOJO getTransactionDestinationPOJOFromTransactionEntity(TransactionEntity te,boolean isAdvisor,int customerId) {
		//transaction_destination
		if(te.getAccountFrom().getCustomer().getId() == customerId) {
			return new TransactionDestinationPOJO(te.getId(), te.getDate().toLocalDate(), te.getAccountFrom().getAccountType().getName(), te.getAccountTo().getAccountType().getName(),
					te.getAccountFrom().getCustomer().getFirstname(), te.getAmount(), te.getAuthor().getFirstname(), te.getComment(), TransactionStateEnum.getStateFromCode(te.getApplied(),isAdvisor).toString());
		}
		//transaction_source
		else if(te.getAccountTo().getCustomer().getId() == customerId) {
			return new TransactionSourcePOJO(te.getId(), te.getDate().toLocalDate(), te.getAccountFrom().getAccountType().getName(), te.getAccountTo().getAccountType().getName(),
					te.getAccountTo().getCustomer().getFirstname(), te.getAmount(), te.getAuthor().getFirstname(), te.getComment(), TransactionStateEnum.getStateFromCode(te.getApplied(),isAdvisor).toString());
		} else {
			throw new AssertionError("A transaction has neither a source nor destination !");
		}
	}

	/**
	 * 
	 */
	@Override
	public long countAllTansactionsForAdvisorID(long advisorId) {
		int result = 0;

		//find all clients of advisor
		List<CustomerEntity> clients = getAllClientForCustomer(advisorId);

		//adding number of transactions to result for each client
		for(CustomerEntity client : clients) {
			result += (long) em.createNamedQuery("CountAllTransactionFromUserId")
					.setParameter("userId", client.getId())
					.setParameter("paramApplied",1)					
					.getSingleResult();
		}

		return result;
	}
	


	private List<CustomerEntity> getAllClientForCustomer(long advisorId) {
		//find all clients of advisor
		@SuppressWarnings("unchecked")
		List<CustomerEntity> clients = (List<CustomerEntity>)em.createNamedQuery("AllClientsFromAdvisorId")
		.setParameter("advisorId", advisorId)
		.getResultList();
		return clients;
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


	@Override
	public ResponseValidationPOJO validate(ValidationCommitPOJO validation) {
		int transactionId = Integer.parseInt(validation.getTransaction());
		int authorId = Integer.parseInt(validation.getAuthor());
		System.out.println("---------transactionId :"+transactionId+"---authorId :"+authorId+"---------");
		
		TransactionEntity transaction = (TransactionEntity) em.find(TransactionEntity.class,transactionId);
		int accountId =  transaction.getAccountFrom().getId();
		System.out.println("------------Recuperation accountId :"+accountId+"----------------");
		int customerId = ((AccountEntity)em.find(AccountEntity.class,accountId)).getCustomer().getId();
		System.out.println("-------------Recuperation customerId :"+customerId+"--------------");
		int advisorId = ((CustomerEntity)em.find(CustomerEntity.class,customerId)).getAdvisor().getId();
		System.out.println("-------------Recuperation advisorId :"+advisorId+"------------------------");
		if(authorId != advisorId) {
			return new ResponseValidationPOJO(false, "Retour du serveur", "L'auteur n'est pas le conseiller du compte");
		}
		
		boolean isValid = Boolean.parseBoolean(validation.isApprove());
		if(isValid) {
			System.out.println("--------------Transaction is valid-----------------");
			((TransactionEntity) em.find(TransactionEntity.class,transactionId)).setApplied(0);
			em.flush();
		}else {
			System.out.println("-----------Transaction is not valid------------------");
			((TransactionEntity) em.find(TransactionEntity.class,transactionId)).setApplied(1);//TODO Mettre un code de refus
			em.flush();
		}
		
		System.out.println("-----------------End of flush----------------");
		return new ResponseValidationPOJO(true, "Retour du serveur", "");
	}
	


}
