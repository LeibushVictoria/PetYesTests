package com.petyes.tests;

import com.petyes.domain.DataBuilder;
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
    @Tag("regression")
    @DisplayName("Переход в создание запроса из раздела Заводчики")
    void createRequestTest() {
        BasePage basePage = new BasePage();
        Login login = new Login();

        login
                .setCookie(DataBuilder.customerToken);
        basePage
                .openPage("/breeders")
                .clickOnButton("Найти питомца")
                .checkHeader(2, "Создание запроса на подбор животного");
    }

    @Test
    @Tag("regression")
    @DisplayName("Открытие страницы заводчика")
    void openBreederTest() {
        BasePage basePage = new BasePage();
        Login login = new Login();
        BreedersPage breedersPage = new BreedersPage();

        login
                .setCookie(DataBuilder.customerToken);
        basePage
                .openPage("/breeders")
                .enterSearchValue("Продавец Автотест");
        breedersPage
                .openBreederPage("Продавец Автотест");
        basePage
                .checkHeader(5,"Здравствуйте! Я, Продавец Автотест");
    }

    @Test
    @Tag("regression")
    @DisplayName("Оставить отзыв о зоводчике")
    void breederReviewTest() {
        BasePage basePage = new BasePage();
        Login login = new Login();
        BreedersPage breedersPage = new BreedersPage();

        login
                .setCookie(DataBuilder.customerToken);
        basePage
                .openPage("/breeders")
                .enterSearchValue("Продавец Автотест");
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
    @Tag("regression")
    @Tag("smoke")
    void filterBreedersTest(ItemsForLogin items) {
        BasePage basePage = new BasePage();
        Login login = new Login();
        CityComponent cityComponent = new CityComponent();

        login
                .setCookie(items.getToken());
        basePage
                .openPage("/breeders");
        cityComponent
                .chooseCity("Санкт-Петербург");
        basePage
                .chooseRadio("Кошки")
                .selectValueInDropdownInFilter("Выберите породу", "Абиссинская")
                .clickOnSubmitButton()
                .checkLinkById(DataBuilder.breeder_id);
    }
}