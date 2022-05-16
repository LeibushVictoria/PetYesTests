package com.petyes.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

public class AddPetPage {

    @Step("Выбрать породу животного")
    public AddPetPage choosePetBreed(String placeholder, String value) {
        $$(".as-select__container").findBy(text(placeholder)).click();
        $$(".as-select__option-item").findBy(text(value)).shouldBe(visible, Duration.ofSeconds(10)).click();
        return this;
    }

    @Step("Ввести значение в поле")
    public AddPetPage typeValueInInput(String fieldName, String value) {
        $(By.name(fieldName)).setValue(value);
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

    @Step("Проверить создание питомца")
    public void checkPetCreated(String name) {
        $(".pet-profile__info-card").shouldHave(text(name));
    }
}