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

public class ProfileTests extends TestBase{

    @Test
    @DisplayName("Просмотр своего профиля")
    void viewProfileTest() {
        BasePage basePage = new BasePage();
        Login login = new Login();

        String token = login.loginByAPI(App.config.customerPhoneNumberAPI(), App.config.userPassword());
        int user_id = login.getUserId(token);
        basePage
                .openPage("/user/" + user_id)
                .checkHeaderH4("Покупатель Автотест");
    }

    @Test
    @DisplayName("Редактирование своего профиля (о себе)")
    void editProfileTest() {
        Faker faker = new Faker();
        BasePage basePage = new BasePage();
        Login login = new Login();

        String aboutMe = faker.chuckNorris().fact();

        String token = login.loginByAPI(App.config.customerPhoneNumberAPI(), App.config.userPassword());
        int user_id = login.getUserId(token);

        basePage
                .openPage("/settings/info")
                .clearTextarea()
                .enterValueInTextarea("О себе", aboutMe)
                .clickOnButton("Сохранить изменения")
                .checkGreenMessage()
                .openPage("/user/" + user_id)
                .checkBlockDisplay(aboutMe);
    }

    @Test
    @DisplayName("Просмотр запросов")
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

        int id = request.createRequestByAPI(token, 13, 0, 20000, false,
                "Санкт-Петербург", "59.939084", "30.315879", 1007, 0, 6,
                false, true, today, 597);
        basePage
                .openPage("/user/" + user_id + "#requests")
                .checkLink(id);
    }
}
