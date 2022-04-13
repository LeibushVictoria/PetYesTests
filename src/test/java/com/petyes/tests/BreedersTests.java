package com.petyes.tests;

import com.petyes.config.App;
import com.petyes.pages.BreedersPage;
import com.petyes.api.Login;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


@Feature("Заводчики")
public class BreedersTests extends TestBase {

    @Test
    @DisplayName("Переход в создание запроса")
    void CreateRequestTest() {
        Login login = new Login();
        BreedersPage breedersPage = new BreedersPage();

        login.loginByAPI(App.config.customerPhoneNumberAPI(), App.config.userPassword());
        breedersPage
                .openBreedersPage()
                .clickCreateRequestButton()
                .checkCreateRequestPageOpened();
    }

    @Test
    @DisplayName("Открытие страницы заводчика")
    void OpenBreederTest() {
        Login login = new Login();
        BreedersPage breedersPage = new BreedersPage();

        login.loginByAPI(App.config.customerPhoneNumberAPI(), App.config.userPassword());
        breedersPage
                .openBreedersPage()
                .openFirstBreederPage()
                .checkFirstBreederPageOpened();
    }

    @Test
    @DisplayName("Оставить отзыв о зоводчике")
    void BreederReviewTest() {
        Login login = new Login();
        BreedersPage breedersPage = new BreedersPage();

        login.loginByAPI(App.config.customerPhoneNumberAPI(), App.config.userPassword());
        breedersPage
                .openBreedersPage()
                .clickCreateRewiewButton()
                .clickRatingButton()
                .setComment("тест")
                .clickOnSubmit()
                .checkRewiewCreated();
    }
}
