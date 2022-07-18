package com.petyes.pages;

import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class BreedersPage {

    @Step("Открыть страницу заводчика")
    public void openBreederPage(String breeder) {
        $$(".breeder-preview__link").findBy(text(breeder)).click();
    }

    @Step("Открыть отзывы заводчика")
    public BreedersPage openRewiewPopup(String breeder) {
        $$(".breeder-preview__content").findBy(text(breeder)).$(".breeder-preview__review-add").click();
        return this;
    }

    @Step("Нажать на оценку")
    public BreedersPage clickRatingButton(int star) {
        $(".as-rating__star", star).click();
        return this;
    }

    @Step("Ввести комментарий")
    public void setComment(String comment) {
        $(".as-textarea__field").setValue(comment);
    }
}
