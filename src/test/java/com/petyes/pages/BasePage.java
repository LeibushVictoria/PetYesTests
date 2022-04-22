package com.petyes.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class BasePage {

    @Step("Открыть страницу")
    public BasePage openPage(String url) {
        open(url);
        return this;
    }

    @Step("Нажать на Кнопку")
    public BasePage clickOnButton(String text) {
        $$(".as-button__slot").findBy(text(text)).click();
        return this;
    }

    @Step("Проверить заголовок")
    public BasePage checkHeaderH2(String header) {
        $(".h2").shouldHave(text(header));
        return this;
    }

    @Step("Проверить заголовок")
    public BasePage checkHeaderH3(String header) {
        $(".h3").shouldHave(text(header));
        return this;
    }

    @Step("Проверить заголовок")
    public BasePage checkHeaderH5(String header) {
        $(".h5").shouldHave(text(header));
        return this;
    }

    @Step("Выбрать вариант")
    public BasePage chooseRadio(String radio) {
        $$(".as-radio__text").findBy(text(radio)).click();
        return this;
    }

    @Step("Выбрать вариант")
    public BasePage chooseCheckbox(String checkbox) {
        $$(".as-checkbox__text").findBy(text(checkbox)).click();
        return this;
    }

    @Step("Раскрыть фильтр")
    public BasePage openFilter(String filter) {
        $$(".as-collapse__heading").findBy(text(filter)).click();
        return this;
    }

    @Step("Проверить отображение блока")
    public BasePage checkBlockDisplay(String text) {
        $$(".as-card__body").findBy(text(text)).click();
        return this;
    }

    @Step("Ввести значение в поле")
    public BasePage enterValue(int index, String value) {
        $(".as-input__field", index).setValue(value);
        return this;
    }

    @Step("Ввести значение в поле")
    public BasePage enterValueByKeys(int index, String value) {
        $(".as-input__field", index).sendKeys(Keys.CONTROL + "A");
        $(".as-input__field", index).sendKeys(Keys.BACK_SPACE);
        $(".as-input__field", index).sendKeys(value);
        return this;
    }

    @Step("Выбрать значение в выпадающем списке")
    public BasePage selectValueFromDropdown(String placeholder, String value) {
        $$(".as-select__container").findBy(text(placeholder)).click();
        $$(".as-select__option-item").findBy(text(value)).click();
        return this;
    }
}
