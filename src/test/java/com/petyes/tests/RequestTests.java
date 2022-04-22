package com.petyes.tests;

import com.petyes.api.Login;
import com.petyes.api.Request;
import com.petyes.config.App;
import com.petyes.pages.BasePage;
import com.petyes.pages.RequestPage;
import com.petyes.pages.components.CityComponent;
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
        CityComponent cityComponent = new CityComponent();

        login
                .loginByAPI(App.config.customerPhoneNumberAPI(), App.config.userPassword());
        basePage
                .openPage("/buy-add");
        requestPage
                .choosePetType("Собаки");
        basePage
                .selectValueFromDropdown("Выберите породу животного", "Австралийский келпи");
        cityComponent
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
        CityComponent cityComponent = new CityComponent();

        login
                .loginByAPI(App.config.customerPhoneNumberAPI(), App.config.userPassword());
        basePage
                .openPage("/buy-add");
        requestPage
                .chooseRequestType("Возьму бесплатно в хорошие руки")
                .choosePetType("Собаки");
        cityComponent
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

        int id = request.createRequestByAPI(13, 0, 20000, false,
                "Санкт-Петербург", "59.939084", "30.315879", 1007, 0, 6,
                false, true, "2022-04-14T08:56:15.706Z", 597);
        basePage
                .openPage("/buy/" + id)
                .clickOnButton("Удалить");
        requestPage
                .deleteRequest()
                .checkRequestDeleted();
    }
}