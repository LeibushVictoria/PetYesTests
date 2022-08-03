package com.petyes.domain;

import com.petyes.config.AuthConfig;

public enum ItemsForLogin {
    BREEDER(AuthConfig.breederToken),
    CUSTOMER(AuthConfig.customerToken);

    private final String token;

    ItemsForLogin(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
