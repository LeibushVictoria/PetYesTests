package com.petyes.tests;

import com.petyes.api.Login;
import com.petyes.api.Request;
import com.petyes.domain.DataBuilder;
import com.petyes.pages.BasePage;
import com.petyes.pages.RequestPage;
import com.petyes.pages.components.CalendarComponent;
import com.petyes.pages.components.CityComponent;
import io.qameta.allure.AllureId;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Date;

@Feature("Запрос")
public class RequestTests extends TestBase {

    @Test
    @Tag("smoke")
    @Tag("regression")
    @AllureId("5701")
    @DisplayName("Создание запроса на питомца (куплю)")
    void createBuyRequestTest() {
        BasePage basePage = new BasePage();
        Login login = new Login();
        RequestPage requestPage = new RequestPage();
        CityComponent cityComponent = new CityComponent();
        Request request = new Request();

        String petType = "Кошки";
        String breed = "Абиссинская";
        String sex = "Самец";
        String age = "До 6 месяцев";
        String priceFrom = "10 000";
        String priceTo = "30 000";
        String city = "Санкт-Петербург";

        login
                .setCookie(DataBuilder.customerToken);
        basePage
                .openPage("/buy-add");
        requestPage
                .selectPetType("Выберите вид животного", petType);
        basePage
                .enterValueInDropdown("breed", breed)
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
                .clickOnButton("Отправить")
                .clickOnButton("Перейти к запросу")
                .checkHeader(3, breed);
        requestPage
                .checkTag("Не для разведения")
                .checkResults(0, "Порода", breed)
                .checkResults(1, "Пол", sex)
                .checkResults(2, "Возраст", age)
                .checkResults(4, "Метрика", "Да")
                .checkResults(5, "Вакцинация", "Да")
                .checkResults(6, "Кастрация", "Нет")
                .checkResults(7, "Электронный чип", "Нет")
                .checkResults(8, "Удобно забрать", city);

        int request_id = basePage.getIdFromUrl();
        request.deleteRequestByAPI(request_id);

    }

    @Test
    @Tag("regression")
    @DisplayName("Создание запроса на питомца (возьму бесплатно)")
    void createFreeRequestTest() {
        BasePage basePage = new BasePage();
        Login login = new Login();
        RequestPage requestPage = new RequestPage();
        CityComponent cityComponent = new CityComponent();
        Request request = new Request();

        String petType = "Собаки";

        login
                .setCookie(DataBuilder.customerToken);
        basePage
                .openPage("/buy-add")
                .chooseRadio("Возьму бесплатно в хорошие руки");
        requestPage
                .selectPetType("Выберите вид животного", petType);
        cityComponent
                .chooseCity("Санкт-Петербург");
        basePage
                .clickOnButton("Продолжить")
                .clickOnButton("Отправить")
                .clickOnButton("Перейти к запросу")
                .checkHeader(3, petType)
                .checkBlockDisplay("Бесплатно");

        int request_id = basePage.getIdFromUrl();
        request.deleteRequestByAPI(request_id);
    }

    @Test
    @Tag("regression")
    @DisplayName("Редактирование запроса на питомца (пол)")
    void editRequestTest() {
        BasePage basePage = new BasePage();
        Request request = new Request();
        CalendarComponent calendarComponent = new CalendarComponent();
        Login login = new Login();
        RequestPage requestPage = new RequestPage();

        Date today = calendarComponent.getTodayDate();
        int request_id = request.createRequestByAPI(0, 20000, false,
                "Санкт-Петербург", "59.939084", "30.315879", 0, 0, 6,
                false, true, today);

        String sex = "Самка";

        login
                .setCookie(DataBuilder.customerToken);
        basePage
                .openPage("/buy/" + request_id)
                .clickOnButton("Редактировать")
                .chooseRadio(sex)
                .clickOnButton("Продолжить")
                .clickOnButton("Сохранить")
                .checkGreenMessage();
        requestPage
                .checkResults(1, "Пол", sex);

        request.deleteRequestByAPI(request_id);
    }

    @Test
    @Tag("regression")
    @DisplayName("PET-729 Смотреть подходящие предложения")
    void seeSaleOffersTest() {
        BasePage basePage = new BasePage();
        Login login = new Login();
        RequestPage requestPage = new RequestPage();

        login
                .setCookie(DataBuilder.customerToken);
        basePage
                .openPage("/buy/" + DataBuilder.request_id)
                .clickOnButton("Смотреть предложения");
        requestPage
                .checkSaleOffer(0, DataBuilder.sale_id);
    }

    @Test
    @Tag("smoke")
    @Tag("regression")
    @DisplayName("Смотреть похожие предложения (разный пол)")
    void seeSimilarOffersTest() {
        BasePage basePage = new BasePage();
        Request request = new Request();
        CalendarComponent calendarComponent = new CalendarComponent();
        Login login = new Login();
        RequestPage requestPage = new RequestPage();

        Date today = calendarComponent.getTodayDate();
        int request_id = request.createRequestByAPI(0, 20000, false,
                "Санкт-Петербург", "59.939084", "30.315879", 1, 0, 6,
                false, true, today);

        login
                .setCookie(DataBuilder.customerToken);
        basePage
                .openPage("/buy/" + request_id)
                .clickOnButton("Смотреть предложения");
        requestPage
                .checkSaleOffer(1, DataBuilder.sale_id);

        request.deleteRequestByAPI(request_id);
    }

    @Test
    @Tag("regression")
    @AllureId("5702")
    @DisplayName("Удаление запроса на питомца")
    void deleteRequestTest() {
        BasePage basePage = new BasePage();
        Request request = new Request();
        RequestPage requestPage = new RequestPage();
        CalendarComponent calendarComponent = new CalendarComponent();
        Login login = new Login();

        Date today = calendarComponent.getTodayDate();
        int id = request.createRequestByAPI(0, 20000, false,
                "Санкт-Петербург", "59.939084", "30.315879", 0,0, 6,
                false, true, today);

        login
                .setCookie(DataBuilder.customerToken);
        basePage
                .openPage("/buy/" + id)
                .clickOnButton("Удалить");
        requestPage
                .deleteRequest();
        basePage
                .checkGreenMessage();
    }

    @Test
    @Tag("regression")
    @DisplayName("Завершение запроса на питомца")
    void closeRequestTest() {
        BasePage basePage = new BasePage();
        Request request = new Request();
        CalendarComponent calendarComponent = new CalendarComponent();
        Login login = new Login();
        RequestPage requestPage = new RequestPage();

        Date today = calendarComponent.getTodayDate();
        int request_id = request.createRequestByAPI(0, 20000, false,
                "Санкт-Петербург", "59.939084", "30.315879", 0,0, 6,
                false, true, today);

        login
                .setCookie(DataBuilder.customerToken);
        basePage
                .openPage("/buy/" + request_id)
                .clickOnButton("Завершить")
                .chooseRadio("Другое");
        requestPage
                .enterReason("Автотестовая причина завершения");
        basePage
                .clickOnButton("Продолжить")
                .checkGreenMessage();

        request.deleteRequestByAPI(request_id);
    }
}