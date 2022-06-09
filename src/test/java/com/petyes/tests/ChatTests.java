package com.petyes.tests;

import com.petyes.api.Login;
import com.petyes.config.App;
import com.petyes.pages.BasePage;
import com.petyes.pages.ChatPage;
import com.petyes.pages.components.CalendarComponent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatTests extends TestBase {

    @Test
    @DisplayName("Отправка и получение сообщения")
    void messageExchangeTest() {
        BasePage basePage = new BasePage();
        Login login = new Login();
        ChatPage chatPage = new ChatPage();
        CalendarComponent calendarComponent = new CalendarComponent();

        String customerToken = login.loginByAPI(App.config.customerPhoneNumberAPI(), App.config.userPassword());
        String breederToken = login.loginByAPI(App.config.breederPhoneNumberAPI(), App.config.userPassword());

        Date dateToday = calendarComponent.getTodayDate();
        SimpleDateFormat formater = new SimpleDateFormat("HH:mm:ss");
        String message = "Автотестовое сообщение " + formater.format(dateToday);

        login
                .setCookie(customerToken);
        basePage
                .openPage("/chat");
        chatPage
                .openChat("Продавец Автотест")
                .writeMessage(message)
                .sendMessage()
                .checkMessage(message);
        login
                .setCookie(breederToken);
        basePage
                .openPage("/chat");
        chatPage
                .openChat("Покупатель Автотест")
                .checkMessage(message);
    }
}