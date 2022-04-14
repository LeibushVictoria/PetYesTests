package com.petyes.tests;

import com.petyes.api.Login;
import com.petyes.api.Request;
import com.petyes.config.App;
import com.petyes.pages.BasePage;
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
    void createBuyRequestTest() {
        BasePage basePage = new BasePage();
        Login login = new Login();
        RequestPage requestPage = new RequestPage();

        login
                .loginByAPI(App.config.customerPhoneNumberAPI(), App.config.userPassword());
        basePage
                .openPage("/buy-add");
        requestPage
                .choosePetType("Собаки")
                .choosePetBreed("Австралийский келпи")
                .chooseCity("Санкт-Петербург");
        basePage
                .clickOnButton("Продолжить")
                .clickOnButton("Продолжить")
                .clickOnButton("Опубликовать")
                .clickOnButton("Перейти к запросу")
                .checkHeaderH3("Австралийский келпи");
    }

    @Test
    @DisplayName("Создание запроса на питомца (возьму бесплатно)")
    void createFreeRequestTest() {
        BasePage basePage = new BasePage();
        Login login = new Login();
        RequestPage requestPage = new RequestPage();

        login
                .loginByAPI(App.config.customerPhoneNumberAPI(), App.config.userPassword());
        basePage
                .openPage("/buy-add");
        requestPage
                .chooseRequestType("Возьму бесплатно в хорошие руки")
                .choosePetType("Собаки")
                .chooseCity("Санкт-Петербург");
        basePage
                .clickOnButton("Продолжить")
                .clickOnButton("Продолжить")
                .clickOnButton("Опубликовать")
                .clickOnButton("Перейти к запросу")
                .checkHeaderH3("Собаки");
    }

    @Test
    @AllureId("5702")
    @DisplayName("Удаление запроса на питомца")
    void deleteRequestTest() {
        BasePage basePage = new BasePage();
        Request request = new Request();
        RequestPage requestPage = new RequestPage();

        int id = request.createRequestByAPI();
        basePage
                .openPage("/buy/" + id)
                .clickOnButton("Удалить");
        requestPage
                .deleteRequest()
                .checkRequestDeleted();
    }
}