package com.ejbank.api;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.ejbank.AccountBean;
import com.ejbank.pojos.AccountDetailsPOJO;
import com.ejbank.pojos.AccountsPOJO;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class AccountWebService {
	
	@EJB
	private AccountBean accountBean;
	
	@Path("/accounts/{userId}") // For accounts/{userId}
	@GET
	public AccountsPOJO getCustomerAccounts(@PathParam("userId") int id) {
		if(id < 0) throw new IllegalArgumentException("Identifiant négatif !"); // A voir si on fait une exception ou un POJO vide pour pas planter le serveur
		return accountBean.getAccountsById(id);
	}

	@Path("/accounts/all/{userId}")
	@GET
	public AccountsPOJO getAllAccounts(@PathParam("userId") int id) {
		if(id < 0) throw new IllegalArgumentException("Identifiant négatif !");
		return accountBean.getAllAccountsById(id);
	}
	
	@Path("account/{account_id}/{user_id}")
	@GET
	public AccountDetailsPOJO getAccountDetails(@PathParam("account_id") int accountId, @PathParam("user_id") int userId) {
		return accountBean.getAccountDetails(accountId, userId);
	}
	
	@Path("/accounts/attached/{user_id}")
	@GET
	public AccountsPOJO getAllAccountsAttached(@PathParam("user_id") int id) {
		return accountBean.getAllAccountsAttached(id);
	}

}
