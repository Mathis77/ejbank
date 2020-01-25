package com.ejbank.pojos;

public enum TransactionStateEnum {

    APPLYED,
    TO_APPROVE,
    WAITING_APPROVE;

    public static TransactionStateEnum getStateFromCode(int code, boolean advisor) {
        switch(code){
            case 0 : return APPLYED;
            case 1 : return advisor ? TO_APPROVE : WAITING_APPROVE;
            default: throw new AssertionError("The state code must be either 0 or 1 ! Current : "+code);
        }
    }
}
