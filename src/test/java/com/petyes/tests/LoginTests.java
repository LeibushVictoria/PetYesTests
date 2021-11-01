package com.petyes.tests;

import com.petyes.config.App;
import com.petyes.helpers.AllureRestAssuredFilter;
import com.petyes.tests.TestBase;
import com.codeborne.selenide.Configuration;
import io.qameta.allure.AllureId;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.RestAssured;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;

@Feature("Авторизация")
public class LoginTests extends TestBase {

    @BeforeAll
    static void configureBaseUrl() {
        RestAssured.baseURI = App.config.apiUrl();
        Configuration.baseUrl = App.config.webUrl();
    }

    @Test
    @AllureId("#5696")
    @DisplayName("Успешная авторизация продавца")
    void LoginAsBreederTest() {
        step("Открыть страницу авторизации", () ->
                open("/login"));

        step("Заполнить форму авторизации", () -> {
            $(By.name("phone")).setValue(App.config.breederPhoneNumber());
            $(By.name("password")).setValue(App.config.userPassword());
            $(byText("Войти")).click();
        });

        step("Проверить успешную авторизацию", () ->
                $(".app-landing__cover").shouldHave(text("Находите хозяев для Ваших щенков и котят")));
    }

    @Test
    @AllureId("#5697")
    @DisplayName("Успешная авторизация покупателя")
    void LoginAsCustomerTest() {
        step("Открыть страницу авторизации", () ->
                open("/login"));

        step("Заполнить форму авторизации", () -> {
            $(By.name("phone")).setValue(App.config.customerPhoneNumber());
            $(By.name("password")).setValue(App.config.userPassword());
            $(byText("Войти")).click();
        });

        step("Проверить успешную авторизацию", () ->
                $(".app-landing__cover").shouldHave(text("Ваш любимец в паре кликов от Вас")));
    }

    @Test
    @AllureId("#5699")
    @DisplayName("Авторизация с неправильным паролем")
    void LoginWithWrongPasswordTest() {
        step("Открыть страницу авторизации", () ->
                open("/login"));

        step("Заполнить форму авторизации", () -> {
            $(By.name("phone")).setValue(App.config.customerPhoneNumber());
            $(By.name("password")).setValue("WrongPassword");
            $(byText("Войти")).click();
        });

        step("Проверить сообщение об ошибке", () ->
                $(".as-alert__content").shouldBe(visible));
    }

    void LoginAsCustomer() {
        step("Авторизоваться как покупатель", () -> {
            open("/login");
            $(By.name("phone")).setValue(App.config.customerPhoneNumber());
            $(By.name("password")).setValue(App.config.userPassword());
            $(byText("Войти")).click();
            $(".app-landing__cover").shouldHave(text("Ваш любимец в паре кликов от Вас"));
        });
    }

    void LoginAsBreeder() {
        step("Авторизоваться как продавец", () -> {
            open("/login");
            $(By.name("phone")).setValue(App.config.breederPhoneNumber());
            $(By.name("password")).setValue(App.config.userPassword());
            $(byText("Войти")).click();
            $(".app-landing__cover").shouldHave(text("Находите хозяев для Ваших щенков и котят"));
        });
    }

}
