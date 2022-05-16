package com.petyes.tests;

import com.github.javafaker.Faker;
import com.petyes.api.Login;
import com.petyes.config.App;
import com.petyes.pages.AddPetPage;
import com.petyes.pages.BasePage;
import com.petyes.pages.components.CalendarComponent;
import io.qameta.allure.AllureId;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

@Feature("Питомец")
public class AddPetTests extends TestBase {

    @Test
    @AllureId("5704")
    @DisplayName("Создание питомца")
    void addPetTest() {
        BasePage basePage = new BasePage();
        Login login = new Login();
        AddPetPage addPetPage = new AddPetPage();
        CalendarComponent calendarComponent = new CalendarComponent();
        Faker faker = new Faker();

        String aboutPet = faker.chuckNorris().fact();

        Date pastDate = calendarComponent.getPastDate(7);
        SimpleDateFormat formater = new SimpleDateFormat("dd.MM.yyyy");
        String date = formater.format(pastDate);

        login
                .loginByAPI(App.config.breederPhoneNumberAPI(), App.config.userPassword());
        basePage
                .openPage("/pet/new")
                .selectValueFromDropdown("Выберите вид животного", "Кошки");
        addPetPage
                .choosePetBreed("Выберите породу животного", "Абиссинская")
                .typeValueInInput("nickname", "autoTest");
        basePage
                .enterValueInTextarea(aboutPet);
        calendarComponent
                .chooseDateInCalendar("10");
        basePage
                .selectValueFromDropdown("Выберите окрас", "Белый")
                .selectValueFromDropdown("Выберите цвет глаз", "Голубые")
                .chooseCheckbox("Кастрат");
                //.clickOnButton("Продолжить заполнение");
        addPetPage
                .uploadAvatarFile(0, "test.jpg")
                .clickOnSideBarTab("Регистрационные данные")
                .typeValueInInput("passport_num", "123")
                .typeValueInInput("passport_from", "абв");
        calendarComponent
                .enterDate("passport_date", date);
        addPetPage
                .typeValueInInput("passport_name", "autoTest_passport")
                .uploadFile(1, "pet_passport.jpg");
        basePage
                .chooseRadio("Да");
        addPetPage
                .typeValueInInput("chip_num", "456");
        calendarComponent
                .enterDate("chip_date", date);
        addPetPage
                .typeValueInInput("chip_place", "где");
        basePage
                .selectValueFromDropdown("Выберите орган сертификации", "CFA");
        addPetPage
                .typeValueInInput("association[0].identifier", "789")
                .typeValueInInput("association[0].link", "https://www.test.ru")
                .uploadFile(2, "pet_certificate.jpg");;
        //basePage
                //.clickOnButton("Продолжить заполнение")
        addPetPage
                .clickOnSideBarTab("Фотографии")
                .uploadPhoto(3, "test.jpg");
        basePage
                .clickOnButton("Создать");
        addPetPage
                .checkPetCreated("autoTest");
    }
}