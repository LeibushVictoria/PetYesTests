package com.petyes.config;

import com.petyes.api.Login;

public class AuthConfig {

    public static String breederToken = getBreederToken();
    public static String customerToken = getCustomerToken();
    public static String adminToken = getAdminToken();

    public static int breederId = getBreederId();
    public static int customerId = getCustomerId();

    public static String getBreederToken() {
        Login login = new Login();
        return login.loginByAPI(App.config.breederPhoneNumber(), App.config.userPassword());
    }

    public static String getCustomerToken() {
        Login login = new Login();
        return login.loginByAPI(App.config.customerPhoneNumber(), App.config.userPassword());
    }

    public static String getAdminToken() {
        Login login = new Login();
        return login.loginByAPI(App.config.adminPhoneNumber(), App.config.adminPassword());
    }

    public static int getBreederId() {
        Login login = new Login();
        return login.getUserId(breederToken);
    }

    public static int getCustomerId() {
        Login login = new Login();
        return login.getUserId(customerToken);
    }
}
