package com.ejbank.pojos;

import java.util.Objects;

public class AccountWithUserPOJO implements AccountPOJO {

    private final int id;
    private final String type;
    private final String user;
    private final float amount;

    public AccountWithUserPOJO(int id, String type, String user, float amount) {
        this.id = id;
        this.type = Objects.requireNonNull(type);
        this.user = user;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getUser() {
        return user;
    }

    public float getAmount() {
        return amount;
    }

}
