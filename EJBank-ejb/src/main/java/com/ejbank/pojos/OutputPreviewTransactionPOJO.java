package com.ejbank.pojos;

public class OutputPreviewTransactionPOJO {

    private final boolean result;
    private final float before;
    private final float after;
    private final String message;
    private final String error;

    public OutputPreviewTransactionPOJO(boolean result, float before, float after, String message, String error) {
        this.result = result;
        this.before = before;
        this.after = after;
        this.message = message;
        this.error = error;
    }

    public boolean isResult() {
        return result;
    }

    public float getBefore() {
        return before;
    }

    public float getAfter() {
        return after;
    }

    public String getMessage() {
        return message;
    }

    public String getError() {
        return error;
    }
}
