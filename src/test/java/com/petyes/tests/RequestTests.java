package com.petyes.tests;

import com.petyes.api.Login;
import com.petyes.api.Pet;
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
        Request request = new Request();

        String petType = "Собаки";
        String breed = "Австралийский келпи";
        String color = "Черный";
        String sex = "Самец";
        String age = "До 6 месяцев";
        String priceFrom = "10 000";
        String priceTo = "30 000";
        String city = "Санкт-Петербург";
        String comment = "Автотестовый комментарий";

        String token = login.loginByAPI(App.config.customerPhoneNumberAPI(), App.config.userPassword());

        login
                .setCookie(token);
        basePage
                .openPage("/buy-add");
        requestPage
                .selectPetType("Выберите вид животного", petType);
        basePage
                .enterValueInDropdown("breed", breed);
        requestPage
                .chooseColor(color); //заменить
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
                .clickOnButton("Продолжить");
        requestPage
                .enterComment(comment);
        basePage
                .clickOnButton("Опубликовать")
                .clickOnButton("Перейти к запросу")
                .checkHeader(3, breed);
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

        int request_id = basePage.getIdFromUrl();
        request.deleteRequestByAPI(request_id);

    }

    @Test
    @DisplayName("Создание запроса на питомца (возьму бесплатно)")
    void createFreeRequestTest() {
        BasePage basePage = new BasePage();
        Login login = new Login();
        RequestPage requestPage = new RequestPage();
        CityComponent cityComponent = new CityComponent();
        Request request = new Request();

        String token = login.loginByAPI(App.config.customerPhoneNumberAPI(), App.config.userPassword());

        login
                .setCookie(token);
        basePage
                .openPage("/buy-add")
                .chooseRadio("Возьму бесплатно в хорошие руки");
        requestPage
                .selectPetType("Выберите вид животного", "Собаки");
        cityComponent
                .chooseCity("Санкт-Петербург");
        basePage
                .clickOnButton("Продолжить")
                .clickOnButton("Продолжить")
                .clickOnButton("Опубликовать")
                .clickOnButton("Перейти к запросу")
                .checkHeader(3,"Собаки");

        int request_id = basePage.getIdFromUrl();
        request.deleteRequestByAPI(request_id);
    }

    @Test
    @DisplayName("Редактирование запроса на питомца (цена)")
    void editRequestTest() {
        BasePage basePage = new BasePage();
        Request request = new Request();
        CalendarComponent calendarComponent = new CalendarComponent();
        Login login = new Login();
        RequestPage requestPage = new RequestPage();

        Date today = calendarComponent.getTodayDate();
        int request_id = request.createRequestByAPI(13, 0, 20000, false,
                "Санкт-Петербург", "59.939084", "30.315879", 1007, 0, 0, 6,
                false, true, today, 597);

        String priceFrom = "10 000";
        String priceTo = "30 000";

        String token = login.loginByAPI(App.config.customerPhoneNumberAPI(), App.config.userPassword());

        login
                .setCookie(token);
        basePage
                .openPage("/buy/" + request_id)
                .clickOnButton("Редактировать")
                .enterValueByKeys(0, priceFrom)
                .enterValueByKeys(1, priceTo)
                .clickOnButton("Продолжить")
                .clickOnButton("Продолжить")
                .clickOnButton("Сохранить")
                .checkGreenMessage();
        requestPage
                .checkPrice(priceFrom + " - " + priceTo + " ₽");

        request.deleteRequestByAPI(request_id);
    }

    //bug
    @Test
    @DisplayName("Смотреть подходящие предложения")
    void seeSaleOffersTest() {
        BasePage basePage = new BasePage();
        Request request = new Request();
        Pet pet = new Pet();
        CalendarComponent calendarComponent = new CalendarComponent();
        Login login = new Login();
        RequestPage requestPage = new RequestPage();

        Date birth = calendarComponent.getOtherDate(-20);
        int pet_id = pet.createPetByAPI(false, 13, "autoTestCat", birth, 0, 1007, 0,597);
        int sell_id = pet.salePetByAPI(false, false, true, 10000, pet_id);

        Date today = calendarComponent.getTodayDate();
        int request_id = request.createRequestByAPI(13, 0, 20000, false,
                "Санкт-Петербург", "59.939084", "30.315879", 1007, 0, 0, 6,
                false, true, today, 597);

        String token = login.loginByAPI(App.config.customerPhoneNumberAPI(), App.config.userPassword());

        login
                .setCookie(token);
        basePage
                .openPage("/buy/" + request_id)
                .clickOnButton("Смотреть предложения");
        requestPage
                .checkSaleOffer(0, sell_id);

        pet.cancelPetSaleByAPI(sell_id);
        pet.deletePetByAPI(pet_id);
        request.deleteRequestByAPI(request_id);
    }

    @Test
    @DisplayName("Смотреть похожие предложения")
    void seeSimilarOffersTest() {
        BasePage basePage = new BasePage();
        Request request = new Request();
        Pet pet = new Pet();
        CalendarComponent calendarComponent = new CalendarComponent();
        Login login = new Login();
        RequestPage requestPage = new RequestPage();

        Date birth = calendarComponent.getOtherDate(-20);
        int pet_id = pet.createPetByAPI(false, 13, "autoTestCat", birth, 1, 1007, 0,597);
        int sell_id = pet.salePetByAPI(false, false, true, 10000, pet_id);

        Date today = calendarComponent.getTodayDate();
        int request_id = request.createRequestByAPI(13, 0, 20000, false,
                "Санкт-Петербург", "59.939084", "30.315879", 1007, 0, 0, 6,
                false, true, today, 597);

        String token = login.loginByAPI(App.config.customerPhoneNumberAPI(), App.config.userPassword());

        login
                .setCookie(token);
        basePage
                .openPage("/buy/" + request_id)
                .clickOnButton("Смотреть предложения");
        requestPage
                .checkSaleOffer(1, sell_id);

        pet.cancelPetSaleByAPI(sell_id);
        pet.deletePetByAPI(pet_id);
        request.deleteRequestByAPI(request_id);
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

        Date today = calendarComponent.getTodayDate();
        int id = request.createRequestByAPI(13, 0, 20000, false,
                "Санкт-Петербург", "59.939084", "30.315879", 1007, 0,0, 6,
                false, true, today, 597);

        String token = login.loginByAPI(App.config.customerPhoneNumberAPI(), App.config.userPassword());

        login
                .setCookie(token);
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
        RequestPage requestPage = new RequestPage();

        Date today = calendarComponent.getTodayDate();
        int request_id = request.createRequestByAPI(13, 0, 20000, false,
                "Санкт-Петербург", "59.939084", "30.315879", 1007, 0,0, 6,
                false, true, today, 597);

        String token = login.loginByAPI(App.config.customerPhoneNumberAPI(), App.config.userPassword());

        login
                .setCookie(token);
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