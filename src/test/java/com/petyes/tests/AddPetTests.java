package com.petyes.tests;

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
        SimpleDateFormat formater = new SimpleDateFormat("dd.MM.yyyy");

        Date pastDate = calendarComponent.getPastDate(7);
        String date = formater.format(pastDate);

        login
                .loginByAPI(App.config.breederPhoneNumberAPI(), App.config.userPassword());
        basePage
                .openPage("/pet/new")
                .selectValueFromDropdown("Вид животного", "Кошки");
        addPetPage
                .choosePetBreed("Выберите породу животного", 2)
                .typeValueInInput("nickname", "autoTest");
        basePage
                .enterValueInTextarea("Описание питомца", "Автотестовое описание питомца");
        calendarComponent
                .enterDate("birth", date);
        basePage
                .selectValueFromDropdown("Окрас", "Белый")
                .selectValueFromDropdown("Цвет глаз", "Голубые")
                .chooseCheckbox("Кастрат");
        addPetPage
                .uploadAvatarFile(0, "pet_avatar.jpg")
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
                .selectValueFromDropdown("Орган сертификации", "CFA");
        addPetPage
                .typeValueInInput("association[0].identifier", "789")
                .typeValueInInput("association[0].link", "https://www.association.ru")
                .uploadFile(2, "pet_certificate.jpg")
                .clickOnSideBarTab("Фотографии")
                .uploadPhoto(3, "pet_photo.jpg")
                .clickOnSideBarTab("Выставки и награды");
        basePage
                .enterValueInDropdown("Название выставки", "Автотестовая выставка");
        calendarComponent
                .enterDate("exposition[0].date_start", date)
                .enterDate("exposition[0].date_end", date);
        addPetPage
                .typeValueInInput("exposition[0].link", "https://www.exposition.ru");
        basePage
                .enterValueInTextarea("Описание выставки", "Автотестовое описание выставки")
                .clickOnButton("Добавить достижение")
                .enterValueInDropdown("Вид награды", "Автотестовая награда")
                .enterValueInInput("Номинация", "Автотестовая номинация");
        addPetPage
                .uploadFile(4, "pet_exposition.jpg")
                .clickOnSideBarTab("Рацион питания");
        basePage
                .enterValueInDropdown("Наименование корма", "Автотестовый корм");
        calendarComponent
                .enterFoodDate("food[0].date_from", date)
                .enterDate("food[0].date_to", date);
        basePage
                .enterValueInTextarea("Описание рациона", "Автотестовое описание рациона");
        addPetPage
                .clickOnSideBarTab("Вакцинация");
        basePage
                .enterValueInDropdown("Название вакцины", "Автотестовая вакцина");
        calendarComponent
                .enterDate("vaccines[0].date_from", date);
        basePage
                .selectValueFromDropdown("Повторная вакцинация", "Не повторять")
                .enterValueInTextarea("Описание вакцины", "Автотестовое описание вакцины");
        addPetPage
                .clickOnSideBarTab("Лечение");
        basePage
                .enterValueInInput("Диагноз", "Автотестовый диагноз");
        calendarComponent
                .enterDate("cure[0].date_from", date)
                .enterDate("cure[0].date_from_diagnosis", date);
        basePage
                .enterValueInTextarea("Назначенное лечение", "Автотестовое лечение");
        addPetPage
                .uploadFile(5, "pet_cure.jpg")
                .clickOnSideBarTab("Медицинская карта");
        basePage
                .selectValueFromDropdown("Группа крови", "Группа A")
                .enterValueInDropdown("Генетические заболевания", "Автотестовое заболевание");
        calendarComponent
                .enterDate("medical_card.pet_measurements[0].date", date);
        basePage
                .enterNumberInInput("medical_card.pet_measurements[0].weight", "3")
                .enterNumberInInput("medical_card.pet_measurements[0].height", "20");
        addPetPage
                .clickOnSideBarTab("Вязка");
        basePage
                .clickOnButton("Создать")
                .checkBlockDisplay("autoTest");
    }
}