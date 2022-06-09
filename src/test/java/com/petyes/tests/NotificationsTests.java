package com.petyes.tests;

import com.petyes.api.Login;
import com.petyes.api.Pet;
import com.petyes.api.Request;
import com.petyes.config.App;
import com.petyes.pages.BasePage;
import com.petyes.pages.components.CalendarComponent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NotificationsTests extends TestBase {

    @Test
    @DisplayName("Уведомление о питомце, подходящем под запрос")
    void newPetNotificationTest() {
        BasePage basePage = new BasePage();
        Request request = new Request();
        Pet pet = new Pet();
        CalendarComponent calendarComponent = new CalendarComponent();
        Login login = new Login();

        String customerToken = login.loginByAPI(App.config.customerPhoneNumberAPI(), App.config.userPassword());

        Date dateToday = calendarComponent.getTodayDate();
        SimpleDateFormat formaterRequest = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String today = formaterRequest.format(dateToday);

        int request_id = request.createRequestByAPI(customerToken, 13, 0, 20000, false,
                "Санкт-Петербург", "59.939084", "30.315879", 1007, 0, 0, 6,
                false, true, today, 598);

        String breederToken = login.loginByAPI(App.config.breederPhoneNumberAPI(), App.config.userPassword());

        Date dateBirth = calendarComponent.getOtherDate(-20);
        SimpleDateFormat formaterPet = new SimpleDateFormat("dd.MM.yyyy");
        String birth = formaterPet.format(dateBirth);

        int pet_id = pet.createPetByAPI(breederToken, false, 13, "autoTestCat", birth, 0, 1007, 0,598);
        int sell_id = pet.sellPetByAPI(breederToken, false, false, true, 10000, pet_id);

        login
                .setCookie(customerToken);
        basePage
                .openPage("/user/notifications")
                .checkLink(sell_id);

        request.deleteRequestByAPI(customerToken, request_id);
        pet.cancelSellPetByAPI(breederToken, sell_id);
        pet.deletePetByAPI(breederToken, pet_id);
    }

    @Test
    @DisplayName("Уведомление о новом запросе, подходящем под объявление")
    void newRequestNotificationTest() {
        BasePage basePage = new BasePage();
        Request request = new Request();
        Pet pet = new Pet();
        CalendarComponent calendarComponent = new CalendarComponent();
        Login login = new Login();

        String breederToken = login.loginByAPI(App.config.breederPhoneNumberAPI(), App.config.userPassword());

        Date dateBirth = calendarComponent.getOtherDate(-20);
        SimpleDateFormat formaterPet = new SimpleDateFormat("dd.MM.yyyy");
        String birth = formaterPet.format(dateBirth);

        int pet_id = pet.createPetByAPI(breederToken, false, 13, "autoTestCat", birth, 0, 1007, 0,598);
        int sell_id = pet.sellPetByAPI(breederToken, false, false, true, 10000, pet_id);

        String customerToken = login.loginByAPI(App.config.customerPhoneNumberAPI(), App.config.userPassword());

        Date dateToday = calendarComponent.getTodayDate();
        SimpleDateFormat formaterRequest = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String today = formaterRequest.format(dateToday);

        int request_id = request.createRequestByAPI(customerToken, 13, 0, 20000, false,
                "Санкт-Петербург", "59.939084", "30.315879", 1007, 0, 0, 6,
                false, true, today, 598);

        login
                .setCookie(breederToken);
        basePage
                .openPage("/user/notifications")
                .checkLink(request_id);

        pet.cancelSellPetByAPI(breederToken, sell_id);
        pet.deletePetByAPI(breederToken, pet_id);
        request.deleteRequestByAPI(customerToken, request_id);
    }

    @Test
    @DisplayName("Уведомление о новом запросе, подходящем под специализацию")
    void newRequestSpecNotificationTest() {
        BasePage basePage = new BasePage();
        Request request = new Request();
        Pet pet = new Pet();
        CalendarComponent calendarComponent = new CalendarComponent();
        Login login = new Login();

        String customerToken = login.loginByAPI(App.config.customerPhoneNumberAPI(), App.config.userPassword());

        Date dateToday = calendarComponent.getTodayDate();
        SimpleDateFormat formaterRequest = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String today = formaterRequest.format(dateToday);

        int request_id = request.createRequestByAPI(customerToken, 13, 0, 20000, false,
                "Санкт-Петербург", "59.939084", "30.315879", 1007, 0, 0, 6,
                false, true, today, 597);

        String breederToken = login.loginByAPI(App.config.breederPhoneNumberAPI(), App.config.userPassword());

        login
                .setCookie(breederToken);
        basePage
                .openPage("/user/notifications")
                .checkLink(request_id);

        request.deleteRequestByAPI(customerToken, request_id);
    }
}
