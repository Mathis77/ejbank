package com.ejbank.pojos;

public class OutputCommitTransactionPOJO {

    private final boolean result;
    private final String message;

    public OutputCommitTransactionPOJO(boolean result, String message) {
        this.result = result;
        this.message = message;
    }

    public boolean isResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }
}
