package com.petyes.domain;


import com.petyes.config.App;

public enum ItemsForLogin {
    BREEDER(App.config.breederPhoneNumberAPI()),
    CUSTOMER(App.config.customerPhoneNumberAPI());

    private final String phoneNumber;

    ItemsForLogin(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
