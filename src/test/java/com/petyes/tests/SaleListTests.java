package com.petyes.tests;

import com.petyes.api.Login;
import com.petyes.domain.DataBuilder;
import com.petyes.domain.ItemsForLogin;
import com.petyes.pages.BasePage;
import com.petyes.pages.components.CityComponent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

public class SaleListTests extends TestBase {

    @EnumSource(ItemsForLogin.class)
    @ParameterizedTest(name = "Работа фильтров: {0}")
    @Tag("regression")
    void filterSalesTest(ItemsForLogin items) {
        BasePage basePage = new BasePage();
        Login login = new Login();
        CityComponent cityComponent = new CityComponent();

        login
                .setCookie(items.getToken());
        basePage
                .openPage("/for-sale");
        cityComponent
                .chooseCity("Санкт-Петербург");
        basePage
                .chooseRadio("Кошки")
                .selectValueInDropdownInFilter("Выберите породу", "Абиссинская")
                .chooseRadio("Куплю")
                .enterValueByKeys(0, "9999")
                .enterValueByKeys(1, "10002")
                .chooseRadio("Самец")
                .chooseCheckbox("До 6 месяцев")
                .chooseCheckbox("Кастрация/стерилизация")
                .clickOnSubmitButton()
                .checkLinkById(DataBuilder.sale_id);
    }

    @Test
    @Tag("regression")
    @DisplayName("Просмотр объявления")
    void viewSaleTest() {
        BasePage basePage = new BasePage();
        Login login = new Login();

        login
                .setCookie(DataBuilder.customerToken);
        basePage
                .openPage("/for-sale/cat/" + DataBuilder.sale_id)
                .checkBlockDisplay("Кошка Абиссинская 20 дней")
                .checkBlockDisplay("Продавец Автотест");
    }

    @Test
    @Tag("regression")
    @DisplayName("Переход в создание запроса из раздела Объявления")
    void createRequestTest() {
        BasePage basePage = new BasePage();
        Login login = new Login();

        login
                .setCookie(DataBuilder.customerToken);
        basePage
                .openPage("/for-sale")
                .clickOnButton("Оставить заявку")
                .checkHeader(2, "Создание запроса на подбор животного");
    }
}
