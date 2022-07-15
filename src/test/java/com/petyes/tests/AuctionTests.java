package com.petyes.tests;

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

public class AuctionTests {

    @Test
    @DisplayName("Выставить на аукцион существующего питомца")
    void auctionPetTest() {
        Pet pet = new Pet();
        BasePage basePage = new BasePage();
        Login login = new Login();
        CalendarComponent calendarComponent = new CalendarComponent();
        Auction auction = new Auction();

        SimpleDateFormat formaterBirth = new SimpleDateFormat("dd.MM.yyyy");
        SimpleDateFormat formaterAuction = new SimpleDateFormat("dd-MM-yyyy HH:mm");

        Date dateBirth = calendarComponent.getOtherDate(-20);
        String birth = formaterBirth.format(dateBirth);

        Date dateTomorrow = calendarComponent.getOtherDate(1);
        String tomorrow = formaterAuction.format(dateTomorrow);

        Date dateDayAfterTomorrow = calendarComponent.getOtherDate(2);
        String dayAfterTomorrow = formaterAuction.format(dateDayAfterTomorrow);

        String token = login.loginByAPI(App.config.breederPhoneNumberAPI(), App.config.userPassword());

        int pet_id = pet.createPetByAPI(token, false, 13, "autoTestAuctionCat", birth, 0, 1007, 1,597);

        login
                .setCookie(token);
        basePage
                .openPage("/sale/add/" + pet_id)
                .chooseRadio("Аукцион");
        calendarComponent
                .enterDate("finished_at", dayAfterTomorrow)
                .enterDate("started_at", tomorrow);
        basePage
                .enterNumberInInput("start_cost", "10000")
                .enterNumberInInput("blitz_price", "20000")
                .clickOnButton("Опубликовать")
                .clickOnButton("Закрыть")
                .checkGreenMessage()
                .checkBlockDisplay("Абиссинская")
                .checkBlockDisplay("10 000 ₽");

        int auction_id = basePage.getIdFromUrl();
        auction.deleteAuctionByAPI(token, auction_id);
    }

    //bug
    @Test
    @DisplayName("Редактировать аукцион (блиц-цена)")
    void editPetAuctionTest() {
        BasePage basePage = new BasePage();
        Login login = new Login();
        CalendarComponent calendarComponent = new CalendarComponent();
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

        int pet_id = pet.createPetByAPI(token, false, 13, "autoTestAuctionCat", birth, 0, 1007, 1,597);
        int auction_id = auction.createAuctionByAPI(token, tomorrow, dayAfterTomorrow, 10000, 1,
                20000, false, false, pet_id);

        String price= "99 999";

        login
                .setCookie(token);
        basePage
                .openPage("/auction/" + auction_id)
                .clickOnButton("Управление")
                .enterValueByKeys(4, price)
                .clickOnButton("Сохранить изменения")
                .checkGreenMessage();

        auction.deleteAuctionByAPI(token, auction_id);
    }

    //bug
    @Test
    @DisplayName("Удалить аукцион")
    void cancelPetSaleTest() {
        BasePage basePage = new BasePage();
        Login login = new Login();
        CalendarComponent calendarComponent = new CalendarComponent();
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

        int pet_id = pet.createPetByAPI(token, false, 13, "autoTestAuctionCat", birth, 0, 1007, 1,597);
        int auction_id = auction.createAuctionByAPI(token, tomorrow, dayAfterTomorrow, 10000, 1,
                20000, false, false, pet_id);

        String price= "99 999";

        login
                .setCookie(token);
        basePage
                .openPage("/auction/" + auction_id)
                .clickOnButton("Управление")
                .clickOnButton("Удалить аукцион")
                .clickOnButton("Да, удалить")
                .checkGreenMessage();
    }

}
