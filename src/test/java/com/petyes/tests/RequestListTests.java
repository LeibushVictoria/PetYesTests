package com.petyes.tests;

import com.petyes.api.Login;
import com.petyes.api.Pet;
import com.petyes.api.Request;
import com.petyes.api.Sale;
import com.petyes.config.App;
import com.petyes.pages.BasePage;
import com.petyes.pages.RequestPage;
import com.petyes.pages.components.CalendarComponent;
import com.petyes.pages.components.CityComponent;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class RequestListTests extends TestBase {

    @Test
    @Tag("smoke")
    @Tag("regression")
    @DisplayName("Работа фильтров")
    void filterRequestsTest() {
        BasePage basePage = new BasePage();
        Login login = new Login();
        CityComponent cityComponent = new CityComponent();
        CalendarComponent calendarComponent = new CalendarComponent();
        Request request = new Request();

        Date today = calendarComponent.getTodayDate();
        int request_id = request.createRequestByAPI(10000, 10001, false,
                "Санкт-Петербург", "59.939084", "30.315879", 0,0, 6,
                false, true, today);

        String token = login.loginByAPI(App.config.breederPhoneNumber(), App.config.userPassword());

        login
                .setCookie(token);
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
                //.chooseCheckbox("До 6 месяцев") bug PET-737
                .clickOnSubmitButton()
                .checkLinkById(request_id);

        request.deleteRequestByAPI(request_id);
    }

    @Test
    @Tag("regression")
    @DisplayName("Просмотр запроса")
    void viewRequestTest() {
        BasePage basePage = new BasePage();
        Login login = new Login();
        CalendarComponent calendarComponent = new CalendarComponent();
        Request request = new Request();

        Date today = calendarComponent.getTodayDate();
        int request_id = request.createRequestByAPI(10000, 10001, false,
                "Санкт-Петербург", "59.939084", "30.315879", 0,0, 6,
                false, true, today);

        String token = login.loginByAPI(App.config.breederPhoneNumber(), App.config.userPassword());

        login
                .setCookie(token);
        basePage
                .openPage("/buy/" + request_id)
                .checkBlockDisplay("Требуемые характеристики")
                .checkBlockDisplay("Откликнуться")
                .checkBlockDisplay("Покупатель Автотест");

        request.deleteRequestByAPI(request_id);
    }

    @Disabled("Баг с откликами, PET-724")
    @Test
    @Tag("regression")
    @DisplayName("Отклик на запрос")
    void requestApplicationTest() {
        BasePage basePage = new BasePage();
        Login login = new Login();
        CalendarComponent calendarComponent = new CalendarComponent();
        Pet pet = new Pet();
        Sale sale = new Sale();
        Request request = new Request();
        RequestPage requestPage = new RequestPage();

        Date birth = calendarComponent.getOtherDate(-20);
        int pet_id = pet.createPetByAPI(false, "autoTestRequestCat", birth, 0, 1);
        sale.salePetByAPI(false, false, true, 10000, pet_id);

        Date today = calendarComponent.getTodayDate();
        int request_id = request.createRequestByAPI(10000, 10001, false,
                "Санкт-Петербург", "59.939084", "30.315879", 0,0, 6,
                false, true, today);

        String token = login.loginByAPI(App.config.breederPhoneNumber(), App.config.userPassword());

        login
                .setCookie(token);
        basePage
                .openPage("/buy/" + request_id)
                .clickOnButton("Откликнуться");
        requestPage
                .choosePetToResponse(pet_id);
        basePage
                .clickOnButton("Предложить (1)")
                .checkGreenMessage()
                .checkBlockDisplay("Отменить отклик");

        sale.cancelPetSaleByAPI(pet_id);
        pet.deletePetByAPI(pet_id);
        request.deleteRequestByAPI(request_id);
    }
}
