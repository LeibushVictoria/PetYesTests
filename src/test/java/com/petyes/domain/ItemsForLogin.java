package com.petyes.domain;

public enum ItemsForLogin {
    BREEDER(DataBuilder.breederToken),
    CUSTOMER(DataBuilder.customerToken);

    private final String token;

    ItemsForLogin(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
