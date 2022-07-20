package com.petyes.tests;

import com.petyes.config.App;
import com.petyes.domain.ItemsForLogin;
import com.petyes.pages.BasePage;
import com.petyes.pages.BreedersPage;
import com.petyes.api.Login;
import com.petyes.pages.components.CityComponent;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;


@Feature("Заводчики")
public class BreederListTests extends TestBase {

    @Test
    @DisplayName("Переход в создание запроса из раздела Заводчики")
    void createRequestTest() {
        BasePage basePage = new BasePage();
        Login login = new Login();

        String token = login.loginByAPI(App.config.customerPhoneNumber(), App.config.userPassword());

        login
                .setCookie(token);
        basePage
                .openPage("/breeders")
                .clickOnButton("Найти питомца")
                .checkHeader(2, "Создание запроса на подбор животного");
    }

    @Test
    @DisplayName("Открытие страницы заводчика")
    void openBreederTest() {
        BasePage basePage = new BasePage();
        Login login = new Login();
        BreedersPage breedersPage = new BreedersPage();

        String token = login.loginByAPI(App.config.customerPhoneNumber(), App.config.userPassword());

        login
                .setCookie(token);
        basePage
                .openPage("/breeders");
        breedersPage
                .openBreederPage("Продавец Автотест");
        basePage
                .checkHeader(5,"Привет, я Продавец Автотест");
    }

    @Test
    @DisplayName("Оставить отзыв о зоводчике")
    void breederReviewTest() {
        BasePage basePage = new BasePage();
        Login login = new Login();
        BreedersPage breedersPage = new BreedersPage();

        String token = login.loginByAPI(App.config.customerPhoneNumber(), App.config.userPassword());

        login
                .setCookie(token);
        basePage
                .openPage("/breeders");
        breedersPage
                .openRewiewPopup("Продавец Автотест")
                .clickRatingButton(3)
                .setComment("тест");
        basePage
                .clickOnButton("Отправить")
                .clickOnButton("Хорошо");
    }

    @EnumSource(ItemsForLogin.class)
    @ParameterizedTest(name = "Работа фильтров: {0}")
    @Tag("smoke")
    void filterBreedersTest(ItemsForLogin items) {
        BasePage basePage = new BasePage();
        Login login = new Login();
        CityComponent cityComponent = new CityComponent();

        String token = login.loginByAPI(items.getPhoneNumber(), App.config.userPassword());

        login
                .setCookie(token);
        basePage
                .openPage("/breeders");
        cityComponent
                .chooseCity("Санкт-Петербург");
        basePage
                .chooseRadio("Кошки")
                .selectValueInDropdownInFilter("Выберите породу", "Абиссинская")
                .clickOnButton("Показать")
                .checkResult("Продавец Автотест");
    }
}