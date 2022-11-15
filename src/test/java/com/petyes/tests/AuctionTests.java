package com.petyes.tests;

import com.petyes.api.Auction;
import com.petyes.api.Login;
import com.petyes.api.Pet;
import com.petyes.config.AuthConfig;
import com.petyes.pages.BasePage;
import com.petyes.pages.components.CalendarComponent;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

@Disabled
public class AuctionTests extends TestBase {
    @Test
    @Tag("regression")
    @DisplayName("Выставить на аукцион существующего питомца")
    void auctionPetTest() {
        Pet pet = new Pet();
        BasePage basePage = new BasePage();
        Login login = new Login();
        CalendarComponent calendarComponent = new CalendarComponent();
        Auction auction = new Auction();

        Date birth = calendarComponent.getOtherDate(-20);
        int pet_id = pet.createPetByAPI(false, "autoTestAuctionCat", birth, 0, 1);

        SimpleDateFormat formaterAuction = new SimpleDateFormat("dd.MM.yyyy HH:mm z");

        Date dateTomorrow = calendarComponent.getOtherDate(1);
        String tomorrow = formaterAuction.format(dateTomorrow);

        Date dateDayAfterTomorrow = calendarComponent.getOtherDate(2);
        String dayAfterTomorrow = formaterAuction.format(dateDayAfterTomorrow);

        login
                .setCookie(AuthConfig.breederToken);
        basePage
                .openPage("/for-sale/create/" + pet_id)
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
                .checkBlockDisplay("Управление");

        int auction_id = basePage.getIdFromUrl();
        auction.deleteAuctionByAPI(auction_id);
    }

    @Test
    @Tag("regression")
    @DisplayName("Редактировать аукцион (блиц-цена)")
    void editPetAuctionTest() {
        BasePage basePage = new BasePage();
        Login login = new Login();
        CalendarComponent calendarComponent = new CalendarComponent();
        Auction auction = new Auction();

        Date tomorrow = calendarComponent.getOtherDate(1);
        Date dayAfterTomorrow = calendarComponent.getOtherDate(2);

        int auction_id = auction.createAuctionByAPI(tomorrow, dayAfterTomorrow, 10000, 1, 20000, false, false);

        String price= "99 999";

        login
                .setCookie(AuthConfig.breederToken);
        basePage
                .openPage("/auction/" + auction_id)
                .clickOnButton("Управление")
                .enterValueByKeys(4, price)
                .clickOnButton("Сохранить изменения")
                .checkGreenMessage();

        auction.deleteAuctionByAPI(auction_id);
    }

    @Test
    @Tag("regression")
    @DisplayName("Удалить аукцион")
    void cancelPetSaleTest() {
        BasePage basePage = new BasePage();
        Login login = new Login();
        CalendarComponent calendarComponent = new CalendarComponent();
        Auction auction = new Auction();

        Date tomorrow = calendarComponent.getOtherDate(1);
        Date dayAfterTomorrow = calendarComponent.getOtherDate(2);

        int auction_id = auction.createAuctionByAPI(tomorrow, dayAfterTomorrow, 10000, 1, 20000, false, false);

        login
                .setCookie(AuthConfig.breederToken);
        basePage
                .openPage("/auction/" + auction_id)
                .clickOnButton("Управление")
                .clickOnButton("Удалить аукцион")
                .clickOnButton("Да, удалить")
                .checkGreenMessage();
    }
}
