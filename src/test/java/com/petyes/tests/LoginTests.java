package com.petyes.tests;

import com.petyes.config.App;
import com.petyes.pages.LoginPage;
import io.qameta.allure.AllureId;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.*;

@Feature("Авторизация")
public class LoginTests extends TestBase {

    @Test
    @AllureId("5696")
    @DisplayName("Успешная авторизация продавца")
    void loginAsBreederTest() {
        LoginPage loginPage = new LoginPage();
        loginPage
                .openLoginPage()
                .fillLoginForm(App.config.breederPhoneNumber(), App.config.userPassword())
                .clickSubmitButton()
                .checkLogin("Находите хозяев для Ваших щенков и котят");
    }

    @Test
    @AllureId("5697")
    @DisplayName("Успешная авторизация покупателя")
    void loginAsCustomerTest() {
        LoginPage loginPage = new LoginPage();
        loginPage
                .openLoginPage()
                .fillLoginForm(App.config.customerPhoneNumber(), App.config.userPassword())
                .clickSubmitButton()
                .checkLogin("Ваш любимец в паре кликов от Вас");
    }

    @Test
    @AllureId("5699")
    @DisplayName("Авторизация с неправильным паролем")
    void loginWithWrongPasswordTest() {
        LoginPage loginPage = new LoginPage();
        loginPage
                .openLoginPage()
                .fillLoginForm(App.config.customerPhoneNumber(), "WrongPassword")
                .clickSubmitButton()
                .checkErrorMessage();
    }
}