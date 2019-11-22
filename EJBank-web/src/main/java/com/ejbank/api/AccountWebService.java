package com.ejbank.api;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.ejbank.pojos.AccountPOJO;

@Path("/accounts")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class AccountWebService {
	
	@Path("/{userId}")
	@GET
	public List<AccountPOJO> getCustomerAccounts(@PathParam("userId") int id) {
		if(id < 0) throw new IllegalArgumentException("Identifiant négatif !"); // A voir si on fait une execption ou un POJO vide pour pas planter le serveur
		return null; /* <FIXME> */
	}

}
