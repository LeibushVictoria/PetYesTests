package com.petyes.pages;

import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class BasePage {

    @Step("Открыть страницу")
    public BasePage openPage(String url) {
        open(url);
        return this;
    }

    @Step("Нажать на Кнопку")
    public BasePage clickOnButton(String text) {
        $$(".as-button__slot").findBy(text(text)).click();
        return this;
    }

    @Step("Проверить заголовок")
    public BasePage checkHeaderH2(String header) {
        $(".h2").shouldHave(text(header));
        return this;
    }

    @Step("Проверить заголовок")
    public BasePage checkHeaderH3(String header) {
        $(".h3").shouldHave(text(header));
        return this;
    }

    @Step("Проверить заголовок")
    public BasePage checkHeaderH5(String header) {
        $(".h5").shouldHave(text(header));
        return this;
    }

    @Step("Выбрать вариант")
    public BasePage chooseRadio(String radio) {
        $$(".as-radio__text").findBy(text(radio)).click();
        return this;
    }

    @Step("Выбрать вариант")
    public BasePage chooseCheckbox(String checkbox) {
        $$(".as-checkbox__text").findBy(text(checkbox)).click();
        return this;
    }
}
