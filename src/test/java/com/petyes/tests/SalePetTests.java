package com.petyes.tests;

import com.petyes.api.Login;
import com.petyes.api.Pet;
import com.petyes.config.App;
import com.petyes.pages.PetPage;
import com.petyes.pages.BasePage;
import com.petyes.pages.SalePetPage;
import com.petyes.pages.components.CalendarComponent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SalePetTests extends TestBase{

    @Test
    @DisplayName("Продать существующего питомца")
    void saleExistingPetTest() {
        Pet pet = new Pet();
        BasePage basePage = new BasePage();
        Login login = new Login();
        CalendarComponent calendarComponent = new CalendarComponent();

        Date birth = calendarComponent.getOtherDate(-20);
        int pet_id = pet.createPetByAPI(false, 13, "autoTestSaleCat", birth, 0, 1007, 1,597);

        String token = login.loginByAPI(App.config.breederPhoneNumberAPI(), App.config.userPassword());

        login
                .setCookie(token);
        basePage
                .openPage("/sale/add/" + pet_id)
                .enterNumberInInput("not_for_breeding_price", "10000")
                .clickOnButton("Опубликовать")
                .checkGreenMessage()
                .checkBlockDisplay("autoTestSaleCat, Абиссинская");

        pet.cancelPetSaleByAPI(pet_id);
        pet.deletePetByAPI(pet_id);
    }

    @Test
    @DisplayName("Отдать даром питомца без породы")
    void freeSaleExistingPetTest() {
        Pet pet = new Pet();
        BasePage basePage = new BasePage();
        Login login = new Login();
        CalendarComponent calendarComponent = new CalendarComponent();
        PetPage petPage = new PetPage();
        SalePetPage salePetPage = new SalePetPage();

        Date dateBirth = calendarComponent.getOtherDate(-20);
        SimpleDateFormat formater = new SimpleDateFormat("dd.MM.yyyy");
        String birth = formater.format(dateBirth);

        String petType = "Кошки";
        String nickname = "autoTestFreeSaleCat";

        String token = login.loginByAPI(App.config.breederPhoneNumberAPI(), App.config.userPassword());

        login
                .setCookie(token);
        basePage
                .openPage("/sale-pets-list");
        petPage
                .selectPetType("Выберите вид животного", petType);
        basePage
                .enterValueInInput("nickname", nickname);
        calendarComponent
                .enterDate("birth", birth);
        salePetPage
                .clickOnSaleButton("Продать");

        int pet_id = basePage.getIdFromUrl();

        basePage
                .clickOnButton("Опубликовать")
                .checkGreenMessage()
                .checkBlockDisplay("autoTestFreeSaleCat, Без породы");

        pet.cancelPetSaleByAPI(pet_id);
        pet.deletePetByAPI(pet_id);
    }

    @Test
    @DisplayName("Редактирование объявление (цена)")
    void editPetSaleTest() {
        BasePage basePage = new BasePage();
        Pet pet = new Pet();
        CalendarComponent calendarComponent = new CalendarComponent();
        Login login = new Login();

        Date birth = calendarComponent.getOtherDate(-20);
        int pet_id = pet.createPetByAPI(false, 13, "autoTestSaleCat", birth, 0, 1007, 1,597);
        int sell_id = pet.salePetByAPI(false, false, true, 10000, pet_id);

        String price= "99 999";

        String token = login.loginByAPI(App.config.breederPhoneNumberAPI(), App.config.userPassword());

        login
                .setCookie(token);
        basePage
                .openPage("/sale/" + sell_id)
                .clickOnButton("Редактировать")
                .enterValueByKeys(1, price)
                .clickOnButton("Сохранить")
                .checkGreenMessage()
                .checkBlockDisplay(price + " ₽");

        pet.cancelPetSaleByAPI(pet_id);
        pet.deletePetByAPI(pet_id);
    }

    @Test
    @DisplayName("Снятие объявления с продажи")
    void cancelPetSaleTest() {
        BasePage basePage = new BasePage();
        Pet pet = new Pet();
        CalendarComponent calendarComponent = new CalendarComponent();
        Login login = new Login();
        SalePetPage salePetPage = new SalePetPage();

        Date birth = calendarComponent.getOtherDate(-20);
        int pet_id = pet.createPetByAPI(false, 13, "autoTestSaleCat", birth, 0, 1007, 1,597);
        int sell_id = pet.salePetByAPI(false, false, true, 10000, pet_id);

        String token = login.loginByAPI(App.config.breederPhoneNumberAPI(), App.config.userPassword());

        login
                .setCookie(token);
        basePage
                .openPage("/sale/" + sell_id)
                .clickOnButton("Снять с продажи")
                .chooseRadio("Другое");
        salePetPage
                .enterReason("Автотестовая причина завершения");
        basePage
                .clickOnButton("Продолжить")
                .checkGreenMessage();

        pet.deletePetByAPI(pet_id);
    }
}
