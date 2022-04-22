package com.petyes.pages;

import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class AuctionPage {

    @Step("Проверить результат")
    public AuctionPage checkResult(int id) {
        $(".a[href*=\""+id+"\"]").exists();
        return this;
    }
}
