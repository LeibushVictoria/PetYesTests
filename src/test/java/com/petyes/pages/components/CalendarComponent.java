package com.petyes.pages.components;

import io.qameta.allure.Step;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class CalendarComponent {

    @Step("Выбрать дату в календаре")
    public void setDate(String day) {
            $(".as-datepicker-input").click();
            $(".as-datepicker__navigate-btn", 1).click();
            $(".as-datepicker__calendar").$(byText(day)).click();
    }
}