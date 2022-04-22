package com.petyes.pages.components;

import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CityComponent {

    @Step("Выбрать город")
    public void chooseCity(String city) {
        $("input[data-vv-name=\"city\"]").setValue(city);
        $$(".as-select__option-item").findBy(text(city)).shouldBe(visible, Duration.ofSeconds(10)).click();
    }
}
