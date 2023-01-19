package com.petyes.tests;

import com.petyes.api.Login;
import com.petyes.domain.DataBuilder;
import com.petyes.pages.BasePage;
import com.petyes.pages.MyPetsPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class MyPetsTests extends TestBase {

    @Test
    @Tag("regression")
    @DisplayName("Открыть страницу питомца")
    void openPetTest() {
        BasePage basePage = new BasePage();
        Login login = new Login();
        MyPetsPage myPetsPage = new MyPetsPage();

        login
                .setCookie(DataBuilder.breederToken);
        basePage
                .openPage("/mypets");
        myPetsPage
                .clickOnPet(DataBuilder.petName);
        basePage
                .checkBlockDisplay(DataBuilder.petName);
    }

    @Test
    @Tag("regression")
    @DisplayName("Открыть родословную питомца")
    void openGenTreeTest() {
        BasePage basePage = new BasePage();
        Login login = new Login();
        MyPetsPage myPetsPage = new MyPetsPage();

        login
                .setCookie(DataBuilder.breederToken);
        basePage
                .openPage("/mypets");
        myPetsPage
                .clickOnGenTree(DataBuilder.pet_id)
                .checkGenTree(DataBuilder.petName);
    }

    @Test
    @Tag("smoke")
    @Tag("regression")
    @DisplayName("Открыть редактирование питомца")
    void openEditPetTest() {
        BasePage basePage = new BasePage();
        Login login = new Login();
        MyPetsPage myPetsPage = new MyPetsPage();

        login
                .setCookie(DataBuilder.breederToken);
        basePage
                .openPage("/mypets");
        myPetsPage
                .clickOnEdit(DataBuilder.pet_id);
        basePage
                .checkHeader(2, "Редактирование карточки питомца");
    }

    @Test
    @Tag("regression")
    @DisplayName("Открыть продажу питомца (отдать даром)")
    void openFreeSalePetTest() {
        BasePage basePage = new BasePage();
        Login login = new Login();
        MyPetsPage myPetsPage = new MyPetsPage();

        login
                .setCookie(DataBuilder.breederToken);
        basePage
                .openPage("/mypets");
        myPetsPage
                .clickOnFreeSale(DataBuilder.pet_id)
                .checkSale("Отдам бесплатно в хорошие руки");
    }

    @Test
    @Tag("regression")
    @DisplayName("Открыть продажу питомца")
    void openSalePetTest() {
        BasePage basePage = new BasePage();
        Login login = new Login();
        MyPetsPage myPetsPage = new MyPetsPage();

        login
                .setCookie(DataBuilder.breederToken);
        basePage
                .openPage("/mypets");
        myPetsPage
                .clickOnSale(DataBuilder.pet_id)
                .checkSale("Продажа сейчас");
    }
}