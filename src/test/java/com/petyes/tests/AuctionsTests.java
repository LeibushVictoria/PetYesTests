package com.petyes.tests;

import com.petyes.api.Auction;
import com.petyes.api.Login;
import com.petyes.config.App;
import com.petyes.pages.AuctionPage;
import com.petyes.pages.BasePage;
import com.petyes.pages.components.CityComponent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AuctionsTests extends TestBase{

    @Test
    @DisplayName("Работа фильтров")
    void filterAuctionsTest() {
        BasePage basePage = new BasePage();
        Login login = new Login();
        Auction auction = new Auction();
        CityComponent cityComponent = new CityComponent();
        AuctionPage auctionPage = new AuctionPage();

        int auction_id = auction.createAuctionByAPI("22.04.2022 00:01 GMT+03:00", "23.04.2022 00:02 GMT+03:00", 10000, 1,
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
                .enterValue(0, "22.04.2022 00:01")
                .openFilter("Дата окончания")
                .enterValue(1, "23.04.2022 00:02")
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
                .clickOnButton("Показать");
        auctionPage
                .checkResult(auction_id);
    }

    @Test
    @DisplayName("Просмотр аукциона")
    void viewAuctionTest() {
        BasePage basePage = new BasePage();
        Login login = new Login();
        Auction auction = new Auction();
        int auction_id = auction.createAuctionByAPI("20.04.2022 00:01 GMT+03:00", "21.04.2022 00:02 GMT+03:00", 10000, 1,
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
        int auction_id = auction.createAuctionByAPI("20.04.2022 00:01 GMT+03:00", "21.04.2022 00:02 GMT+03:00", 10000, 1,
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
