package com.ejbank.api;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.ejbank.TransactionBean;
import com.ejbank.pojos.*;

@Path("/transaction")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
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
	
	@Path("/validation/notification/{user_id}")
	@GET
	public long getTansactionsFromAdvisor(@PathParam("user_id") long user_id) {
		return transactionBean.countAllTansactionsForAdvisorID(user_id);
	}

	@Path("/preview")
	@POST
	public OutputPreviewTransactionPOJO preview(InputPreviewTransactionPOJO ipt) {
		if(ipt.getAmount() <= 0) return new OutputPreviewTransactionPOJO(false, 0, 0, "", "Le montant ne peut pas être négatif");
		return transactionBean.preview(ipt);
	}

	@POST
	public OutputCommitTransactionPOJO commit(InputCommitTransactionPOJO ict) {
		return transactionBean.commit(ict);
	}
	
	@Path("/validation")
	@POST
	public ResponseValidationPOJO valid(ValidationCommitPOJO validation) {
		System.out.println("----------------Entrer web service validation---------------------------");
		System.out.println(validation);
		return transactionBean.validate(validation);
	}

}
