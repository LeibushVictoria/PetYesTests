package com.petyes.pages;

import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class MyPetsPage {

    @Step("Нажать на питомца")
    public void clickOnPet(String nickname) {
        $$(".mypets__nickname").findBy(text(nickname)).click();
    }

    @Step("Нажать на иконку родословной")
    public MyPetsPage clickOnGenTree(int id) {
        $("a[href=\"/pet/gen-tree?id="+id+"\"]").click();
        return this;
    }

    @Step("Проверить отображение родословной")
    public void checkGenTree(String nickname) {
        $(".gen-tree-leaf__nickname").shouldHave(text(nickname));
    }

    @Step("Нажать на иконку редактирования")
    public void clickOnEdit(int id) {
        $("a[href=\"/pet/settings/info?id="+id+"\"]").click();
    }

    @Step("Нажать на иконку отдать даром")
    public MyPetsPage clickOnFreeSale(int id) {
        $("a[href=\"/sale/add/"+id+"?is_free=1\"]").click();
        return this;
    }

    @Step("Проверить отображение страницы продажи")
    public void checkSale(String text) {
        $(".as-radio--active").shouldHave(text(text));
    }

    @Step("Нажать на иконку продажи")
    public MyPetsPage clickOnSale(int id) {
        $("a[href=\"/sale/add/"+id+"\"]").click();
        return this;
    }
}
