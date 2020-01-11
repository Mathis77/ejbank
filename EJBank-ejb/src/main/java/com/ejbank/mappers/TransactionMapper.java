package com.ejbank.mappers;

import com.ejbank.entities.TransactionEntity;
import com.ejbank.pojos.TransactionDestinationPOJO;

public interface TransactionMapper {
	
	default TransactionDestinationPOJO getTransactionDestinationPOJOFromTransactionEntity(TransactionEntity te) {
		return new TransactionDestinationPOJO(te.getId(), te.getDate(), te.getAccountFrom().getAccountType().getName(), te.getAccountTo().getAccountType().getName(),
				te.getAccountTo().getCustomer().getFirstname(), te.getAmount(), te.getAuthor().getFirstname(), te.getComment(), te.getApplied());
	}

}
