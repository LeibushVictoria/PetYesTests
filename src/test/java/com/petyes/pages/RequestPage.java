package com.petyes.pages;

import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class RequestPage {

    @Step("Выбрать вид животного")
    public RequestPage selectPetType(String placeholder, String value) {
        $$(".as-select__container").findBy(text(placeholder)).click();
        $$(".as-select__dropdown-menu .as-select__option-item").findBy(text(value)).click();
        return this;
    }

    @Step("Выбрать окрас")
    public RequestPage chooseColor(String color) {
        $("input[data-vv-name=\"colors\"]").setValue(color);
        $$(".as-select__option-item").findBy(text(color)).shouldBe(visible, Duration.ofSeconds(10)).click();
        return this;
    }

    @Step("Ввести комментарий")
    public RequestPage enterComment(String value) {
        $(".as-textarea__field").setValue(value);
        return this;
    }

    @Step("Выбрать дополнительный параметр")
    public RequestPage chooseOption(String optionName, String radio) {
        $$(".as-form-item").findBy(text(optionName)).$$(".as-radio__text").findBy(text(radio)).click();
        return this;
    }

    @Step("Проверить отображение цены")
    public RequestPage checkPrice(String price) {
        $(".buy-request__content-top").shouldHave(text(price));
        return this;
    }

    @Step("Проверить отображение тэга")
    public RequestPage checkTag(String tag) {
        $(".buy-request__tags").shouldHave(text(tag));
        return this;
    }

    @Step("Проверить отображение значения")
    public RequestPage checkResults(int index, String fieldName, String value) {
        $(".buy-request__params-item", index).shouldHave(text(fieldName));
        $(".buy-request__params-item", index).shouldHave(text(value));
        return this;
    }

    @Step("Проверить отображение комментария")
    public RequestPage checkComment(String comment) {
        $(".buy-request__description").shouldHave(text(comment));
        return this;
    }

    @Step("Удалить запрос")
    public RequestPage deleteRequest() {
        $(".as-modal__content").$$(".as-button__slot").findBy(text("Удалить")).click();
        return this;
    }

    @Step("Ввести причину завершения")
    public RequestPage enterReason(String value) {
        $(".as-textarea__field").setValue(value);
        return this;
    }

    @Step("Проверить отображение объявления")
    public RequestPage checkSaleOffer(int index, int id) {
        $(".as-card__body", index).$("a[href=\"/sale/"+id+"\"]").should(exist);
        return this;
    }

    @Step("Выбрать питомца для отклика")
    public RequestPage choosePetToResponse(int pet_id) {
        $("input[type=\"checkbox\"][value =\"" + pet_id + "\"]+span").click();
        return this;
    }
}