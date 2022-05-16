package com.petyes.pages.components;

import io.qameta.allure.Step;

import java.util.Date;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class CalendarComponent {

    public Date getTodayDate() {
        Date today = new Date();
        return today;
    }

    public Date getPastDate(int subtractDays) {
        Date today = new Date();
        long msDate = today.getTime();
        long oneDay = 86400000L;
        long days = oneDay * subtractDays;
        Date pastDate = new Date(msDate-days);
        return pastDate;
    }

    public Date getFutureDate(int addDays) {
        Date today = new Date();
        long msDate = today.getTime();
        long oneDay = 86400000L;
        long days = oneDay * addDays;
        Date pastDate = new Date(msDate+days);
        return pastDate;
    }

    @Step("Выбрать в календаре дату в предыдущем месяце")
    public void chooseDateInCalendar(String day) {
            $(".as-datepicker-input").click();
            $(".as-datepicker__navigate-btn", 1).shouldBe(visible).click();
            $(".as-datepicker__calendar").$(byText(day)).shouldBe(visible).click();
    }

    @Step("Ввести дату")
    public void enterDate(String name, String date) {
        $("input[data-vv-name=" + name + "]").setValue(date);
    }
}