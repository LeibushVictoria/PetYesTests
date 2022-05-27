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
import java.util.Locale;

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
        Locale locale = new Locale("ru");
        SimpleDateFormat birthFormater = new SimpleDateFormat("d" + "-е " + "MMMM yyyy", locale);
        SimpleDateFormat dateFormater = new SimpleDateFormat("d MMMM yyyy", locale);
        SimpleDateFormat chipDateFormater = new SimpleDateFormat("d.MM.yy");

        Date birthDate = calendarComponent.getOtherDate(-20);
        Date pastDate = calendarComponent.getOtherDate(-7);

        String petType = "Кошки";
        String breed = "Абиссинская";
        String nickname = "Автокотик";
        String petDescription = "Автотестовое описание питомца";
        String sex = "Самка";
        String birth = formater.format(birthDate);
        String birthOut = birthFormater.format(birthDate);
        String petColor = "Белый";
        String eyesColor = "Голубые";
        String passportNum = "11 2222";
        String passportFrom = "Котовское МВД";
        String date = formater.format(pastDate);
        String dateOut = dateFormater.format(pastDate);
        String dateChip = chipDateFormater.format(pastDate);
        String passportName = "Автокот";
        String chipNum = "123456";
        String chipPlace = "Холка";
        String associationName = "CFA";
        String associationId = "999999";
        String associationLink = "https://www.association.ru";
        String expositionName = "Автотестовая выставка";
        String expositionLink = "https://www.exposition.ru";
        String expositionDescription = "Автотестовое описание выставки";
        String reward = "Автотестовая награда";
        String nomination = "Автотестовая номинация";
        String foodName = "Автотестовый корм";
        String foodDescription = "Автотестовое описание рациона";
        String vaccinesName = "Автотестовая вакцина";
        String vaccinesRepeat = "Не повторять";
        String vaccinesDescription = "Автотестовое описание вакцины";
        String diagnosis = "Автотестовый диагноз";
        String cure = "Автотестовое лечение";
        String bloodType = "AB";
        String illness = "Автотестовое заболевание";
        String weight = "3";
        String height = "20";

        String avatarFile = "pet_avatar.jpg";
        String passportFile = "pet_passport.jpg";
        String certificateFile = "pet_certificate.jpg";
        String photoFile = "pet_photo.jpg";
        String expositionFile = "pet_exposition.jpg";
        String cureFile = "pet_cure.jpg";

        String token = login.loginByAPI(App.config.breederPhoneNumberAPI(), App.config.userPassword());

        login
                .setCookie(token);
        basePage
                .openPage("/pet/new");
        addPetPage
                .selectPetType("Выберите вид животного", petType)
                .selectBreed(breed);
        basePage
                .enterValueInInput("nickname", nickname)
                .enterValueInTextarea("description", petDescription)
                .chooseRadio(sex);
        calendarComponent
                .enterDate("birth", birth);
        basePage
                .enterValueInDropdown("color", petColor)
                .enterValueInDropdown("eye_color", eyesColor);
        basePage
                .chooseCheckbox("Кастрат");
        addPetPage
                .uploadAvatarFile(0, avatarFile)
                .clickOnSideBarTab("Регистрационные данные");
        basePage
                .enterValueInInput("passport_num", passportNum)
                .enterValueInInput("passport_from", passportFrom);
        calendarComponent
                .enterDate("passport_date", date);
        basePage
                .enterValueInInput("passport_name", passportName);
        addPetPage
                .uploadFile(1, passportFile);
        basePage
                .chooseRadio("Да")
                .enterValueInInput("chip_num", chipNum);
        calendarComponent
                .enterDate("chip_date", date);
        basePage
                .enterValueInInput("chip_place", chipPlace)
                .enterValueInDropdown("association[0].name", associationName)
                .enterValueInInput("association[0].identifier", associationId)
                .enterValueInInput("association[0].link", associationLink);
        addPetPage
                .uploadFile(2, certificateFile)
                .clickOnSideBarTab("Фотографии")
                .uploadPhoto(3, photoFile)
                .clickOnSideBarTab("Выставки и награды");
        basePage
                .enterValueInDropdown("exposition[0].name", expositionName);
        calendarComponent
                .enterDate("exposition[0].date_start", date)
                .enterDate("exposition[0].date_end", date);
        basePage
                .enterValueInInput("exposition[0].link", expositionLink);
        addPetPage
                .enterExpositionDescription("Описание выставки", expositionDescription); //заменить
        basePage
                .clickOnButton("Добавить достижение")
                .enterValueInDropdown("exposition[0].pet_award[0].type", reward)
                .enterValueInInput("exposition[0].pet_award[0].nomination", nomination);
        addPetPage
                .uploadFile(4, expositionFile)
                .clickOnSideBarTab("Рацион питания");
        basePage
                .enterValueInDropdown("food[0].feed", foodName);
        calendarComponent
                .enterFoodDate("food[0].date_from", date)
                .enterDate("food[0].date_to", date);
        basePage
                .enterValueInTextarea("food[0].description", foodDescription);
        addPetPage
                .clickOnSideBarTab("Вакцинация");
        basePage
                .enterValueInDropdown("vaccines[0].vaccine", vaccinesName);
        calendarComponent
                .enterDate("vaccines[0].date_from", date);
        basePage
                .selectValueInDropdown("vaccines[0].recurrence_rule", vaccinesRepeat)
                .enterValueInTextarea("vaccines[0].description", vaccinesDescription);
        addPetPage
                .clickOnSideBarTab("Лечение")
                .enterDiagnosis(diagnosis); //заменить
        calendarComponent
                .enterDate("cure[0].date_from", date)
                .enterDate("cure[0].date_from_diagnosis", date);
        addPetPage
                .enterCure(cure) //заменить
                .uploadFile(5, cureFile)
                .clickOnSideBarTab("Медицинская карта")
                .enterBloodGroup("Группа крови", bloodType); //заменить
        basePage
                .enterValueInDropdown("medical_card.pet_inherited_diseases", illness);
        calendarComponent
                .enterDate("medical_card.pet_measurements[0].date", date);
        basePage
                .enterNumberInInput("medical_card.pet_measurements[0].weight", weight)
                .enterNumberInInput("medical_card.pet_measurements[0].height", height);
        addPetPage
                .clickOnSideBarTab("Вязка");
        basePage
                .clickOnButton("Создать")
                .checkGreenMessage();
        addPetPage
                .checkPetInfo(breed)
                .checkPetInfo(nickname)
                .checkPetInfo(sex)
                .checkPetInfo(birthOut)
                .checkPetInfo(petColor)
                .checkPetInfo(eyesColor)
                .checkPetInfo(petDescription)

                .checkPetContacts(passportNum)
                .checkPetContacts(passportFrom)
                .checkPetContacts(dateOut)
                .checkPetContacts(passportName)
                .checkPetContacts("Да")
                .checkPetContacts(passportFile)
                .checkPetContacts(chipPlace)
                .checkPetContacts(chipNum + " / " + dateChip)
                .checkPetContacts(associationName)
                .checkPetContacts("Идентификтор питомца: " + associationId)
                .checkPetContacts(associationLink)
                .checkPetContacts(certificateFile)

                .checkPetAdwards(expositionName)
                .checkPetAdwards(expositionDescription)
                .checkPetAdwards(expositionLink)
                .checkPetAdwards(expositionFile)
                .checkPetAdwards(reward)
                .checkPetAdwards(nomination)
                .checkPetAdwards(dateOut)

                .checkPetDiet(foodName)
                .checkPetDiet(foodDescription)
                .checkPetDiet(dateOut)

                .checkPetVaccination(vaccinesName)
                .checkPetVaccination(vaccinesDescription)
                .checkPetVaccination(dateOut)

                .checkPetHealth(diagnosis)
                .checkPetHealth(dateOut)
                .checkPetHealth(cureFile)
                .checkPetHealth(cure)
                .checkPetHealth("От: " + dateOut)

                .checkPetMedicalCard(bloodType)
                .checkPetMedicalCard(illness)
                .checkPetMedicalCard(dateOut)
                .checkPetMedicalCard(weight + " кг")
                .checkPetMedicalCard(height + " см");
    }
}