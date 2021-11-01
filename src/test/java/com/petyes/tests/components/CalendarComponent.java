package com.petyes.tests.components;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static io.qameta.allure.Allure.step;

public class CalendarComponent {

    public void setDate() {
        step("Выбрать в календаре 10 число предыдущего месяца", () -> {
            $(".as-datepicker-input").click();
            $(".as-datepicker__navigate-btn", 1).click();
            $(".as-datepicker__calendar").$(byText("10")).click();
        });
    }
}


