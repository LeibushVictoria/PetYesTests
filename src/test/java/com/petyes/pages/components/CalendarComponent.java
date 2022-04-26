package com.petyes.pages.components;

import io.qameta.allure.Step;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class CalendarComponent {

    public String getTodayDate() {
        Date dateToday = new Date();
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String today = formater.format(dateToday);
        return today;
    }
    public String getTomorrowDate() {
        Date today = new Date();
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        long msDate = today.getTime();
        long oneDay = 86400000L;
        Date dateTomorrow = new Date(msDate+oneDay);
        String tomorrow = formater.format(dateTomorrow);
        return tomorrow;
    }

    public String getDayAfterTomorrowDate() {
        Date today = new Date();
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        long msDate = today.getTime();
        long oneDay = 86400000L;
        Date dateDayAfterTomorrow = new Date(msDate+oneDay+oneDay);
        String dayAfterTomorrow = formater.format(dateDayAfterTomorrow);
        return dayAfterTomorrow;
    }

    @Step("Выбрать в календаре дату в предыдущем месяце")
    public void setDate(String day) {
            $(".as-datepicker-input").click();
            $(".as-datepicker__navigate-btn", 1).click();
            $(".as-datepicker__calendar").$(byText(day)).click();
    }
}