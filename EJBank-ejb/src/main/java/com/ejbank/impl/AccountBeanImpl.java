package com.ejbank.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.ejbank.AccountBean;
import com.ejbank.entities.AccountEntity;
import com.ejbank.entities.CustomerEntity;
import com.ejbank.pojos.AccountDetailsPOJO;
import com.ejbank.pojos.AccountPOJO;

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
    public List<AccountPOJO> getAccountsById(int id) {
        CustomerEntity result = em.find(CustomerEntity.class, id);
        List<AccountPOJO> pojos = new ArrayList<AccountPOJO>();
        if(result == null) return pojos; // S'il n'y a pas de compte pour l'utilisateur demandé
        for(AccountEntity account : result.getAccounts()) {
        	pojos.add(new AccountPOJO(account.getId(), account.getAccountType().getName(), account.getBalance()));
        }
        return pojos;
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

}
