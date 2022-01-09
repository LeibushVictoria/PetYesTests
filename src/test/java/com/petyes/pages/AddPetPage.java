package com.petyes.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

public class AddPetPage {

    @Step("Открыть страницу авторизации")
    public AddPetPage openAddPetPage() {
        open("/pet/new");
        return this;
    }

    @Step("Выбрать вид животного")
    public AddPetPage choosePetType(String type) {
        $$(".as-select__container").findBy(text("Выберите вид животного")).click();
        $$(".as-select__option-item").findBy(text(type)).click();
        return this;
    }

    @Step("Выбрать породу животного")
    public AddPetPage choosePetBreed(int id) {
        $$(".as-select__container").findBy(text("Выберите породу животного")).click();
        $(".as-select__option-item", id).click();
        return this;
    }

    @Step("Указать имя животного")
    public void typePetName(String name) {
        $(By.name("nickname")).setValue(name);
    }

    @Step("Нажать кнопку Создать")
    public AddPetPage clickSubmitButton() {
        $$(".as-button__slot").findBy(text("Создать")).click();
        return this;
    }

    @Step("Проверить создание питомца")
    public void checkPetCreated(String name) {
        $(".pet-profile__info-card").shouldHave(text(name));
    }
}