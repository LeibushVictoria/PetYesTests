package com.petyes.pages;

import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

    @Step("Проверить успешную авторизацию")
    public void checkLogin(String text) {
        $(".app-landing__cover").shouldHave(text(text));
    }

    @Step("Проверить сообщение об ошибке")
    public void checkErrorMessage() {
        $(".as-alert__content").shouldBe(visible);
    }
}