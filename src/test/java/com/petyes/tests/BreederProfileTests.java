package com.petyes.tests;

import com.github.javafaker.Faker;
import com.petyes.api.Auction;
import com.petyes.api.Login;
import com.petyes.api.Pet;
import com.petyes.api.Sale;
import com.petyes.config.App;
import com.petyes.pages.BasePage;
import com.petyes.pages.components.CalendarComponent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class BreederProfileTests extends TestBase {

    @Test
    @Tag("regression")
    @DisplayName("Просмотр продавцом своего профиля")
    void viewBreederProfileTest() {
        BasePage basePage = new BasePage();
        Login login = new Login();

        String token = login.loginByAPI(App.config.breederPhoneNumber(), App.config.userPassword());
        int user_id = login.getUserId(token);

        login
                .setCookie(token);
        basePage
                .openPage("/user/" + user_id)
                .checkHeader(4,"Продавец  Автотест");
    }

    @Test
    @Tag("regression")
    @DisplayName("Редактирование продавцом своего профиля (о себе)")
    void editProfileTest() {
        Faker faker = new Faker();
        BasePage basePage = new BasePage();
        Login login = new Login();

        String aboutMe = faker.chuckNorris().fact();

        String token = login.loginByAPI(App.config.breederPhoneNumber(), App.config.userPassword());
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
    @Tag("regression")
    @DisplayName("Просмотр продавцом объявлений в своем профиле")
    void viewSaleTest() {
        BasePage basePage = new BasePage();
        Pet pet = new Pet();
        Sale sale = new Sale();
        CalendarComponent calendarComponent = new CalendarComponent();
        Login login = new Login();

        Date birth = calendarComponent.getOtherDate(-20);
        int pet_id = pet.createPetByAPI(false, 13, "autoTestCat", birth, 0, 1007, 1,597);
        int sale_id = sale.salePetByAPI(false, false, true, 10000, pet_id);

        String token = login.loginByAPI(App.config.breederPhoneNumber(), App.config.userPassword());
        int user_id = login.getUserId(token);

        login
                .setCookie(token);
        basePage
                .openPage("/user/" + user_id + "#sales")
                .checkLinkById(sale_id);

        sale.cancelPetSaleByAPI(pet_id);
        pet.deletePetByAPI(pet_id);
    }

    @Test
    @Tag("regression")
    @DisplayName("Просмотр продавцом аукционов в своем профиле")
    void viewAuctionTest() {
        BasePage basePage = new BasePage();
        CalendarComponent calendarComponent = new CalendarComponent();
        Login login = new Login();
        Auction auction = new Auction();

        Date tomorrow = calendarComponent.getOtherDate(1);
        Date dayAfterTomorrow = calendarComponent.getOtherDate(2);

        int auction_id = auction.createAuctionByAPI(tomorrow, dayAfterTomorrow, 10000, 1, 20000, false, false);

        String token = login.loginByAPI(App.config.breederPhoneNumber(), App.config.userPassword());
        int user_id = login.getUserId(token);

        login
                .setCookie(token);
        basePage
                .openPage("/user/" + user_id + "#auctions")
                .checkLinkById(auction_id);

        auction.deleteAuctionByAPI(auction_id);
    }
}
