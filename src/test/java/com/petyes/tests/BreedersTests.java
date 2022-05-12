package com.petyes.tests;

import com.petyes.config.App;
import com.petyes.pages.BasePage;
import com.petyes.pages.BreedersPage;
import com.petyes.api.Login;
import com.petyes.pages.components.CityComponent;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


@Feature("Заводчики")
public class BreedersTests extends TestBase {

    @Test
    @DisplayName("Переход в создание запроса из раздела Заводчики")
    void createRequestTest() {
        BasePage basePage = new BasePage();
        Login login = new Login();
        login
                .loginByAPI(App.config.customerPhoneNumberAPI(), App.config.userPassword());
        basePage
                .openPage("/breeders")
                .clickOnButton("Найти питомца")
                .checkHeaderH2("Создание запроса на подбор животного");
    }

    @Test
    @DisplayName("Открытие страницы заводчика")
    void openBreederTest() {
        BasePage basePage = new BasePage();
        Login login = new Login();
        BreedersPage breedersPage = new BreedersPage();
        login
                .loginByAPI(App.config.customerPhoneNumberAPI(), App.config.userPassword());
        basePage
                .openPage("/breeders");
        breedersPage
                .openBreederPage("Продавец Автотест");
        basePage
                .checkHeaderH5("Привет, я Продавец Автотест");
    }

    @Test
    @DisplayName("Оставить отзыв о зоводчике")
    void breederReviewTest() {
        BasePage basePage = new BasePage();
        Login login = new Login();
        BreedersPage breedersPage = new BreedersPage();
        login
                .loginByAPI(App.config.customerPhoneNumberAPI(), App.config.userPassword());
        basePage
                .openPage("/breeders");
        breedersPage
                .openRewiewPopup("Продавец Автотест");
        basePage
                .clickOnButton("Написать отзыв");
        breedersPage
                .clickRatingButton(3)
                .setComment("тест");
        basePage
                .clickOnButton("Отправить");
        breedersPage
                .checkRewiewCreated();
    }

    @Test
    @DisplayName("Работа фильтров")
    void filterBreedersTest() {
        BasePage basePage = new BasePage();
        Login login = new Login();
        CityComponent cityComponent = new CityComponent();

        login
                .loginByAPI(App.config.customerPhoneNumberAPI(), App.config.userPassword());
        basePage
                .openPage("/breeders");
        cityComponent
                .chooseCity("Санкт-Петербург");
        basePage
                .chooseRadio("Кошки")
                .selectValueFromDropdown("Выберите породу", "Абиссинская")
                .clickOnButton("Показать")
                .checkResult("Продавец Автотест");
    }
}