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
     * @see AccountBean#getAccountsById(int)
     */
    public List<AccountPOJO> getAccountsById(int id) {
        CustomerEntity result = em.find(CustomerEntity.class, id);
//    	return result.getAccounts().stream()
//    				.map(a -> new AccountPOJO(a.getId(), a.getAccountType().getName(), a.getBalance()))
//    				.collect(Collectors.toList());
        List<AccountPOJO> pojos = new ArrayList<AccountPOJO>();
        if(result == null) return pojos;
        for(AccountEntity account : result.getAccounts()) {
        	pojos.add(new AccountPOJO(account.getId(), account.getAccountType().getName(), account.getBalance()));
        }
        return pojos;
    }

}
