package com.petyes.pages;

import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class KnowledgePage {

    @Step("Выбрать вид животного - кошку")
    public void choosePetType() {
        $(".specialization-choose__btn--cat").click();
    }

    @Step("Выбрать вариант")
    public void chooseBubble(String bubble) {
        $$(".breed-selector-bubbles__circle").findBy(text(bubble)).click();
    }

    @Step("Проверить отображение пород")
    public void checkBreedsDisplay(String breed) {
        $$(".breed-choose-preview").findBy(text(breed)).click();
    }

    @Step("Добавить в сравнение")
    public KnowledgePage addToComparison(String breed) {
        $$(".breed-preview").findBy(text(breed)).$$(".as-button__slot").findBy(text("Сравнить")).click();
        return this;
    }

    @Step("Открыть плашку сравнения")
    public void openComparison() {
        $(".compare-breeds-sheet__header").click();
    }

    @Step("Проверить счетчик")
    public void checkCounter(String text) {
        $(".text-loading").shouldHave(text(text));
    }

    @Step("Проверить результат")
    public KnowledgePage checkResult(String breed) {
        $(".knowledge-breeds__content").shouldHave(text(breed));
        return this;
    }

    @Step("Нажать на Запросить")
    public void clickOnRequestButton(String breed) {
        $$(".breed-preview").findBy(text(breed)).$$(".as-button__slot").findBy(text("Запросить")).click();
    }

    @Step("Открыть породу")
    public void openBreed(String breed) {
        $$(".breed-preview__link").findBy(text(breed)).click();
    }

    @Step("Проверить отображение пород в списке сравнения")
    public KnowledgePage checkComparison(String breed) {
        $$(".compare-breed-preview__title").findBy(text(breed)).shouldBe(visible);
        return this;
    }
}
