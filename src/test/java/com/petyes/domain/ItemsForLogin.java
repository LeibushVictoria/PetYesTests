package com.petyes.domain;

import com.petyes.config.App;

public enum ItemsForLogin {
    BREEDER(App.config.breederPhoneNumber()),
    CUSTOMER(App.config.customerPhoneNumber());

    private final String phoneNumber;

    ItemsForLogin(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
