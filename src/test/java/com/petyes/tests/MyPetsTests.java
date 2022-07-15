package com.petyes.tests;

import com.petyes.api.Login;
import com.petyes.api.Pet;
import com.petyes.config.App;
import com.petyes.pages.BasePage;
import com.petyes.pages.MyPetsPage;
import com.petyes.pages.components.CalendarComponent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyPetsTests extends TestBase {

    @Test
    @DisplayName("Открыть страницу питомца")
    void openPetTest() {
        Pet pet = new Pet();
        BasePage basePage = new BasePage();
        Login login = new Login();
        CalendarComponent calendarComponent = new CalendarComponent();
        MyPetsPage myPetsPage = new MyPetsPage();

        Date dateBirth = calendarComponent.getOtherDate(-20);
        SimpleDateFormat formater = new SimpleDateFormat("dd.MM.yyyy");
        String birth = formater.format(dateBirth);

        String token = login.loginByAPI(App.config.breederPhoneNumberAPI(), App.config.userPassword());

        String nickname = "autoTestMyPetsCat";

        int pet_id = pet.createPetByAPI(token, false, 13, nickname, birth, 0, 1007, 1,597);

        login
                .setCookie(token);
        basePage
                .openPage("/mypets");
        myPetsPage
                .clickOnPet(nickname);
        basePage
                .checkBlockDisplay(nickname);

        pet.deletePetByAPI(token, pet_id);
    }

    @Test
    @DisplayName("Открыть родословную питомца")
    void openGenTreeTest() {
        Pet pet = new Pet();
        BasePage basePage = new BasePage();
        Login login = new Login();
        CalendarComponent calendarComponent = new CalendarComponent();
        MyPetsPage myPetsPage = new MyPetsPage();

        Date dateBirth = calendarComponent.getOtherDate(-20);
        SimpleDateFormat formater = new SimpleDateFormat("dd.MM.yyyy");
        String birth = formater.format(dateBirth);

        String token = login.loginByAPI(App.config.breederPhoneNumberAPI(), App.config.userPassword());

        String nickname = "autoTestMyPetsCat";

        int pet_id = pet.createPetByAPI(token, false, 13, nickname, birth, 0, 1007, 1,597);

        login
                .setCookie(token);
        basePage
                .openPage("/mypets");
        myPetsPage
                .clickOnGenTree(pet_id)
                .checkGenTree(nickname);

        pet.deletePetByAPI(token, pet_id);
    }

    @Test
    @DisplayName("Открыть редактирование питомца")
    void openEditPetTest() {
        Pet pet = new Pet();
        BasePage basePage = new BasePage();
        Login login = new Login();
        CalendarComponent calendarComponent = new CalendarComponent();
        MyPetsPage myPetsPage = new MyPetsPage();

        Date dateBirth = calendarComponent.getOtherDate(-20);
        SimpleDateFormat formater = new SimpleDateFormat("dd.MM.yyyy");
        String birth = formater.format(dateBirth);

        String token = login.loginByAPI(App.config.breederPhoneNumberAPI(), App.config.userPassword());

        int pet_id = pet.createPetByAPI(token, false, 13, "autoTestMyPetsCat", birth, 0, 1007, 1,597);

        login
                .setCookie(token);
        basePage
                .openPage("/mypets");
        myPetsPage
                .clickOnEdit(pet_id);
        basePage
                .checkHeader(2, "Редактирование карточки питомца");

        pet.deletePetByAPI(token, pet_id);
    }

    @Test
    @DisplayName("Открыть продажу питомца (отдать даром)")
    void openFreeSalePetTest() {
        Pet pet = new Pet();
        BasePage basePage = new BasePage();
        Login login = new Login();
        CalendarComponent calendarComponent = new CalendarComponent();
        MyPetsPage myPetsPage = new MyPetsPage();

        Date dateBirth = calendarComponent.getOtherDate(-20);
        SimpleDateFormat formater = new SimpleDateFormat("dd.MM.yyyy");
        String birth = formater.format(dateBirth);

        String token = login.loginByAPI(App.config.breederPhoneNumberAPI(), App.config.userPassword());

        int pet_id = pet.createPetByAPI(token, false, 13, "autoTestMyPetsCat", birth, 0, 1007, 1,597);

        login
                .setCookie(token);
        basePage
                .openPage("/mypets");
        myPetsPage
                .clickOnFreeSale(pet_id)
                .checkSale("Отдам бесплатно в хорошие руки");

        pet.deletePetByAPI(token, pet_id);
    }

    @Test
    @DisplayName("Открыть продажу питомца")
    void openSalePetTest() {
        Pet pet = new Pet();
        BasePage basePage = new BasePage();
        Login login = new Login();
        CalendarComponent calendarComponent = new CalendarComponent();
        MyPetsPage myPetsPage = new MyPetsPage();

        Date dateBirth = calendarComponent.getOtherDate(-20);
        SimpleDateFormat formater = new SimpleDateFormat("dd.MM.yyyy");
        String birth = formater.format(dateBirth);

        String token = login.loginByAPI(App.config.breederPhoneNumberAPI(), App.config.userPassword());

        int pet_id = pet.createPetByAPI(token, false, 13, "autoTestMyPetsCat", birth, 0, 1007, 1,597);

        login
                .setCookie(token);
        basePage
                .openPage("/mypets");
        myPetsPage
                .clickOnSale(pet_id)
                .checkSale("Продажа сейчас");

        pet.deletePetByAPI(token, pet_id);
    }
}
