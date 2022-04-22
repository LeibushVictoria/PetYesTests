package com.petyes.pages;

import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$$;

public class RequestPage {

    @Step("Выбрать вид животного")
    public RequestPage choosePetType(String type) {
        $$(".as-select__container").findBy(text("Выберите вид животного")).click();
        $$(".as-select__option-item").findBy(text(type)).click();
        return this;
    }

    @Step("Выбрать тип запроса")
    public RequestPage chooseRequestType(String type) {
        $$(".as-radio__text").findBy(text(type)).click();
        return this;
    }

    @Step("Удалить запрос")
    public RequestPage deleteRequest() {
        $(".as-modal__content").$$(".as-button__slot").findBy(text("Удалить")).click();
        return this;
    }

    @Step("Проверить удаление запроса")
    public void checkRequestDeleted() {
        $(".iziToast-color-green").shouldBe(visible);
    }
}