package com.petyes.tests;

import com.petyes.api.Login;
import com.petyes.api.Pet;
import com.petyes.api.Sale;
import com.petyes.config.AuthConfig;
import com.petyes.pages.PetPage;
import com.petyes.pages.BasePage;
import com.petyes.pages.components.CalendarComponent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SalePetTests extends TestBase{

    @Test
    @Tag("smoke")
    @Tag("regression")
    @DisplayName("Продать существующего питомца")
    void saleExistingPetTest() {
        Pet pet = new Pet();
        Sale sale = new Sale();
        BasePage basePage = new BasePage();
        Login login = new Login();
        CalendarComponent calendarComponent = new CalendarComponent();

        Date birth = calendarComponent.getOtherDate(-20);
        int pet_id = pet.createPetByAPI(false, "autoTestSaleCat", birth, 0, 1);

        login
                .setCookie(AuthConfig.breederToken);
        basePage
                .openPage("/sale/add/" + pet_id)
                .enterNumberInInput("not_for_breeding_price", "10000")
                .clickOnButton("Опубликовать")
                .checkGreenMessage()
                .checkBlockDisplay("autoTestSaleCat, Абиссинская");

        sale.cancelPetSaleByAPI(pet_id);
        pet.deletePetByAPI(pet_id);
    }

    @Test
    @Tag("smoke")
    @Tag("regression")
    @DisplayName("Отдать даром питомца без породы")
    void freeSaleExistingPetTest() {
        Pet pet = new Pet();
        Sale sale = new Sale();
        BasePage basePage = new BasePage();
        Login login = new Login();
        CalendarComponent calendarComponent = new CalendarComponent();
        PetPage petPage = new PetPage();

        Date dateBirth = calendarComponent.getOtherDate(-20);
        SimpleDateFormat formater = new SimpleDateFormat("dd.MM.yyyy");
        String birth = formater.format(dateBirth);

        String petType = "Кошки";
        String nickname = "autoTestFreeSaleCat";

        login
                .setCookie(AuthConfig.breederToken);
        basePage
                .openPage("/sale-pets-list")
                .clickOnButton("Новое животное");
        petPage
                .selectPetType("Выберите вид животного", petType);
        basePage
                .enterValueInInput("nickname", nickname);
        calendarComponent
                .enterDate("birth", birth);
        basePage
                .clickOnSubmitButton()
                .checkHeader(2, "Подача объявления");
        int pet_id = basePage.getIdFromUrl();
        basePage
                .clickOnButton("Опубликовать")
                .checkGreenMessage()
                .checkBlockDisplay(nickname + ", Без породы");

        sale.cancelPetSaleByAPI(pet_id);
        pet.deletePetByAPI(pet_id);
    }

    @Test
    @Tag("smoke")
    @Tag("regression")
    @DisplayName("Редактирование объявление (комментарий)")
    void editPetSaleTest() {
        BasePage basePage = new BasePage();
        Pet pet = new Pet();
        Sale sale = new Sale();
        CalendarComponent calendarComponent = new CalendarComponent();
        Login login = new Login();

        Date birth = calendarComponent.getOtherDate(-20);
        int pet_id = pet.createPetByAPI(false, "autoTestSaleCat", birth, 0, 1);
        int sale_id = sale.salePetByAPI(false, false, true, 10000, pet_id);

        String comment = "Автотестовый комментарий";

        login
                .setCookie(AuthConfig.breederToken);
        basePage
                .openPage("/sale/" + sale_id)
                .clickOnButton("Редактировать")
                .enterValueInSingleTextarea(comment)
                .clickOnButton("Сохранить")
                .checkGreenMessage()
                .checkBlockDisplay(comment);

        sale.cancelPetSaleByAPI(pet_id);
        pet.deletePetByAPI(pet_id);
    }

    @Test
    @Tag("regression")
    @DisplayName("Снятие объявления с продажи")
    void cancelPetSaleTest() {
        BasePage basePage = new BasePage();
        Pet pet = new Pet();
        Sale sale = new Sale();
        CalendarComponent calendarComponent = new CalendarComponent();
        Login login = new Login();

        Date birth = calendarComponent.getOtherDate(-20);
        int pet_id = pet.createPetByAPI(false, "autoTestSaleCat", birth, 0, 1);
        int sale_id = sale.salePetByAPI(false, false, true, 10000, pet_id);

        login
                .setCookie(AuthConfig.breederToken);
        basePage
                .openPage("/sale/" + sale_id)
                .clickOnButton("Снять с продажи")
                .chooseRadio("Другое")
                .enterValueInSingleTextarea("Автотестовая причина завершения")
                .clickOnButton("Продолжить")
                .checkGreenMessage();

        pet.deletePetByAPI(pet_id);
    }
}