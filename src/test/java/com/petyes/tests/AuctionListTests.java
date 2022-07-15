package com.petyes.tests;

import com.petyes.api.Auction;
import com.petyes.api.Login;
import com.petyes.api.Pet;
import com.petyes.config.App;
import com.petyes.pages.BasePage;
import com.petyes.pages.components.CalendarComponent;
import com.petyes.pages.components.CityComponent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AuctionListTests extends TestBase {

    @Test
    @DisplayName("Работа фильтров")
    void filterAuctionsTest() {
        BasePage basePage = new BasePage();
        Login login = new Login();
        CityComponent cityComponent = new CityComponent();
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

        String breederToken = login.loginByAPI(App.config.breederPhoneNumberAPI(), App.config.userPassword());

        int pet_id = pet.createPetByAPI(breederToken, false, 13, "autoTestAuctionCat", birth, 0, 1007, 1,597);
        int auction_id = auction.createAuctionByAPI(breederToken, tomorrow, dayAfterTomorrow, 10000, 1,
                20000, false, false, pet_id);

        String customerToken = login.loginByAPI(App.config.customerPhoneNumberAPI(), App.config.userPassword());

        login
                .setCookie(customerToken);
        basePage
                .openPage("/auctions");
        cityComponent
                .chooseCity("Санкт-Петербург");
        basePage
                .chooseCheckbox("Приватные")
                .openFilter("Блиц-цена")
                .chooseRadio("Есть")
                .openFilter("Дата начала")
                .enterValueInInputInFilter(0, tomorrow)
                .openFilter("Дата окончания")
                .enterValueInInputInFilter(1, dayAfterTomorrow)
                .openFilter("Животные")
                .chooseRadio("Кошки")
                .openFilter("Порода")
                .selectValueInDropdownInFilter("Выберите породу", "Бамбино")
                .openFilter("Ценовой диапазон, ₽")
                .enterValueByKeys(2, "9999")
                .enterValueByKeys(3, "10001")
                .openFilter("Пол")
                .chooseRadio("Самец")
                .openFilter("Возраст")
                .chooseCheckbox("До 6 месяцев")
                .openFilter("Особенности")
                .chooseCheckbox("Кастрация/стерилизация")
                .clickOnButton("Показать")
                .checkLinkById(auction_id);

        auction.deleteAuctionByAPI(breederToken, auction_id);
    }

    @Test
    @DisplayName("Просмотр аукциона")
    void viewAuctionTest() {
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

        String breederToken = login.loginByAPI(App.config.breederPhoneNumberAPI(), App.config.userPassword());

        int pet_id = pet.createPetByAPI(breederToken, false, 13, "autoTestAuctionCat", birth, 0, 1007, 1,597);
        int auction_id = auction.createAuctionByAPI(breederToken, tomorrow, dayAfterTomorrow, 10000, 1,
                20000, false, false, pet_id);

        String customerToken = login.loginByAPI(App.config.customerPhoneNumberAPI(), App.config.userPassword());

        login
                .setCookie(customerToken);
        basePage
                .openPage("/auction/" + auction_id)
                .checkBlockDisplay("Характеристики")
                .checkBlockDisplay("Подать заявку на участие")
                .checkBlockDisplay("Продавец Автотест");

        auction.deleteAuctionByAPI(breederToken, auction_id);
    }

    //bug
    @Test
    @DisplayName("Подать заявку на участие в аукционе")
    void auctionApplicationTest() {
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

        String breederToken = login.loginByAPI(App.config.breederPhoneNumberAPI(), App.config.userPassword());

        int pet_id = pet.createPetByAPI(breederToken, false, 13, "autoTestAuctionCat", birth, 0, 1007, 1,597);
        int auction_id = auction.createAuctionByAPI(breederToken, tomorrow, dayAfterTomorrow, 10000, 1,
                20000, false, false, pet_id);

        String customerToken = login.loginByAPI(App.config.customerPhoneNumberAPI(), App.config.userPassword());

        login
                .setCookie(customerToken);
        basePage
                .openPage("/auction/" + auction_id)
                .clickOnButton("Подать заявку на участие")
                .clickOnButton("Да, подать")
                .checkBlockDisplay("Отменить заявку на участие");

        auction.deleteAuctionByAPI(breederToken, auction_id);
    }
}
