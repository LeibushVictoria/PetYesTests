package com.petyes.tests;

import com.petyes.api.Login;
import com.petyes.api.Request;
import com.petyes.config.App;
import com.petyes.pages.BasePage;
import com.petyes.pages.RequestPage;
import com.petyes.pages.components.CalendarComponent;
import com.petyes.pages.components.CityComponent;
import io.qameta.allure.AllureId;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

@Feature("Запрос")
public class RequestTests extends TestBase {

    @Test
    @AllureId("5701")
    @DisplayName("Создание запроса на питомца (куплю)")
    void createBuyRequestTest() {
        BasePage basePage = new BasePage();
        Login login = new Login();
        RequestPage requestPage = new RequestPage();
        CityComponent cityComponent = new CityComponent();

        String breed = "Австралийский келпи";
        String color = "Черный";
        String sex = "Самец";
        String age = "До 6 месяцев";
        String priceFrom = "10 000";
        String priceTo = "30 000";
        String city = "Санкт-Петербург";
        String comment = "Автотестовый комментарий";

        login
                .loginByAPI(App.config.customerPhoneNumberAPI(), App.config.userPassword());
        basePage
                .openPage("/buy-add")
                .selectValueFromDropdown("Выберите вид животного", "Собаки")
                .selectValueFromDropdown("Выберите породу животного", breed);
        requestPage
                .chooseColor(color);
        basePage
                .chooseRadio(sex)
                .chooseCheckbox(age)
                .enterValueByKeys(0, priceFrom)
                .enterValueByKeys(1, priceTo);
        cityComponent
                .chooseCity(city);
        basePage
                .clickOnButton("Продолжить");
        requestPage
                .chooseOption("Метрика", "Да")
                .chooseOption("Вакцинация", "Да")
                .chooseOption("Кастрация/стерилизация", "Нет")
                .chooseOption("Электронный чип", "Нет");
        basePage
                .clickOnButton("Продолжить")
                .enterValueInTextarea(comment)
                .clickOnButton("Опубликовать")
                .clickOnButton("Перейти к запросу")
                .checkHeaderH3(breed);
        requestPage
                .checkPrice(priceFrom + " - " + priceTo + " ₽")
                .checkTag("Не для разведения")
                .checkResults(0, "Порода", breed)
                .checkResults(1, "Пол", sex)
                .checkResults(2, "Возраст", age)
                .checkResults(3, "Окрас", color)
                .checkResults(4, "Метрика", "Да")
                .checkResults(5, "Вакцинация", "Да")
                .checkResults(6, "Кастрация", "Нет")
                .checkResults(7, "Электронный чип", "Нет")
                .checkResults(8, "Удобно забрать", city)
                .checkComment(comment);
    }

    @Test
    @DisplayName("Создание запроса на питомца (возьму бесплатно)")
    void createFreeRequestTest() {
        BasePage basePage = new BasePage();
        Login login = new Login();
        CityComponent cityComponent = new CityComponent();

        login
                .loginByAPI(App.config.customerPhoneNumberAPI(), App.config.userPassword());
        basePage
                .openPage("/buy-add")
                .chooseRadio("Возьму бесплатно в хорошие руки")
                .selectValueFromDropdown("Выберите вид животного", "Собаки");
        cityComponent
                .chooseCity("Санкт-Петербург");
        basePage
                .clickOnButton("Продолжить")
                .clickOnButton("Продолжить")
                .clickOnButton("Опубликовать")
                .clickOnButton("Перейти к запросу")
                .checkHeaderH3("Собаки");
    }

    @Test
    @AllureId("5702")
    @DisplayName("Удаление запроса на питомца")
    void deleteRequestTest() {
        BasePage basePage = new BasePage();
        Request request = new Request();
        RequestPage requestPage = new RequestPage();
        CalendarComponent calendarComponent = new CalendarComponent();
        Login login = new Login();

        String token = login.loginByAPI(App.config.customerPhoneNumberAPI(), App.config.userPassword());

        Date dateToday = calendarComponent.getTodayDate();
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String today = formater.format(dateToday);

        int id = request.createRequestByAPI(token, 13, 0, 20000, false,
                "Санкт-Петербург", "59.939084", "30.315879", 1007, 0, 6,
                false, true, today, 597);
        basePage
                .openPage("/buy/" + id)
                .clickOnButton("Удалить");
        requestPage
                .deleteRequest();
        basePage
                .checkGreenMessage();
    }

    @Test
    @DisplayName("Завершение запроса на питомца")
    void closeRequestTest() {
        BasePage basePage = new BasePage();
        Request request = new Request();
        CalendarComponent calendarComponent = new CalendarComponent();
        Login login = new Login();

        String token = login.loginByAPI(App.config.customerPhoneNumberAPI(), App.config.userPassword());

        Date dateToday = calendarComponent.getTodayDate();
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String today = formater.format(dateToday);

        int id = request.createRequestByAPI(token, 13, 0, 20000, false,
                "Санкт-Петербург", "59.939084", "30.315879", 1007, 0, 6,
                false, true, today, 597);
        basePage
                .openPage("/buy/" + id)
                .clickOnButton("Завершить")
                .chooseRadio("Другое")
                .enterValueInTextarea("Автотестовая причина завершения")
                .clickOnButton("Продолжить")
                .checkGreenMessage();
    }
}