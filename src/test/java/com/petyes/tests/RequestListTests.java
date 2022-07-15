package com.petyes.tests;

import com.petyes.api.Login;
import com.petyes.api.Pet;
import com.petyes.api.Request;
import com.petyes.config.App;
import com.petyes.pages.BasePage;
import com.petyes.pages.RequestPage;
import com.petyes.pages.components.CalendarComponent;
import com.petyes.pages.components.CityComponent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RequestListTests extends TestBase {

    @Test
    @DisplayName("Работа фильтров")
    void filterRequestsTest() {
        BasePage basePage = new BasePage();
        Login login = new Login();
        CityComponent cityComponent = new CityComponent();
        CalendarComponent calendarComponent = new CalendarComponent();
        Request request = new Request();

        Date dateToday = calendarComponent.getTodayDate();
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String today = formater.format(dateToday);

        String customerToken = login.loginByAPI(App.config.customerPhoneNumberAPI(), App.config.userPassword());

        int request_id = request.createRequestByAPI(customerToken, 13, 10000, 10001, false,
                "Санкт-Петербург", "59.939084", "30.315879", 1007, 0,0, 6,
                false, true, today, 597);

        String breederToken = login.loginByAPI(App.config.breederPhoneNumberAPI(), App.config.userPassword());

        login
                .setCookie(breederToken);
        basePage
                .openPage("/search?type=0");
        cityComponent
                .chooseCity("Санкт-Петербург");
        basePage
                .chooseRadio("Кошки")
                .selectValueInDropdownInFilter("Выберите породу", "Абиссинская")
                .chooseRadio("Куплю")
                .enterValueByKeys(1, "9999")
                .enterValueByKeys(2, "10002")
                .chooseRadio("Самец")
                //.chooseCheckbox("До 6 месяцев") bug
                .clickOnSubmitButton()
                .checkLinkById(request_id);

        request.deleteRequestByAPI(customerToken, request_id);
    }

    @Test
    @DisplayName("Просмотр запроса")
    void viewRequestTest() {
        BasePage basePage = new BasePage();
        Login login = new Login();
        CalendarComponent calendarComponent = new CalendarComponent();
        Request request = new Request();

        Date dateToday = calendarComponent.getTodayDate();
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String today = formater.format(dateToday);

        String customerToken = login.loginByAPI(App.config.customerPhoneNumberAPI(), App.config.userPassword());

        int request_id = request.createRequestByAPI(customerToken, 13, 10000, 10001, false,
                "Санкт-Петербург", "59.939084", "30.315879", 1007, 0,0, 6,
                false, true, today, 597);

        String breederToken = login.loginByAPI(App.config.breederPhoneNumberAPI(), App.config.userPassword());

        login
                .setCookie(breederToken);
        basePage
                .openPage("/buy/" + request_id)
                .checkBlockDisplay("Требуемые характеристики")
                .checkBlockDisplay("Откликнуться")
                .checkBlockDisplay("Покупатель Автотест");

        request.deleteRequestByAPI(customerToken, request_id);
    }

    //bug
    @Test
    @DisplayName("Отклик на запрос")
    void requestApplicationTest() {
        BasePage basePage = new BasePage();
        Login login = new Login();
        CalendarComponent calendarComponent = new CalendarComponent();
        Pet pet = new Pet();
        Request request = new Request();
        RequestPage requestPage = new RequestPage();

        Date dateToday = calendarComponent.getTodayDate();
        SimpleDateFormat formaterRequest = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String today = formaterRequest.format(dateToday);

        Date dateBirth = calendarComponent.getOtherDate(-20);
        SimpleDateFormat formaterBirth = new SimpleDateFormat("dd.MM.yyyy");
        String birth = formaterBirth.format(dateBirth);

        String breederToken = login.loginByAPI(App.config.breederPhoneNumberAPI(), App.config.userPassword());

        int pet_id = pet.createPetByAPI(breederToken, false, 13, "autoTestRequestCat", birth, 0, 1004, 1,596);
        pet.salePetByAPI(breederToken, false, false, true, 10000, pet_id);

        String customerToken = login.loginByAPI(App.config.customerPhoneNumberAPI(), App.config.userPassword());

        int request_id = request.createRequestByAPI(customerToken, 13, 10000, 10001, false,
                "Санкт-Петербург", "59.939084", "30.315879", 1004, 0,0, 6,
                false, true, today, 596);

        login
                .setCookie(breederToken);
        basePage
                .openPage("/buy/" + request_id)
                .clickOnButton("Откликнуться");
        requestPage
                .choosePetToResponse(pet_id);
        basePage
                .clickOnButton("Предложить (1)")
                .checkGreenMessage()
                .checkBlockDisplay("Отменить отклик");

        pet.cancelPetSaleByAPI(breederToken, pet_id);
        pet.deletePetByAPI(breederToken, pet_id);
        request.deleteRequestByAPI(customerToken, request_id);
    }
}