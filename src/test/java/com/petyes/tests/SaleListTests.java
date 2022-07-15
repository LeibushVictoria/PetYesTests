package com.petyes.tests;

import com.petyes.api.Login;
import com.petyes.api.Pet;
import com.petyes.config.App;
import com.petyes.pages.BasePage;
import com.petyes.pages.components.CalendarComponent;
import com.petyes.pages.components.CityComponent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SaleListTests extends TestBase {

    @Test
    @DisplayName("Работа фильтров")
    void filterSalesTest() {
        BasePage basePage = new BasePage();
        Login login = new Login();
        CityComponent cityComponent = new CityComponent();
        CalendarComponent calendarComponent = new CalendarComponent();
        Pet pet = new Pet();

        Date dateBirth = calendarComponent.getOtherDate(-20);
        SimpleDateFormat formaterBirth = new SimpleDateFormat("dd.MM.yyyy");
        String birth = formaterBirth.format(dateBirth);

        String breederToken = login.loginByAPI(App.config.breederPhoneNumberAPI(), App.config.userPassword());

        int pet_id = pet.createPetByAPI(breederToken, false, 13, "autoTestSaleCat", birth, 0, 1004, 1,596);
        int sale_id = pet.salePetByAPI(breederToken, false, false, true, 9998, pet_id);

        String customerToken = login.loginByAPI(App.config.customerPhoneNumberAPI(), App.config.userPassword());

        login
                .setCookie(customerToken);
        basePage
                .openPage("/search?type=1");
        cityComponent
                .chooseCity("Санкт-Петербург");
        basePage
                .chooseRadio("Кошки")
                .selectValueInDropdownInFilter("Выберите породу", "Австралийский мист")
                .chooseRadio("Куплю")
                .enterValueByKeys(0, "9997")
                .enterValueByKeys(1, "9999")
                .chooseRadio("Самец")
                .chooseCheckbox("До 6 месяцев")
                .chooseCheckbox("Кастрация/стерилизация")
                .clickOnSubmitButton()
                .checkLinkById(sale_id);

        pet.cancelPetSaleByAPI(breederToken, pet_id);
        pet.deletePetByAPI(breederToken, pet_id);
    }

    @Test
    @DisplayName("Просмотр объявления")
    void viewSaleTest() {
        BasePage basePage = new BasePage();
        Login login = new Login();
        CalendarComponent calendarComponent = new CalendarComponent();
        Pet pet = new Pet();

        Date dateBirth = calendarComponent.getOtherDate(-20);
        SimpleDateFormat formaterBirth = new SimpleDateFormat("dd.MM.yyyy");
        String birth = formaterBirth.format(dateBirth);

        String breederToken = login.loginByAPI(App.config.breederPhoneNumberAPI(), App.config.userPassword());

        int pet_id = pet.createPetByAPI(breederToken, false, 13, "autoTestSaleCat", birth, 0, 1004, 1,596);
        int sale_id = pet.salePetByAPI(breederToken, false, false, true, 9998, pet_id);

        String customerToken = login.loginByAPI(App.config.customerPhoneNumberAPI(), App.config.userPassword());

        login
                .setCookie(customerToken);
        basePage
                .openPage("/sale/" + sale_id)
                .checkBlockDisplay("autoTestSaleCat, Австралийский мист")
                .checkBlockDisplay("Продавец Автотест");

        pet.cancelPetSaleByAPI(breederToken, pet_id);
        pet.deletePetByAPI(breederToken, pet_id);
    }

    @Test
    @DisplayName("Переход в создание запроса из раздела Объявления")
    void createRequestTest() {
        BasePage basePage = new BasePage();
        Login login = new Login();

        String token = login.loginByAPI(App.config.customerPhoneNumberAPI(), App.config.userPassword());

        login
                .setCookie(token);
        basePage
                .openPage("/search?type=1")
                .clickOnButton("Оставить заявку")
                .checkHeader(2, "Создание запроса на подбор животного");
    }
}