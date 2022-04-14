package com.petyes.tests;

import com.petyes.config.App;
import com.petyes.pages.BasePage;
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
        BasePage basePage = new BasePage();
        LoginPage loginPage = new LoginPage();
        basePage
                .openPage("/login");
        loginPage
                .fillLoginForm(App.config.breederPhoneNumber(), App.config.userPassword());
        basePage
                .clickOnButton("Войти");
        loginPage
                .checkLogin("Находите хозяев для Ваших щенков и котят");
    }

    @Test
    @AllureId("5697")
    @DisplayName("Успешная авторизация покупателя")
    void loginAsCustomerTest() {
        BasePage basePage = new BasePage();
        LoginPage loginPage = new LoginPage();
        basePage
                .openPage("/login");
        loginPage
                .fillLoginForm(App.config.customerPhoneNumber(), App.config.userPassword());
        basePage
                .clickOnButton("Войти");
        loginPage
                .checkLogin("Ваш любимец в паре кликов от Вас");
    }

    @Test
    @AllureId("5699")
    @DisplayName("Авторизация с неправильным паролем")
    void loginWithWrongPasswordTest() {
        BasePage basePage = new BasePage();
        LoginPage loginPage = new LoginPage();
        basePage
                .openPage("/login");
        loginPage
                .fillLoginForm(App.config.customerPhoneNumber(), "WrongPassword");
        basePage
                .clickOnButton("Войти");
        loginPage
                .checkErrorMessage();
    }
}