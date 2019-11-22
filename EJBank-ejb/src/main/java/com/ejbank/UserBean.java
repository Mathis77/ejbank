package com.ejbank;

import javax.ejb.Local;

import com.ejbank.pojos.CustomerPOJO;

@Local
public interface UserBean {
	
	CustomerPOJO getUserById(int id);

}
