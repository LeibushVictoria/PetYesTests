package com.petyes.pages;

import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class KnowledgePage {

    @Step("Выбрать вид животного - кошку")
    public KnowledgePage choosePetType() {
        $(".specialization-choose__btn--cat").click();
        return this;
    }

    @Step("Выбрать вариант")
    public KnowledgePage chooseBubble(String bubble) {
        $$(".breed-selector-bubbles__circle").findBy(text(bubble)).click();
        return this;
    }

    @Step("Проверить отображение пород")
    public KnowledgePage checkBreedsDisplay(String breed) {
        $$(".breed-choose-preview").findBy(text(breed)).click();
        return this;
    }

    @Step("Добавить в сравнение")
    public KnowledgePage addToComparison(String breed) {
        $$(".breed-preview").findBy(text(breed)).$$(".as-button__slot").findBy(text("Сравнить")).click();
        return this;
    }

    @Step("Открыть плашку сравнения")
    public KnowledgePage openComparison() {
        $(".compare-breeds-sheet__header").click();
        return this;
    }

    @Step("Раскрыть фильтр")
    public KnowledgePage openFilter(String filter) {
        $$(".as-collapse__heading").findBy(text(filter)).click();
        return this;
    }

    @Step("Проверить счетчик")
    public KnowledgePage checkCounter(String text) {
        $(".text-loading").shouldHave(text(text));
        return this;
    }

    @Step("Проверить результат")
    public KnowledgePage checkResult(String breed) {
        $(".knowledge-breeds__content").shouldHave(text(breed));
        return this;
    }

    @Step("Нажать на Запросить")
    public KnowledgePage clickOnRequestButton(String breed) {
        $$(".breed-preview").findBy(text(breed)).$$(".as-button__slot").findBy(text("Запросить")).click();
        return this;
    }

    @Step("Открыть породу")
    public KnowledgePage openBreed(String breed) {
        $$(".breed-preview__link").findBy(text(breed)).click();
        return this;
    }
}
