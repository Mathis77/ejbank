package com.ejbank.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.ejbank.AccountBean;
import com.ejbank.entities.AccountEntity;
import com.ejbank.entities.AdvisorEntity;
import com.ejbank.entities.CustomerEntity;
import com.ejbank.entities.UserEntity;
import com.ejbank.pojos.*;

/**
 * Session Bean implementation class AccountBeanImpl
 */
@Stateless
@LocalBean
public class AccountBeanImpl implements AccountBean {
	
	@PersistenceContext(unitName = "EJBankPU")
	private EntityManager em;

	/**
	 * Get a User's accounts by its id
     * @see AccountBean#getAccountsById(int)
     */
    public AccountsPOJO getAccountsById(int id) {
        CustomerEntity result = em.find(CustomerEntity.class, id);
		if(result == null) return new AccountsPOJO(Collections.<AccountPOJO>emptyList(), ""); // S'il n'y a pas de compte pour l'utilisateur demandé
        List<AccountPOJO> pojos = new ArrayList<>();
        for(AccountEntity account : result.getAccounts()) {
        	pojos.add(new AccountWithoutUserPOJO(account.getId(), account.getAccountType().getName(), account.getBalance()));
        }
        return new AccountsPOJO(pojos, "");
    }

	@Override
	public AccountsPOJO getAllAccountsById(int id) {
		UserEntity result = em.find(UserEntity.class, id);
		if(result == null) return new AccountsPOJO(Collections.<AccountPOJO>emptyList(), ""); // S'il n'y a pas de compte pour l'utilisateur demandé
		List<AccountPOJO> pojos = new ArrayList<>();
		if(result instanceof AdvisorEntity) { // Si c'est un conseiller
			for(CustomerEntity ce : ((AdvisorEntity)result).getCustomers()) {
				for(AccountEntity account : ce.getAccounts()) {
					pojos.add(new AccountWithUserPOJO(account.getId(), account.getAccountType().getName(), account.getCustomer().getFirstname(), account.getBalance()));
				}
			}
		} else { // Si c'est un client
			for(AccountEntity account : ((CustomerEntity)result).getAccounts()) {
				pojos.add(new AccountWithUserPOJO(account.getId(), account.getAccountType().getName(), account.getCustomer().getFirstname(), account.getBalance()));
			}
		}
		return new AccountsPOJO(pojos, "");
	}

	public AccountDetailsPOJO getAccountDetails(int accountId, int userId) {
    	AccountEntity account = em.find(AccountEntity.class, accountId);
    	if(account == null) {
    		return new AccountDetailsPOJO("");
    	}
    	CustomerEntity customer = account.getCustomer();
    	if(customer.getId() != userId && customer.getAdvisor().getId() != userId) {
    		// Il ne s'agit ni du client, ni du conseiller
    		return new AccountDetailsPOJO("L'utilisateur qui tente d'accéder au compte numéro "+accountId+" n'en ait pas le possesseur. Il ne s'agit pas non plus du conseiller.");
    	}
    	float interest = account.getBalance() * (account.getAccountType().getRate() / 100f);
    	String owner = customer.getFirstname() + " " + customer.getLastname();
    	String advisor = customer.getAdvisor().getFirstname() + " " + customer.getAdvisor().getLastname();
    	return new AccountDetailsPOJO(owner, advisor, account.getAccountType().getRate(), interest, account.getBalance(), null);
    }

	@Override
	public AccountsPOJO getAllAccountsAttached(int advisorId) {
		
		
		//Get the list of customer for this advisor
		@SuppressWarnings("unchecked")
		List<CustomerEntity> customersList = (List<CustomerEntity>) em.createNamedQuery("AllClientsFromAdvisorId")
				.setParameter("advisorId",advisorId)
				.getResultList();
		
		
		//Get the list of accounts for each customer
		List<AccountPOJO> accountsList = new ArrayList<>();
		for(CustomerEntity customer : customersList) {
			for(AccountEntity account : customer.getAccounts()) {
				accountsList.add(new AccountsAttachedPOJO(((Integer)account.getId()).toString(), account.getCustomer().getFirstname()+" "+account.getCustomer().getLastname(),
						account.getAccountType().getName(),account.getBalance(), countAllTansactionsForAccount(account.getId())));
			}
		}
		
		return new AccountsPOJO(accountsList,"");
	}
	
	private long countAllTansactionsForAccount(int accountId) {
		long result = 0;
		//Get number of transactions for account
		result = (long) em.createNamedQuery("CountAllTransactionFromAccount")
				.setParameter("accountId", accountId)
				.setParameter("paramApplied",1)					
				.getSingleResult();
		return result;
	}

}
