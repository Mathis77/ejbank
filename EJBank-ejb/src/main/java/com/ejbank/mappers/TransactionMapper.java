package com.ejbank.mappers;

import com.ejbank.entities.TransactionEntity;
import com.ejbank.pojos.TransactionDestinationPOJO;
import com.ejbank.pojos.TransactionPOJO;
import com.ejbank.pojos.TransactionStateEnum;

public interface TransactionMapper {
	
	default TransactionPOJO getTransactionDestinationPOJOFromTransactionEntity(TransactionEntity te) {
		if(te.getAccountFrom() == null && te.getAccountTo() != null) {
			return new TransactionDestinationPOJO(te.getId(), te.getDate().toLocalDate(), te.getAccountFrom().getAccountType().getName(), te.getAccountTo().getAccountType().getName(),
					te.getAccountTo().getCustomer().getFirstname(), te.getAmount(), te.getAuthor().getFirstname(), te.getComment(), TransactionStateEnum.getStateFromCode(te.getApplied()).toString());
		} else if(te.getAccountTo() == null && te.getAccountFrom() != null) {
			return new TransactionDestinationPOJO(te.getId(), te.getDate().toLocalDate(), te.getAccountFrom().getAccountType().getName(), te.getAccountTo().getAccountType().getName(),
					te.getAccountFrom().getCustomer().getFirstname(), te.getAmount(), te.getAuthor().getFirstname(), te.getComment(), TransactionStateEnum.getStateFromCode(te.getApplied()).toString());
		} else {
			throw new AssertionError("A transaction has neither a source nor destination !");
		}

	}

}
