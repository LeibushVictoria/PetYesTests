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

        Date dateTomorrow = calendarComponent.getFutureDate(1);
        SimpleDateFormat formater1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String tomorrow = formater1.format(dateTomorrow);

        Date dateDayAfterTomorrow = calendarComponent.getFutureDate(2);
        SimpleDateFormat formater2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dayAfterTomorrow = formater2.format(dateDayAfterTomorrow);

        int auction_id = auction.createAuctionByAPI(tomorrow, dayAfterTomorrow, 10000, 1,
                20000, false, false);
        login
                .loginByAPI(App.config.customerPhoneNumberAPI(), App.config.userPassword());
        basePage
                .openPage("/auctions");
        cityComponent
                .chooseCity("Санкт-Петербург");
        basePage
                .chooseCheckbox("Приватные")
                .openFilter("Блиц-цена")
                .chooseRadio("Есть")
                .openFilter("Дата начала")
                .enterValue(0, tomorrow)
                .openFilter("Дата окончания")
                .enterValue(1, dayAfterTomorrow)
                .openFilter("Животные")
                .chooseRadio("Кошки")
                .openFilter("Порода")
                .selectValueFromDropdown("Выберите породу", "Бамбино")
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

        Date dateTomorrow = calendarComponent.getFutureDate(1);
        SimpleDateFormat formater1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String tomorrow = formater1.format(dateTomorrow);

        Date dateDayAfterTomorrow = calendarComponent.getFutureDate(2);
        SimpleDateFormat formater2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dayAfterTomorrow = formater2.format(dateDayAfterTomorrow);

        int auction_id = auction.createAuctionByAPI(tomorrow, dayAfterTomorrow, 10000, 1,
                20000, false, false);
        login
                .loginByAPI(App.config.customerPhoneNumberAPI(), App.config.userPassword());
        basePage
                .openPage("/auction/" + auction_id)
                .checkHeaderH3("Абиссинская")
                .checkBlockDisplay("Подать заявку на участие")
                .checkBlockDisplay("Продавец Автотест");
    }

    @Test
    @DisplayName("Подать заявку на участие в аукционе")
    void auctionApplicationTest() {
        BasePage basePage = new BasePage();
        Login login = new Login();
        Auction auction = new Auction();
        CalendarComponent calendarComponent = new CalendarComponent();

        Date dateTomorrow = calendarComponent.getFutureDate(1);
        SimpleDateFormat formater1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String tomorrow = formater1.format(dateTomorrow);

        Date dateDayAfterTomorrow = calendarComponent.getFutureDate(2);
        SimpleDateFormat formater2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dayAfterTomorrow = formater2.format(dateDayAfterTomorrow);

        int auction_id = auction.createAuctionByAPI(tomorrow, dayAfterTomorrow, 10000, 1,
                20000, false, false);
        login
                .loginByAPI(App.config.customerPhoneNumberAPI(), App.config.userPassword());
        basePage
                .openPage("/auction/" + auction_id)
                .clickOnButton("Подать заявку на участие")
                .clickOnButton("Да, подать")
                .checkBlockDisplay("Отменить заявку на участие");
    }
}
