package com.petyes.tests;

import com.codeborne.selenide.Configuration;
import com.petyes.config.App;
import com.petyes.tests.components.CalendarComponent;
import io.qameta.allure.AllureId;
import io.qameta.allure.Feature;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;

@Feature("Питомец")
public class AddPetTests extends TestBase {

    @BeforeAll
    static void configureBaseUrl() {
        RestAssured.baseURI = App.config.apiUrl();
        Configuration.baseUrl = App.config.webUrl();
    }

    @Test
    @AllureId("#5704")
    @DisplayName("Создание питомца")
    void AddPetTest() {
        LoginTests loginTests = new LoginTests();
        CalendarComponent calendarComponent = new CalendarComponent();
        loginTests.LoginAsBreeder();

        step("Открыть страницу добавления питомца", () ->
                open("/pet/new"));

        step("Заполнить форму добавления питомца", () -> {
            $$(".as-select__container").findBy(text("Выберите вид животного")).click();
            $$(".as-select__option-item").findBy(text("Кошки")).click();
            $$(".as-select__container").findBy(text("Выберите породу животного")).click();
            $(".as-select__option-item", 2).click();
            $(By.name("nickname")).setValue("autoTest");
            calendarComponent.setDate();
            $$(".as-button__slot").findBy(text("Создать")).click();
        });

        step("Проверить создание питомца", () ->
                $(".pet-profile__info-card").shouldHave(text("autoTest")));
    }
}
