package com.petyes.pages.components;

import io.qameta.allure.Step;

import java.util.Date;

import static com.codeborne.selenide.Selenide.$;

public class CalendarComponent {

    public Date getTodayDate() {
        Date today = new Date();
        return today;
    }

    public Date getOtherDate(int daysOffset) {
        Date today = new Date();
        long msDate = today.getTime();
        long oneDay = 86400000L;
        long days = oneDay * daysOffset;
        Date pastDate = new Date(msDate+days);
        return pastDate;
    }

    @Step("Ввести дату")
    public CalendarComponent enterDate(String name, String date) {
        $("input[data-vv-name=\"" + name + "\"]").setValue(date);
        return this;
    }

    @Step("Ввести дату начала питания")
    public CalendarComponent enterFoodDate(String name, String date) {
        $("input[name=\"" + name + "\"]").setValue(date);
        return this;
    }
}