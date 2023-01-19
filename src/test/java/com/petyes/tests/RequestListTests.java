package com.petyes.tests;

import com.petyes.api.Login;
import com.petyes.domain.DataBuilder;
import com.petyes.pages.BasePage;
import com.petyes.pages.RequestPage;
import com.petyes.pages.components.CityComponent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class RequestListTests extends TestBase {

    @Test
    @Tag("smoke")
    @Tag("regression")
    @DisplayName("Работа фильтров")
    void filterRequestsTest() {
        BasePage basePage = new BasePage();
        Login login = new Login();
        CityComponent cityComponent = new CityComponent();

        login
                .setCookie(DataBuilder.breederToken);
        basePage
                .openPage("/search");
        cityComponent
                .chooseCity("Санкт-Петербург");
        basePage
                .chooseRadio("Кошки")
                .selectValueInDropdownInFilter("Выберите породу", "Абиссинская")
                .chooseRadio("Куплю")
                .enterValueByKeys(1, "9999")
                .enterValueByKeys(2, "10002")
                .chooseRadio("Самец")
                .chooseCheckbox("До 6 месяцев")
                .clickOnSubmitButton()
                .checkLinkById(DataBuilder.request_id);
    }

    @Test
    @Tag("regression")
    @DisplayName("Просмотр запроса")
    void viewRequestTest() {
        BasePage basePage = new BasePage();
        Login login = new Login();

        login
                .setCookie(DataBuilder.breederToken);
        basePage
                .openPage("/buy/" + DataBuilder.request_id)
                .checkBlockDisplay("Требуемые характеристики")
                .checkBlockDisplay("Откликнуться")
                .checkBlockDisplay("Покупатель Автотест");
    }

    @Test
    @Tag("regression")
    @DisplayName("Отклик на запрос")
    void requestApplicationTest() {
        BasePage basePage = new BasePage();
        Login login = new Login();
        RequestPage requestPage = new RequestPage();

        login
                .setCookie(DataBuilder.breederToken);
        basePage
                .openPage("/buy/" + DataBuilder.request_id)
                .clickOnButton("Откликнуться");
        requestPage
                .choosePetToResponse(DataBuilder.petForSale_id);
        basePage
                .clickOnButton("Предложить (1)")
                .checkGreenMessage()
                .checkBlockDisplay("Отменить отклик")
                .clickOnButton("Отменить отклик")
                .checkGreenMessage();
    }
}
