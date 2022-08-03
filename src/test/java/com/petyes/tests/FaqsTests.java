package com.petyes.tests;

import com.petyes.api.Login;
import com.petyes.domain.ItemsForLogin;
import com.petyes.pages.BasePage;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

public class FaqsTests extends TestBase {

    @EnumSource(ItemsForLogin.class)
    @ParameterizedTest(name = "Оформить подписку в разделе Вопросы: {0}")
    @Tag("regression")
    void viewBreedTest(ItemsForLogin items) {
        BasePage basePage = new BasePage();
        Login login = new Login();

        login
                .setCookie(items.getToken());
        basePage
                .openPage("/faqs")
                .enterValueInInput("email", "test@test.test")
                .clickOnButton("Подписаться")
                .checkGreenMessage();
    }
}
