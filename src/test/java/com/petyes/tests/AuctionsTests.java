package com.petyes.tests;

import com.petyes.api.Auction;
import com.petyes.api.Login;
import com.petyes.config.App;
import com.petyes.pages.BasePage;
import com.petyes.pages.components.CalendarComponent;
import com.petyes.pages.components.CityComponent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AuctionsTests extends TestBase{

    @Test
    @DisplayName("Работа фильтров")
    void filterAuctionsTest() {
        BasePage basePage = new BasePage();
        Login login = new Login();
        Auction auction = new Auction();
        CityComponent cityComponent = new CityComponent();
        CalendarComponent calendarComponent = new CalendarComponent();

        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        Date dateTomorrow = calendarComponent.getOtherDate(1);
        String tomorrow = formater.format(dateTomorrow);

        Date dateDayAfterTomorrow = calendarComponent.getOtherDate(2);
        String dayAfterTomorrow = formater.format(dateDayAfterTomorrow);

        String breederToken = login.loginByAPI(App.config.breederPhoneNumberAPI(), App.config.userPassword());
        int auction_id = auction.createAuctionByAPI(breederToken, tomorrow, dayAfterTomorrow, 10000, 1,
                20000, false, false);

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
                .checkLink(auction_id);
    }

    @Test
    @DisplayName("Просмотр аукциона")
    void viewAuctionTest() {
        BasePage basePage = new BasePage();
        Login login = new Login();
        Auction auction = new Auction();
        CalendarComponent calendarComponent = new CalendarComponent();
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        Date dateTomorrow = calendarComponent.getOtherDate(1);
        String tomorrow = formater.format(dateTomorrow);

        Date dateDayAfterTomorrow = calendarComponent.getOtherDate(2);
        String dayAfterTomorrow = formater.format(dateDayAfterTomorrow);

        String breederToken = login.loginByAPI(App.config.breederPhoneNumberAPI(), App.config.userPassword());
        int auction_id = auction.createAuctionByAPI(breederToken, tomorrow, dayAfterTomorrow, 10000, 1,
                20000, false, false);

        String customerToken = login.loginByAPI(App.config.customerPhoneNumberAPI(), App.config.userPassword());

        login
                .setCookie(customerToken);
        basePage
                .openPage("/auction/" + auction_id)
                .checkBlockDisplay("Характеристики")
                .checkBlockDisplay("Подать заявку на участие")
                .checkBlockDisplay("Продавец Автотест");
    }

    //bug
    @Test
    @DisplayName("Подать заявку на участие в аукционе")
    void auctionApplicationTest() {
        BasePage basePage = new BasePage();
        Login login = new Login();
        Auction auction = new Auction();
        CalendarComponent calendarComponent = new CalendarComponent();
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        Date dateTomorrow = calendarComponent.getOtherDate(1);
        String tomorrow = formater.format(dateTomorrow);

        Date dateDayAfterTomorrow = calendarComponent.getOtherDate(2);
        String dayAfterTomorrow = formater.format(dateDayAfterTomorrow);

        String breederToken = login.loginByAPI(App.config.breederPhoneNumberAPI(), App.config.userPassword());
        int auction_id = auction.createAuctionByAPI(breederToken, tomorrow, dayAfterTomorrow, 10000, 1,
                20000, false, false);

        String customerToken = login.loginByAPI(App.config.customerPhoneNumberAPI(), App.config.userPassword());

        login
                .setCookie(customerToken);
        basePage
                .openPage("/auction/" + auction_id)
                .clickOnButton("Подать заявку на участие")
                .clickOnButton("Да, подать")
                .checkBlockDisplay("Отменить заявку на участие");
    }
}
