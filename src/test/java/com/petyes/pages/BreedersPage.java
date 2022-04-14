package com.petyes.pages;

import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class BreedersPage {

    @Step("Открыть страницу заводчика")
    public BreedersPage openBreederPage(String breeder) {
        $$(".breeder-preview__link").findBy(text(breeder)).click();
        return this;
    }

    @Step("Открыть отзывы заводчика")
    public BreedersPage openRewiewPopup(String breeder) {
        $$(".breeder-preview__content").findBy(text(breeder)).$(".breeder-preview__review-count").click();
        return this;
    }

    @Step("Нажать на оценку")
    public BreedersPage clickRatingButton(int star) {
        $(".as-rating__star", star).click();
        return this;
    }

    @Step("Ввести комментарий")
    public BreedersPage setComment(String comment) {
        $(".as-textarea__field").setValue(comment);
        return this;
    }

    @Step("Проверить отправку отзыва")
    public BreedersPage checkRewiewCreated() {
        $(".as-modal__content").shouldHave(text("Хорошо"));
        return this;
    }
}
