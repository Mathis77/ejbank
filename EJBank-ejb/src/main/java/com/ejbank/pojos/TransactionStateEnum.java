package com.ejbank.pojos;

public enum TransactionStateEnum {

    APPLYED,
    TO_APPROVE,
    WAITING_APPROVE;

    public static TransactionStateEnum getStateFromCode(int code) {
        switch(code){
            case 0 : return APPLYED;
            case 1 : return TO_APPROVE;
            case 2 : return WAITING_APPROVE;
            default: throw new AssertionError("The state code must be between 0 and 2 ! Current : "+code);
        }
    }
}
