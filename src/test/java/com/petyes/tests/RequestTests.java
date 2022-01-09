package com.petyes.tests;

import com.petyes.config.App;
import com.petyes.pages.LoginPage;
import com.petyes.pages.RequestPage;
import io.qameta.allure.AllureId;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Feature("Запрос")
public class RequestTests extends TestBase {

    @Test
    @AllureId("5701")
    @DisplayName("Создание запроса на питомца")
    void CreateRequestTest() {
        LoginPage loginPage = new LoginPage();
        RequestPage requestPage = new RequestPage();

        loginPage.openLoginPage()
                .fillLoginForm(App.config.customerPhoneNumber(), App.config.userPassword())
                .clickSubmitButton()
                .checkLogin("Ваш любимец в паре кликов от Вас");
        requestPage
                .openCreateRequestPage()
                .choosePetType("Собаки")
                .choosePetBreed("Австралийский келпи")
                .clickContinueButton()
                .clickContinueButton()
                .clickPublishButton()
                .openCreatedRequest()
                .checkRequestCreated("Австралийский келпи");
    }

    @Test
    @AllureId("5702")
    @DisplayName("Удаление запроса на питомца")
    void DeleteRequestTest() {
        LoginPage loginPage = new LoginPage();
        RequestPage requestPage = new RequestPage();

        loginPage.openLoginPage()
                .fillLoginForm(App.config.customerPhoneNumber(), App.config.userPassword())
                .clickSubmitButton()
                .checkLogin("Ваш любимец в паре кликов от Вас");
        requestPage
                .openRequestPage()
                .deleteRequest()
                .checkRequestDeleted();
    }
}