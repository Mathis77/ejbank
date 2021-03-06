package com.ejbank.pojos;

public class InputPreviewTransactionPOJO {

    private int source;
    private int destination;
    private float amount;
    private int author;

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public int getDestination() {
        return destination;
    }

    public void setDestination(int destination) {
        this.destination = destination;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public int getAuthor() {
        return author;
    }

    public void setAuthor(int author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "InputPreviewTransaction{" +
                "source=" + source +
                ", destination=" + destination +
                ", amount=" + amount +
                ", author=" + author +
                '}';
    }
}
