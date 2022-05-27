package com.petyes.pages;

import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

public class AddPetPage {

    @Step("Выбрать вид животного")
    public AddPetPage selectPetType(String placeholder, String value) {
        $$(".as-select__container").findBy(text(placeholder)).click();
        $$(".as-select__dropdown-menu .as-select__option-item").findBy(text(value)).click();
        return this;
    }

    @Step("Выбрать породу животного")
    public AddPetPage selectBreed(String value) {
        $(".breed-select").click();
        $$(".breed-select .as-select__dropdown-menu .as-select__option-item").findBy(text(value)).click();
        return this;
    }

    @Step("Нажать на пункт меню")
    public AddPetPage clickOnSideBarTab(String tabName) {
        $$(".as-sidebar-tab__link").findBy(text(tabName)).click();
        return this;
    }

    @Step("Загрузить аватар питомца")
    public AddPetPage uploadAvatarFile(int id, String fileName) {
        $("input[type=\"file\"", id).uploadFromClasspath(fileName);
        $(".avatar-upload--empty").shouldNotBe(visible);
        return this;
    }

    @Step("Загрузить файл")
    public AddPetPage uploadFile(int id, String fileName) {
        $("input[type=\"file\"", id).uploadFromClasspath(fileName);
        $(".file-picker__files").exists();
        return this;
    }

    @Step("Загрузить фото питомца")
    public AddPetPage uploadPhoto(int id, String fileName) {
        $("input[type=\"file\"", id).uploadFromClasspath(fileName);
        $(".images-upload__image").exists();
        return this;
    }

    @Step("Ввести описание выставки")
    public AddPetPage enterExpositionDescription(String textareaName, String value) {
        $$(".as-form-item").findBy(text(textareaName)).$(".as-textarea__field").setValue(value);
        return this;
    }

    @Step("Ввести диагноз")
    public AddPetPage enterDiagnosis(String value) {
        $("input[data-vv-name=\"cure[0].diagnosis\"").setValue(value);
        return this;
    }

    @Step("Ввести назначенное лечение")
    public AddPetPage enterCure(String value) {
        $("textarea[class~=\"as-textarea__field\"][data-vv-name=\"cure[0].prescription\"]").setValue(value);
        return this;
    }

    @Step("Выбрать группу крови")
    public AddPetPage enterBloodGroup(String dropdownName, String value) {
        $$(".as-form-item").findBy(text(dropdownName)).$(".as-select__container").click();
        $$(".as-select__option-item").findBy(text(value)).shouldBe(visible).click();
        return this;
    }

    @Step("Проверить отображение характеристик и описания питомца")
    public AddPetPage checkPetInfo(String value) {
        $("#info").shouldHave(text(value));
        return this;
    }

    @Step("Проверить отображение регистрационных данных")
    public AddPetPage checkPetContacts(String value) {
        $("#contacts").shouldHave(text(value));
        return this;
    }

    @Step("Проверить отображение выставок и наград")
    public AddPetPage checkPetAdwards(String value) {
        $("#adwards").shouldHave(text(value));
        return this;
    }

    @Step("Проверить отображение рациона питания")
    public AddPetPage checkPetDiet(String value) {
        $("#diet").shouldHave(text(value));
        return this;
    }

    @Step("Проверить отображение вакцинации")
    public AddPetPage checkPetVaccination(String value) {
        $("#vaccination").shouldHave(text(value));
        return this;
    }

    @Step("Проверить отображение лечения")
    public AddPetPage checkPetHealth(String value) {
        $("#health").shouldHave(text(value));
        return this;
    }

    @Step("Проверить отображение мед карты")
    public AddPetPage checkPetMedicalCard(String value) {
        $("#medical_card").shouldHave(text(value));
        return this;
    }
}