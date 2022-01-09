package com.petyes.pages;

import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$$;

public class RequestPage {

    @Step("Открыть страницу создания запроса")
    public RequestPage openCreateRequestPage() {
        open("/buy-add");
        return this;
    }

    @Step("Выбрать вид животного")
    public RequestPage choosePetType(String type) {
        $$(".as-select__container").findBy(text("Выберите вид животного")).click();
        $$(".as-select__option-item").findBy(text(type)).click();
        return this;
    }

    @Step("Выбрать породу животного")
    public RequestPage choosePetBreed(String breed) {
        $$(".as-select__container").findBy(text("Выберите породу животного")).click();
        $$(".as-select__option-item").findBy(text(breed)).click();
        return this;
    }

    @Step("Нажать кнопку Продолжить")
    public RequestPage clickContinueButton() {
        $$(".as-button__slot").findBy(text("Продолжить")).click();
        return this;
    }

    @Step("Нажать кнопку Опубликовать")
    public RequestPage clickPublishButton() {
        $$(".as-button__slot").findBy(text("Опубликовать")).click();
        return this;
    }

    @Step("Открыть созданный запрос")
    public RequestPage openCreatedRequest() {
        $$(".as-button__slot").findBy(text("Перейти к запросу")).click();
        return this;
    }

    @Step("Проверить создание запроса")
    public void checkRequestCreated(String breed) {
        $(".h3").shouldHave(text(breed));
    }

    @Step("Открыть страницу запроса")
    public RequestPage openRequestPage() {
        open("/user/46#requests");
        $(".buy-user-preview__container", 0).click();
        return this;
    }

    @Step("Удалить запрос")
    public RequestPage deleteRequest() {
        $$(".as-button__slot").findBy(text("Удалить")).click();
        $(".as-modal__content").$$(".as-button__slot").findBy(text("Удалить")).click();
        return this;
    }

    @Step("Проверить удаление запроса")
    public void checkRequestDeleted() {
        $(".iziToast-color-green").shouldBe(visible);
    }
}