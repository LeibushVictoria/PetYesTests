package com.petyes.pages;

import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class SalePetPage {

    @Step("Нажать на Кнопку")
    public SalePetPage clickOnSaleButton(String text) {
        $("button[type=\"submit\"] .as-button__slot").shouldHave(text(text)).click();
        return this;
    }

    @Step("Ввести причину завершения")
    public SalePetPage enterReason(String value) {
        $(".as-textarea__field").setValue(value);
        return this;
    }
}
