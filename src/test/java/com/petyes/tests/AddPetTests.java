package com.petyes.tests;

import com.petyes.config.App;
import com.petyes.pages.AddPetPage;
import com.petyes.pages.LoginPage;
import com.petyes.pages.components.CalendarComponent;
import io.qameta.allure.AllureId;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Feature("Питомец")
public class AddPetTests extends TestBase {

    @Test
    @AllureId("5704")
    @DisplayName("Создание питомца")
    void addPetTest() {
        LoginPage loginPage = new LoginPage();
        AddPetPage addPetPage = new AddPetPage();
        CalendarComponent calendarComponent = new CalendarComponent();

        loginPage
                .openLoginPage()
                .fillLoginForm(App.config.breederPhoneNumber(), App.config.userPassword())
                .clickSubmitButton()
                .checkLogin("Находите хозяев для Ваших щенков и котят");
        addPetPage
                .openAddPetPage()
                .choosePetType("Кошки")
                .choosePetBreed(2)
                .typePetName("autoTest");
        calendarComponent
                .setDate("10");
        addPetPage
                .clickSubmitButton()
                .checkPetCreated("autoTest");
    }
}