package com.petyes.pages;

import com.petyes.models.LoginData;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.*;
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
    public BasePage checkHeader(int headerNumber, String headerName) {
        $(".h" + headerNumber).shouldHave(text(headerName));
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

    @Step("Ввести значение в поле")
    public BasePage enterValueInInput(String fieldName, String value) {
        $(By.name(fieldName)).setValue(value);
        return this;
    }

    @Step("Ввести значение в поле")
    public BasePage enterValueByKeys(int index, String value) {
        $(".as-input__field", index).sendKeys(Keys.CONTROL + "A");
        $(".as-input__field", index).sendKeys(Keys.BACK_SPACE);
        $(".as-input__field", index).sendKeys(value);
        return this;
    }

    @Step("Ввести значение в поле в фильтрах")
    public BasePage enterValueInInputInFilter(int id, String value) {
        $(".as-input__field", id).setValue(value);
        return this;
    }

    @Step("Ввести значение в числовое поле")
    public BasePage enterNumberInInput(String inputName, String value) {
        $("input[data-vv-name=\"" + inputName + "\"]").sendKeys(Keys.CONTROL + "A");
        $("input[data-vv-name=\"" + inputName + "\"]").sendKeys(Keys.BACK_SPACE);
        $("input[data-vv-name=\"" + inputName + "\"]").sendKeys(value);
        return this;
    }

    @Step("Выбрать значение в выпадающем списке")
    public BasePage enterValueInDropdown(String selectName, String value) {
        $("input[data-vv-name=\"" + selectName +"\"]").setValue(value);
        $$(".as-select__option-item").findBy(text(value)).click();
        return this;
    }

    @Step("Выбрать значение в выпадающем списке")
    public BasePage selectValueInDropdown(String selectName, String value) {
        $("div[data-vv-name=\"" + selectName +"\"]").click();
        $$(".as-select__option-item").findBy(text(value)).click();
        return this;
    }

    @Step("Выбрать значение в выпадающем списке в фильтрах")
    public BasePage selectValueInDropdownInFilter(String placeholder, String value) {
        $$(".as-select__container").findBy(text(placeholder)).click();
        $$(".as-select__option-item").findBy(text(value)).shouldBe(visible).click();
        return this;
    }

    @Step("Ввести текст в textarea")
    public BasePage enterValueInTextarea(String textareaName, String value) {
        $("textarea[class~=\"as-textarea__field\"][name=\"" + textareaName +"\"]").setValue(value);
        return this;
    }

    @Step("Очистить поле textarea")
    public BasePage clearTextarea() {
        $(".as-textarea__field").clear();
        return this;
    }

    @Step("Раскрыть фильтр")
    public BasePage openFilter(String filter) {
        $$(".as-collapse__heading").findBy(text(filter)).click();
        return this;
    }

    @Step("Загрузить файл с выбором области")
    public BasePage uploadFile(int id, String fileName) {
        $("input[type=\"file\"", id).uploadFromClasspath(fileName);
        $$(".as-button__slot").findBy(text("Сохранить и продолжить")).click();
        $(".images-upload__image").exists();
        return this;
    }

    @Step("Проверить отображение текста в блоке")
    public BasePage checkBlockDisplay(String text) {
        $$(".as-card__body").findBy(text(text)).shouldBe(visible);
        return this;
    }

    @Step("Проверить результат поиска")
    public BasePage checkResult(String value) {
        $(".search-page__results").shouldHave(text(value));
        return this;
    }

    @Step("Проверить отображение зеленой всплывашки")
    public BasePage checkGreenMessage() {
        $(".iziToast-color-green").shouldBe(visible);
        return this;
    }

    @Step("Проверить наличие ссылки по id")
    public BasePage checkLink(int id) {
        $("a[href*=\""+id+"\"]").should(exist);
        return this;
    }
}