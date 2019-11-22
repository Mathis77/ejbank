package com.ejbank.api;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.ejbank.AccountBean;
import com.ejbank.pojos.AccountDetailsPOJO;
import com.ejbank.pojos.AccountPOJO;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class AccountWebService {
	
	@EJB
	private AccountBean accountBean;
	
	@Path("/accounts/{userId}") // For accounts/{userId}
	@GET
	public List<AccountPOJO> getCustomerAccounts(@PathParam("userId") int id) {
		if(id < 0) throw new IllegalArgumentException("Identifiant négatif !"); // A voir si on fait une exception ou un POJO vide pour pas planter le serveur
		return accountBean.getAccountsById(id);
	}
	
	@Path("account/{account_id}/{user_id}")
	@GET
	public AccountDetailsPOJO getAccountDetails(@PathParam("account_id") int accountId, @PathParam("user_id") int userId) {
		return accountBean.getAccountDetails(accountId, userId);
	}

}
