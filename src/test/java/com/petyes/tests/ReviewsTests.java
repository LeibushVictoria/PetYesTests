package com.petyes.tests;

import com.petyes.api.Login;
import com.petyes.config.App;
import com.petyes.pages.BasePage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ReviewsTests extends TestBase {

    @Test
    @DisplayName("Оставить отзыв")
    void compareBreedsTest() {
        BasePage basePage = new BasePage();
        Login login = new Login();

        String token = login.loginByAPI(App.config.customerPhoneNumberAPI(), App.config.userPassword());

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
