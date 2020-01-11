package com.ejbank.api;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.ejbank.TransactionBean;
import com.ejbank.pojos.TransactionsPOJO;

@Path("/transaction")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class TransactionWebService {
	
	@EJB
	private TransactionBean transactionBean;
	
	@Path("/list/{account_id}/{offset}/{user_id}")
	@GET
	public TransactionsPOJO getTransactions(@PathParam("account_id") int account_id, @PathParam("offset") int offset,
			@PathParam("user_id") int user_id) {
		return transactionBean.getAllTransactionsFromAnAccount(account_id, offset, user_id);
	}

}
