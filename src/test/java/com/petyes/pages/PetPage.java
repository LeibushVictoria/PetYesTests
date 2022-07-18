package com.petyes.pages;

import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

public class PetPage {

    @Step("Выбрать вид животного")
    public PetPage selectPetType(String placeholder, String value) {
        $$(".as-select__container").findBy(text(placeholder)).click();
        $$(".as-select__dropdown-menu .as-select__option-item").findBy(text(value)).click();
        return this;
    }

    @Step("Выбрать породу животного")
    public void selectBreed(String value) {
        $(".breed-select").click();
        $$(".breed-select .as-select__dropdown-menu .as-select__option-item").findBy(text(value)).click();
    }

    @Step("Нажать на пункт меню")
    public PetPage clickOnSideBarTab(String tabName) {
        $$(".as-sidebar-tab__link").findBy(text(tabName)).click();
        return this;
    }

    @Step("Загрузить аватар питомца")
    public PetPage uploadAvatarFile(int id, String fileName) {
        $("input[type=\"file\"]", id).uploadFromClasspath(fileName);
        $(".avatar-upload--empty").shouldNotBe(visible);
        return this;
    }

    @Step("Загрузить файл")
    public PetPage uploadFile(int id, String fileName) {
        $("input[type=\"file\"]", id).uploadFromClasspath(fileName);
        $(".file-picker__files").should(exist);
        return this;
    }

    @Step("Загрузить фото питомца")
    public PetPage uploadPhoto(int id, String fileName) {
        $("input[type=\"file\"]", id).uploadFromClasspath(fileName);
        $(".images-upload__image").should(exist);
        return this;
    }

    @Step("Ввести описание выставки")
    public void enterExpositionDescription(String textareaName, String value) {
        $("textarea[aria-placeholder=\"" + textareaName + "\"]").setValue(value);
    }

    @Step("Выбрать группу крови")
    public void enterBloodGroup(String dropdownName, String value) {
        $$(".as-form-item").findBy(text(dropdownName)).$(".as-select__container").click();
        $$(".as-select__option-item").findBy(text(value)).shouldBe(visible).click();
    }

    @Step("Проверить отображение характеристик и описания питомца")
    public PetPage checkPetInfo(String value) {
        $("#info").shouldHave(text(value));
        return this;
    }

    @Step("Проверить отображение регистрационных данных")
    public PetPage checkPetContacts(String value) {
        $("#contacts").shouldHave(text(value));
        return this;
    }

    @Step("Проверить отображение выставок и наград")
    public PetPage checkPetAdwards(String value) {
        $("#adwards").shouldHave(text(value));
        return this;
    }

    @Step("Проверить отображение рациона питания")
    public PetPage checkPetDiet(String value) {
        $("#diet").shouldHave(text(value));
        return this;
    }

    @Step("Проверить отображение вакцинации")
    public PetPage checkPetVaccination(String value) {
        $("#vaccination").shouldHave(text(value));
        return this;
    }

    @Step("Проверить отображение лечения")
    public PetPage checkPetHealth(String value) {
        $("#health").shouldHave(text(value));
        return this;
    }

    @Step("Проверить отображение мед карты")
    public PetPage checkPetMedicalCard(String value) {
        $("#medical_card").shouldHave(text(value));
        return this;
    }

    @Step("Удалить питомца")
    public void deletePet() {
        $(".as-modal__content").$$(".as-button__slot").findBy(text("Удалить")).click();
    }
}