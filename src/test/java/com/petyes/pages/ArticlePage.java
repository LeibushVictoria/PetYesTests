package com.petyes.pages;

import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class ArticlePage {

    @Step("Загрузить файл")
    public void uploadFile(String fileName) {
        $("input[type=\"file\"", 0).uploadFromClasspath(fileName);
        $(".cropper-view-box").shouldBe(visible);
        $$(".as-button__slot").findBy(text("Сохранить и продолжить")).click();
        $(".image-upload__image").shouldBe(visible);
    }

    @Step("Ввести текст статьи")
    public ArticlePage enterText(String text) {
        $(".is-editor-empty").setValue(text);
        return this;
    }

    @Step("Выбрать категорию")
    public void selectCategory(String value) {
        $(".as-select__container").click();
        $$(".as-select__option-item").findBy(text(value)).click();
    }

    @Step("Проверить создание статьи")
    public ArticlePage checkCreateArticles(String value) {
        $(".as-modal__content").shouldHave(text(value));
        return this;
    }

    @Step("Проверить результат")
    public void checkResult(String article) {
        $(".knowledge-articles").shouldHave(text(article));
    }
}
