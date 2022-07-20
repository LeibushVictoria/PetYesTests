package com.petyes.tests;

import com.petyes.api.Login;
import com.petyes.config.App;
import com.petyes.domain.ItemsForLogin;
import com.petyes.pages.BasePage;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

public class ReviewsTests extends TestBase {

    @EnumSource(ItemsForLogin.class)
    @ParameterizedTest(name = "Оставить отзыв: {0}")
    @Tag("regression")
    void compareBreedsTest(ItemsForLogin items) {
        BasePage basePage = new BasePage();
        Login login = new Login();

        String token = login.loginByAPI(items.getPhoneNumber(), App.config.userPassword());

        login
                .setCookie(token);
        basePage
                .openPage("/reviews")
                .clickOnButton("Написать отзыв")
                .enterValueInTextarea("review", "Автотестовый отзыв")
                .clickOnButton("Отправить отзыв")
                .checkModalBlockDisplay("Отзыв отправлен на модерацию");
    }
}
