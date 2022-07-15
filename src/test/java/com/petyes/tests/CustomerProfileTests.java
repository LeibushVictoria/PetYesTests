package com.petyes.tests;

import com.github.javafaker.Faker;
import com.petyes.api.Login;
import com.petyes.api.Request;
import com.petyes.config.App;
import com.petyes.pages.BasePage;
import com.petyes.pages.components.CalendarComponent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomerProfileTests extends TestBase {

    @Test
    @DisplayName("Просмотр покупателем своего профиля")
    void viewCustomerProfileTest() {
        BasePage basePage = new BasePage();
        Login login = new Login();

        String token = login.loginByAPI(App.config.customerPhoneNumberAPI(), App.config.userPassword());
        int user_id = login.getUserId(token);

        login
                .setCookie(token);
        basePage
                .openPage("/user/" + user_id)
                .checkHeader(4,"Покупатель Автотест");
    }

    @Test
    @DisplayName("Редактирование покупателем своего профиля (о себе)")
    void editProfileTest() {
        Faker faker = new Faker();
        BasePage basePage = new BasePage();
        Login login = new Login();

        String aboutMe = faker.chuckNorris().fact();

        String token = login.loginByAPI(App.config.customerPhoneNumberAPI(), App.config.userPassword());
        int user_id = login.getUserId(token);

        login
                .setCookie(token);
        basePage
                .openPage("/settings/info")
                .clearTextarea()
                .enterValueInTextarea("description", aboutMe)
                .clickOnButton("Сохранить изменения")
                .checkGreenMessage()
                .openPage("/user/" + user_id)
                .checkBlockDisplay(aboutMe);
    }

    @Test
    @DisplayName("Просмотр покупателем запросов в своем профиле")
    void viewRequestTest() {
        BasePage basePage = new BasePage();
        Request request = new Request();
        CalendarComponent calendarComponent = new CalendarComponent();
        Login login = new Login();

        String token = login.loginByAPI(App.config.customerPhoneNumberAPI(), App.config.userPassword());
        int user_id = login.getUserId(token);

        Date dateToday = calendarComponent.getTodayDate();
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String today = formater.format(dateToday);

        int request_id = request.createRequestByAPI(token,13, 0, 20000, false,
                "Санкт-Петербург", "59.939084", "30.315879", 1007, 0,0, 6,
                false, true, today, 597);

        login
                .setCookie(token);
        basePage
                .openPage("/user/" + user_id + "#requests")
                .checkLinkById(request_id);

        request.deleteRequestByAPI(token, request_id);
    }
}
