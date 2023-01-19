package com.petyes.tests;

import com.petyes.api.Auction;
import com.petyes.api.Login;
import com.petyes.domain.DataBuilder;
import com.petyes.domain.ItemsForLogin;
import com.petyes.pages.BasePage;
import com.petyes.pages.components.CalendarComponent;
import com.petyes.pages.components.CityComponent;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.text.SimpleDateFormat;
import java.util.Date;

@Disabled
public class AuctionListTests extends TestBase {

    @EnumSource(ItemsForLogin.class)
    @ParameterizedTest(name = "Работа фильтров: {0}")
    @Tag("regression")
    void filterAuctionsTest(ItemsForLogin items) {
        BasePage basePage = new BasePage();
        Login login = new Login();
        CityComponent cityComponent = new CityComponent();
        CalendarComponent calendarComponent = new CalendarComponent();
        Auction auction = new Auction();

        SimpleDateFormat formaterAuction = new SimpleDateFormat("dd.MM.yyyy HH:mm z");

        Date dateTomorrow = calendarComponent.getOtherDate(1);
        String tomorrow = formaterAuction.format(dateTomorrow);

        Date dateDayAfterTomorrow = calendarComponent.getOtherDate(2);
        String dayAfterTomorrow = formaterAuction.format(dateDayAfterTomorrow);

        int auction_id = auction.createAuctionByAPI(dateTomorrow, dateDayAfterTomorrow, 10000, 1,
                20000, false, false);

        login
                .setCookie(items.getToken());
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
                .selectValueInDropdownInFilter("Выберите породу", "Абиссинская")
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

        auction.deleteAuctionByAPI(auction_id);
    }

    @Test
    @DisplayName("Просмотр аукциона")
    @Tag("regression")
    void viewAuctionTest() {
        BasePage basePage = new BasePage();
        Login login = new Login();
        CalendarComponent calendarComponent = new CalendarComponent();
        Auction auction = new Auction();

        Date tomorrow = calendarComponent.getOtherDate(1);
        Date dayAfterTomorrow = calendarComponent.getOtherDate(2);

        int auction_id = auction.createAuctionByAPI(tomorrow, dayAfterTomorrow, 10000, 1,
                20000, false, false);

        login
                .setCookie(DataBuilder.customerToken);
        basePage
                .openPage("/auction/" + auction_id)
                .checkBlockDisplay("Характеристики")
                .checkBlockDisplay("Подать заявку на участие")
                .checkBlockDisplay("Продавец Автотест");

        auction.deleteAuctionByAPI(auction_id);
    }

    @Test
    @DisplayName("Подать заявку на участие в аукционе")
    @Tag("regression")
    void auctionApplicationTest() {
        BasePage basePage = new BasePage();
        Login login = new Login();
        CalendarComponent calendarComponent = new CalendarComponent();
        Auction auction = new Auction();

        Date tomorrow = calendarComponent.getOtherDate(1);
        Date dayAfterTomorrow = calendarComponent.getOtherDate(2);

        int auction_id = auction.createAuctionByAPI(tomorrow, dayAfterTomorrow, 10000, 1,
                20000, false, false);

        login
                .setCookie(DataBuilder.customerToken);
        basePage
                .openPage("/auction/" + auction_id)
                .clickOnButton("Подать заявку на участие")
                .clickOnButton("Да, подать")
                .checkBlockDisplay("Отменить заявку на участие");

        auction.deleteAuctionByAPI(auction_id);
    }
}
