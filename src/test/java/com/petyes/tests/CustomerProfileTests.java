package com.petyes.tests;

import com.github.javafaker.Faker;
import com.petyes.api.Login;
import com.petyes.domain.DataBuilder;
import com.petyes.pages.BasePage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class CustomerProfileTests extends TestBase {

    @Test
    @Tag("regression")
    @DisplayName("Просмотр покупателем своего профиля")
    void viewCustomerProfileTest() {
        BasePage basePage = new BasePage();
        Login login = new Login();

        login
                .setCookie(DataBuilder.customerToken);
        basePage
                .openPage("/user/" + DataBuilder.customer_id)
                .checkHeader(4,"Покупатель Автотест");
    }

    @Test
    @Tag("smoke")
    @Tag("regression")
    @DisplayName("Редактирование покупателем своего профиля (о себе)")
    void editProfileTest() {
        Faker faker = new Faker();
        BasePage basePage = new BasePage();
        Login login = new Login();

        String aboutMe = faker.chuckNorris().fact();

        login
                .setCookie(DataBuilder.customerToken);
        basePage
                .openPage("/settings/info")
                .clearTextarea()
                .enterValueInTextarea("description", aboutMe)
                .clickOnButton("Сохранить изменения")
                .checkGreenMessage()
                .openPage("/user/" + DataBuilder.customer_id)
                .checkBlockDisplay(aboutMe);
    }

    @Test
    @Tag("regression")
    @DisplayName("Просмотр покупателем запросов в своем профиле")
    void viewRequestTest() {
        BasePage basePage = new BasePage();
        Login login = new Login();

        login
                .setCookie(DataBuilder.customerToken);
        basePage
                .openPage("/user/" + DataBuilder.customer_id + "#requests")
                .checkLinkById(DataBuilder.request_id);
    }
}
