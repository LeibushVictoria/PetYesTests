package com.petyes.tests;

import com.petyes.api.Login;
import com.petyes.config.App;
import com.petyes.pages.RequestPage;
import io.qameta.allure.AllureId;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Feature("Запрос")
public class RequestTests extends TestBase {

    @Test
    @AllureId("5701")
    @DisplayName("Создание запроса на питомца (куплю)")
    void CreateBuyRequestTest() {
        Login login = new Login();
        RequestPage requestPage = new RequestPage();

        login.loginByAPI(App.config.customerPhoneNumberAPI(), App.config.userPassword());
        requestPage
                .openCreateRequestPage()
                .choosePetType("Собаки")
                .choosePetBreed("Австралийский келпи")
                .chooseCity("Санкт-Петербург")
                .clickContinueButton()
                .clickContinueButton()
                .clickPublishButton()
                .openCreatedRequest()
                .checkRequestCreated("Австралийский келпи");
    }

    @Test
    @DisplayName("Создание запроса на питомца (возьму бесплатно)")
    void CreateFreeRequestTest() {
        Login login = new Login();
        RequestPage requestPage = new RequestPage();

        login.loginByAPI(App.config.customerPhoneNumberAPI(), App.config.userPassword());
        requestPage
                .openCreateRequestPage()
                .chooseRequestType("Возьму бесплатно в хорошие руки")
                .choosePetType("Собаки")
                .chooseCity("Санкт-Петербург")
                .clickContinueButton()
                .clickContinueButton()
                .clickPublishButton()
                .openCreatedRequest()
                .checkRequestCreated("Собаки");
    }

    @Test
    @AllureId("5702")
    @DisplayName("Удаление запроса на питомца")
    void DeleteRequestTest() {
        Login login = new Login();
        RequestPage requestPage = new RequestPage();

        login.loginByAPI(App.config.customerPhoneNumberAPI(), App.config.userPassword());
        requestPage
                .openRequestPage()
                .deleteRequest()
                .checkRequestDeleted();
    }
}