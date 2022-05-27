package com.petyes.pages;

import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class ArticlePage {

    @Step("Ввести текст статьи")
    public ArticlePage enterText(String text) {
        $(".is-editor-empty").setValue(text);
        return this;
    }

    @Step("Выбрать категорию")
    public ArticlePage selectCategory(String value) {
        $(".as-select__container").click();
        $$(".as-select__option-item").findBy(text(value)).click();
        return this;
    }

    @Step("Проверить создание статьи")
    public ArticlePage checkCreateArticles(String value) {
        $(".as-modal__content").shouldHave(text(value));
        return this;
    }

    @Step("Проверить результат")
    public ArticlePage checkResult(String article) {
        $(".knowledge-articles").shouldHave(text(article));
        return this;
    }
}
