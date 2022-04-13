package com.petyes.pages;

import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class BreedersPage {

    @Step("Открыть страницу Заводчики")
    public BreedersPage openBreedersPage() {
        open("/breeders");
        return this;
    }

    @Step("Нажать кнопку Найти питомца")
    public BreedersPage clickCreateRequestButton() {
        $$(".as-button__slot").findBy(text("Найти питомца")).click();
        return this;
    }

    @Step("Проверить открытие создания запроса")
    public BreedersPage checkCreateRequestPageOpened() {
        $(".h2").shouldHave(text("Создание запроса на подбор животного"));
        return this;
    }

    @Step("Открыть страницу первого заводчика")
    public BreedersPage openFirstBreederPage() {
        $(".breeder-preview__content", 0).click();
        return this;
    }

    @Step("Проверить открытие страницы первого заводчика")
    public BreedersPage checkFirstBreederPageOpened() {
        $(".user-profile-cover__footer").shouldHave(text("Начать чат"));
        return this;
    }

    @Step("Нажать на Добавить отзыв у первого заводчика")
    public BreedersPage clickCreateRewiewButton() {
        $$(".as-button__slot").findBy(text("Добавить отзыв")).click();
        return this;
    }

    @Step("Нажать на оценку")
    public BreedersPage clickRatingButton() {
        $(".as-rating__star", 3).click();
        return this;
    }

    @Step("Ввести комментарий")
    public BreedersPage setComment(String comment) {
        $(".as-textarea__field").setValue(comment);
        return this;
    }

    @Step("Нажать на Отправить")
    public BreedersPage clickOnSubmit() {
        $$(".as-button__slot").findBy(text("Отправить")).click();
        return this;
    }

    @Step("Проверить отправку отзыва")
    public BreedersPage checkRewiewCreated() {
        $(".alert-modal__title").shouldHave(text("Спасибо"));
        return this;
    }
}
