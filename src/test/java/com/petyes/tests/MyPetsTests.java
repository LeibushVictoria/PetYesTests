package com.petyes.tests;

import com.petyes.api.Login;
import com.petyes.api.Pet;
import com.petyes.config.App;
import com.petyes.pages.BasePage;
import com.petyes.pages.MyPetsPage;
import com.petyes.pages.components.CalendarComponent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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

        String nickname = "autoTestMyPetsCat";
        Date birth = calendarComponent.getOtherDate(-20);
        int pet_id = pet.createPetByAPI(false, 13, nickname, birth, 0, 1007, 1,597);

        String token = login.loginByAPI(App.config.breederPhoneNumber(), App.config.userPassword());

        login
                .setCookie(token);
        basePage
                .openPage("/mypets");
        myPetsPage
                .clickOnPet(nickname);
        basePage
                .checkBlockDisplay(nickname);

        pet.deletePetByAPI(pet_id);
    }

    @Test
    @DisplayName("Открыть родословную питомца")
    void openGenTreeTest() {
        Pet pet = new Pet();
        BasePage basePage = new BasePage();
        Login login = new Login();
        CalendarComponent calendarComponent = new CalendarComponent();
        MyPetsPage myPetsPage = new MyPetsPage();

        String nickname = "autoTestMyPetsCat";
        Date birth = calendarComponent.getOtherDate(-20);
        int pet_id = pet.createPetByAPI(false, 13, nickname, birth, 0, 1007, 1,597);

        String token = login.loginByAPI(App.config.breederPhoneNumber(), App.config.userPassword());

        login
                .setCookie(token);
        basePage
                .openPage("/mypets");
        myPetsPage
                .clickOnGenTree(pet_id)
                .checkGenTree(nickname);

        pet.deletePetByAPI(pet_id);
    }

    @Test
    @DisplayName("Открыть редактирование питомца")
    void openEditPetTest() {
        Pet pet = new Pet();
        BasePage basePage = new BasePage();
        Login login = new Login();
        CalendarComponent calendarComponent = new CalendarComponent();
        MyPetsPage myPetsPage = new MyPetsPage();

        Date birth = calendarComponent.getOtherDate(-20);
        int pet_id = pet.createPetByAPI(false, 13, "autoTestMyPetsCat", birth, 0, 1007, 1,597);

        String token = login.loginByAPI(App.config.breederPhoneNumber(), App.config.userPassword());

        login
                .setCookie(token);
        basePage
                .openPage("/mypets");
        myPetsPage
                .clickOnEdit(pet_id);
        basePage
                .checkHeader(2, "Редактирование карточки питомца");

        pet.deletePetByAPI(pet_id);
    }

    @Test
    @DisplayName("Открыть продажу питомца (отдать даром)")
    void openFreeSalePetTest() {
        Pet pet = new Pet();
        BasePage basePage = new BasePage();
        Login login = new Login();
        CalendarComponent calendarComponent = new CalendarComponent();
        MyPetsPage myPetsPage = new MyPetsPage();

        Date birth = calendarComponent.getOtherDate(-20);
        int pet_id = pet.createPetByAPI(false, 13, "autoTestMyPetsCat", birth, 0, 1007, 1,597);

        String token = login.loginByAPI(App.config.breederPhoneNumber(), App.config.userPassword());

        login
                .setCookie(token);
        basePage
                .openPage("/mypets");
        myPetsPage
                .clickOnFreeSale(pet_id)
                .checkSale("Отдам бесплатно в хорошие руки");

        pet.deletePetByAPI(pet_id);
    }

    @Test
    @DisplayName("Открыть продажу питомца")
    void openSalePetTest() {
        Pet pet = new Pet();
        BasePage basePage = new BasePage();
        Login login = new Login();
        CalendarComponent calendarComponent = new CalendarComponent();
        MyPetsPage myPetsPage = new MyPetsPage();

        Date birth = calendarComponent.getOtherDate(-20);
        int pet_id = pet.createPetByAPI(false, 13, "autoTestMyPetsCat", birth, 0, 1007, 1,597);

        String token = login.loginByAPI(App.config.breederPhoneNumber(), App.config.userPassword());

        login
                .setCookie(token);
        basePage
                .openPage("/mypets");
        myPetsPage
                .clickOnSale(pet_id)
                .checkSale("Продажа сейчас");

        pet.deletePetByAPI(pet_id);
    }
}
