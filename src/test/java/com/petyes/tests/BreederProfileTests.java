package com.petyes.tests;

import com.github.javafaker.Faker;
import com.petyes.api.Auction;
import com.petyes.api.Login;
import com.petyes.api.Pet;
import com.petyes.config.App;
import com.petyes.pages.BasePage;
import com.petyes.pages.components.CalendarComponent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BreederProfileTests extends TestBase {

    @Test
    @DisplayName("Просмотр продавцом своего профиля")
    void viewBreederProfileTest() {
        BasePage basePage = new BasePage();
        Login login = new Login();

        String token = login.loginByAPI(App.config.breederPhoneNumberAPI(), App.config.userPassword());
        int user_id = login.getUserId(token);

        login
                .setCookie(token);
        basePage
                .openPage("/user/" + user_id)
                .checkHeader(4,"Продавец  Автотест");
    }

    @Test
    @DisplayName("Редактирование продавцом своего профиля (о себе)")
    void editProfileTest() {
        Faker faker = new Faker();
        BasePage basePage = new BasePage();
        Login login = new Login();

        String aboutMe = faker.chuckNorris().fact();

        String token = login.loginByAPI(App.config.breederPhoneNumberAPI(), App.config.userPassword());
        int user_id = login.getUserId(token);

        login
                .setCookie(token);
        basePage
                .openPage("/settings/info")
                .clearTextarea()
                .enterValueInTextarea("description", aboutMe)
                .clickOnButton("Сохранить изменения")
                .checkGreenMessage()
                .openPage("/user/" + user_id)
                .checkBlockDisplay(aboutMe);
    }

    @Test
    @DisplayName("Просмотр продавцом объявлений в своем профиле")
    void viewSaleTest() {
        BasePage basePage = new BasePage();
        Pet pet = new Pet();
        CalendarComponent calendarComponent = new CalendarComponent();
        Login login = new Login();

        String token = login.loginByAPI(App.config.breederPhoneNumberAPI(), App.config.userPassword());
        int user_id = login.getUserId(token);

        Date dateBirth = calendarComponent.getOtherDate(-20);
        SimpleDateFormat formater = new SimpleDateFormat("dd.MM.yyyy");
        String birth = formater.format(dateBirth);

        int pet_id = pet.createPetByAPI(token, false, 13, "autoTestCat", birth, 0, 1007, 1,597);
        int sell_id = pet.salePetByAPI(token, false, false, true, 10000, pet_id);

        login
                .setCookie(token);
        basePage
                .openPage("/user/" + user_id + "#sales")
                .checkLinkById(sell_id);

        pet.cancelPetSaleByAPI(token, sell_id);
        pet.deletePetByAPI(token, pet_id);
    }

    @Test
    @DisplayName("Просмотр продавцом аукционов в своем профиле")
    void viewAuctionTest() {
        BasePage basePage = new BasePage();
        CalendarComponent calendarComponent = new CalendarComponent();
        Login login = new Login();
        Pet pet = new Pet();
        Auction auction = new Auction();

        SimpleDateFormat formaterBirth = new SimpleDateFormat("dd.MM.yyyy");
        SimpleDateFormat formaterAuction = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        Date dateBirth = calendarComponent.getOtherDate(-20);
        String birth = formaterBirth.format(dateBirth);

        Date dateTomorrow = calendarComponent.getOtherDate(1);
        String tomorrow = formaterAuction.format(dateTomorrow);

        Date dateDayAfterTomorrow = calendarComponent.getOtherDate(2);
        String dayAfterTomorrow = formaterAuction.format(dateDayAfterTomorrow);

        String token = login.loginByAPI(App.config.breederPhoneNumberAPI(), App.config.userPassword());
        int user_id = login.getUserId(token);

        int pet_id = pet.createPetByAPI(token, false, 13, "autoTestAuctionCat", birth, 0, 1007, 1,597);
        int auction_id = auction.createAuctionByAPI(token, tomorrow, dayAfterTomorrow, 10000, 1,
                20000, false, false, pet_id);

        login
                .setCookie(token);
        basePage
                .openPage("/user/" + user_id + "#auctions")
                .checkLinkById(auction_id);

        auction.deleteAuctionByAPI(token, auction_id);
    }
}
