package com.petyes.tests;

import com.petyes.api.Login;
import com.petyes.config.App;
import com.petyes.domain.ItemsForLogin;
import com.petyes.pages.BasePage;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

public class FaqsTests extends TestBase {

    @EnumSource(ItemsForLogin.class)
    @ParameterizedTest(name = "Оформить подписку в разделе Вопросы: {0}")
    void viewBreedTest(ItemsForLogin items) {
        BasePage basePage = new BasePage();
        Login login = new Login();

        String token = login.loginByAPI(items.getPhoneNumber(), App.config.userPassword());

        login
                .setCookie(token);
        basePage
                .openPage("/faqs")
                .enterValueInInput("email", "test@test.test")
                .clickOnButton("Подписаться")
                .checkGreenMessage();
    }
}
