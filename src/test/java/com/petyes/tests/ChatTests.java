package com.petyes.tests;

import com.petyes.api.Login;
import com.petyes.config.AuthConfig;
import com.petyes.pages.BasePage;
import com.petyes.pages.ChatPage;
import com.petyes.pages.components.CalendarComponent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatTests extends TestBase {

    @Test
    @Tag("smoke")
    @Tag("regression")
    @DisplayName("Отправка и получение сообщения")
    void messageExchangeTest() {
        BasePage basePage = new BasePage();
        Login login = new Login();
        ChatPage chatPage = new ChatPage();
        CalendarComponent calendarComponent = new CalendarComponent();

        Date dateToday = calendarComponent.getTodayDate();
        SimpleDateFormat formater = new SimpleDateFormat("HH:mm:ss");
        String message = "Автотестовое сообщение " + formater.format(dateToday);

        login
                .setCookie(AuthConfig.customerToken);
        basePage
                .openPage("/user/" + AuthConfig.breederId)
                .clickOnButton("Начать чат")
                .enterValueInSingleTextarea(message);
        chatPage
                .sendMessage()
                .checkMessage(message);
        login
                .setCookie(AuthConfig.breederToken);
        basePage
                .openPage("/chat");
        chatPage
                .openChat("Покупатель Автотест")
                .checkMessage(message);
    }
}