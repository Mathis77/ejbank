package com.ejbank.api;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.ejbank.UserBean;
import com.ejbank.pojos.CustomerPOJO;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class UserWebService {
	
	@EJB
	private UserBean userBean;
	
	@Path("/{userId}")
	@GET
	public CustomerPOJO getCustomer(@PathParam("userId") int id) {
		if(id < 0) throw new IllegalArgumentException("Identifiant négatif !"); // A voir si on fait une execption ou un POJO vide pour pas planter le serveur
		return userBean.getUserById(id);
	}
	
}
