package com.petyes.tests;

import com.petyes.api.*;
import com.petyes.config.App;
import com.petyes.pages.BasePage;
import com.petyes.pages.components.CalendarComponent;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class NotificationsTests extends TestBase {

    @Test
    @DisplayName("Уведомление о питомце, подходящем под запрос")
    void newPetNotificationTest() {
        BasePage basePage = new BasePage();
        Request request = new Request();
        Pet pet = new Pet();
        Sale sale = new Sale();
        CalendarComponent calendarComponent = new CalendarComponent();
        Login login = new Login();

        Date today = calendarComponent.getTodayDate();
        int request_id = request.createRequestByAPI(13, 0, 20000, false,
                "Санкт-Петербург", "59.939084", "30.315879", 1007, 0, 0, 6,
                false, true, today, 597);

        Date birth = calendarComponent.getOtherDate(-20);
        int pet_id = pet.createPetByAPI(false, 13, "autoTestCat", birth, 0, 1007, 0,597);
        int sale_id = sale.salePetByAPI(false, false, true, 10000, pet_id);

        String token = login.loginByAPI(App.config.customerPhoneNumber(), App.config.userPassword());

        login
                .setCookie(token);
        basePage
                .openPage("/user/notifications")
                .checkLinkById(sale_id);

        request.deleteRequestByAPI(request_id);
        sale.cancelPetSaleByAPI(pet_id);
        pet.deletePetByAPI(pet_id);
    }

    @Disabled("Баг с откликами, PET-724")
    @Test
    @DisplayName("Уведомление об отклике на запрос")
    void newResponseNotificationTest() {
        BasePage basePage = new BasePage();
        Request request = new Request();
        Pet pet = new Pet();
        Sale sale = new Sale();
        CalendarComponent calendarComponent = new CalendarComponent();
        Login login = new Login();
        Response response = new Response();

        Date today = calendarComponent.getTodayDate();
        int request_id = request.createRequestByAPI(13, 0, 20000, false,
                "Санкт-Петербург", "59.939084", "30.315879", 1007, 0, 0, 6,
                false, true, today, 597);

        Date birth = calendarComponent.getOtherDate(-20);
        int pet_id = pet.createPetByAPI(false, 13, "autoTestCat", birth, 0, 1007, 0,597);
        int sale_id = sale.salePetByAPI(false, false, true, 10000, pet_id);

        response.responseByAPI(request_id, pet_id);

        String token = login.loginByAPI(App.config.customerPhoneNumber(), App.config.userPassword());

        login
                .setCookie(token);
        basePage
                .openPage("/user/notifications")
                .openLink("/user/49?buy_request=" + request_id)
                .checkLinkById(sale_id);

        request.deleteRequestByAPI(request_id);
        sale.cancelPetSaleByAPI(sale_id);
        pet.deletePetByAPI(pet_id);
    }

    @Test
    @DisplayName("Уведомление о новом запросе, подходящем под объявление")
    void newRequestNotificationTest() {
        BasePage basePage = new BasePage();
        Request request = new Request();
        Pet pet = new Pet();
        Sale sale = new Sale();
        CalendarComponent calendarComponent = new CalendarComponent();
        Login login = new Login();

        Date birth = calendarComponent.getOtherDate(-20);
        int pet_id = pet.createPetByAPI(false, 13, "autoTestCat", birth, 0, 1007, 0,597);
        sale.salePetByAPI(false, false, true, 10000, pet_id);

        Date today = calendarComponent.getTodayDate();
        int request_id = request.createRequestByAPI(13, 0, 20000, false,
                "Санкт-Петербург", "59.939084", "30.315879", 1007, 0, 0, 6,
                false, true, today, 597);

        String token = login.loginByAPI(App.config.breederPhoneNumber(), App.config.userPassword());

        login
                .setCookie(token);
        basePage
                .openPage("/user/notifications")
                .checkLinkById(request_id);

        sale.cancelPetSaleByAPI(pet_id);
        pet.deletePetByAPI(pet_id);
        request.deleteRequestByAPI(request_id);
    }

    @Test
    @DisplayName("Уведомление о новом запросе, подходящем под специализацию")
    void newRequestSpecNotificationTest() {
        BasePage basePage = new BasePage();
        Request request = new Request();
        CalendarComponent calendarComponent = new CalendarComponent();
        Login login = new Login();

        Date today = calendarComponent.getTodayDate();
        int request_id = request.createRequestByAPI(13, 0, 20000, false,
                "Санкт-Петербург", "59.939084", "30.315879", 1007, 0, 0, 6,
                false, true, today, 597);

        String token = login.loginByAPI(App.config.breederPhoneNumber(), App.config.userPassword());

        login
                .setCookie(token);
        basePage
                .openPage("/user/notifications")
                .checkLinkById(request_id);

        request.deleteRequestByAPI(request_id);
    }
}
