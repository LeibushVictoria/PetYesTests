package com.petyes.tests;

import com.petyes.api.Login;
import com.petyes.config.App;
import com.petyes.pages.BasePage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FaqsTests extends TestBase {

    @Test
    @DisplayName("Оформить подписку в разделе Вопросы")
    void viewBreedTest() {
        BasePage basePage = new BasePage();
        Login login = new Login();

        String token = login.loginByAPI(App.config.customerPhoneNumberAPI(), App.config.userPassword());

        login
                .setCookie(token);
        basePage
                .openPage("/faqs")
                .enterValueInInput("email", "test@test.test")
                .clickOnButton("Подписаться")
                .checkGreenMessage();
    }
}
