package com.petyes.pages;

import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class MyPetsPage {

    @Step("Нажать на питомца")
    public MyPetsPage clickOnPet(String nickname) {
        $$(".mypets__nickname").findBy(text(nickname)).click();
        return this;
    }

    @Step("Нажать на иконку родословной")
    public MyPetsPage clickOnGenTree(int id) {
        $("a[href=\"/pet/gen-tree?id="+id+"\"]").click();
        return this;
    }

    @Step("Проверить отображение родословной")
    public MyPetsPage checkGenTree(String nickname) {
        $(".gen-tree-leaf__nickname").shouldHave(text(nickname));
        return this;
    }

    @Step("Нажать на иконку редактирования")
    public MyPetsPage clickOnEdit(int id) {
        $("a[href=\"/pet/settings/info?id="+id+"\"]").click();
        return this;
    }

    @Step("Нажать на иконку отдать даром")
    public MyPetsPage clickOnFreeSale(int id) {
        $("a[href=\"/sale/add/"+id+"?is_free=1\"]").click();
        return this;
    }

    @Step("Проверить отображение страницы продажи")
    public MyPetsPage checkSale(String text) {
        $(".as-radio--active").shouldHave(text(text));
        return this;
    }

    @Step("Нажать на иконку продажи")
    public MyPetsPage clickOnSale(int id) {
        $("a[href=\"/sale/add/"+id+"\"]").click();
        return this;
    }
}
