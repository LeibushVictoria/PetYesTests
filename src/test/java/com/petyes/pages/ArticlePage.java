package com.petyes.pages;

import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class ArticlePage {

    @Step("Ввести аннотацию")
    public ArticlePage enterValue(String text) {
        $(".as-textarea__field").setValue(text);
        return this;
    }

    @Step("Ввести текст статьи")
    public ArticlePage enterText(String text) {
        $(".is-editor-empty").setValue(text);
        return this;
    }

    @Step("Загрузить обложку")
    public ArticlePage uploadPicture(String text, String fileName) {
        $$(".as-button__slot").findBy(text(text)).uploadFromClasspath(fileName);
        return this;
    }

    @Step("Выбрать категорию")
    public ArticlePage selectFirstValueFromDropdown() {
        $(".as-select__container").click();
        $(".as-select__option-item", 0).click();
        return this;
    }
}
