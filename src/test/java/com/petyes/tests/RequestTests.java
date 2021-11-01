package com.petyes.tests;

import com.codeborne.selenide.Configuration;
import com.petyes.config.App;
import io.qameta.allure.AllureId;
import io.qameta.allure.Feature;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;

@Feature("Запрос")
public class RequestTests extends TestBase {

    @BeforeAll
    static void configureBaseUrl() {
        RestAssured.baseURI = App.config.apiUrl();
        Configuration.baseUrl = App.config.webUrl();
    }

    @Test
    @AllureId("#5701")
    @DisplayName("Создание запроса на питомца")
    void CreateRequestTest() {

        step("Открыть страницу создания запроса", () -> {
            LoginTests loginTests = new LoginTests();
            loginTests.LoginAsCustomer();
            open("/buy-add");
        });

        step("Заполнить форму создания запроса", () -> {
            $$(".as-select__container").findBy(text("Выберите вид животного")).click();
            $$(".as-select__option-item").findBy(text("Собаки")).click();
            $$(".as-select__container").findBy(text("Выберите породу животного")).click();
            $$(".as-select__option-item").findBy(text("Австралийский келпи")).click();
            $$(".as-button__slot").findBy(text("Продолжить")).click();
            $$(".as-button__slot").findBy(text("Продолжить")).click();
            $$(".as-button__slot").findBy(text("Опубликовать")).click();
            $$(".as-button__slot").findBy(text("Перейти к запросу")).click();
        });

        step("Проверить создание запроса", () ->
                $(".h3").shouldHave(text("Австралийский келпи")));
    }

    @Test
    @AllureId("#5702")
    @DisplayName("Удаление запроса на питомца")
    void DeleteRequestTest() {
        LoginTests loginTests = new LoginTests();
        loginTests.LoginAsCustomer();

        step("Открыть запрос", () -> {
            open("/user/46#requests");
            $(".buy-user-preview__container", 0).click();
        });

        step("Удалить запрос", () -> {
            $$(".as-button__slot").findBy(text("Удалить")).click();
            $(".as-modal__content").$$(".as-button__slot").findBy(text("Удалить")).click();
        });

        step("Проверить удаление запроса", () ->
                $(".iziToast-color-green").shouldBe(visible));
    }
}
