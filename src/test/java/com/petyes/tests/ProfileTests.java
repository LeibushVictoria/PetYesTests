package com.petyes.tests;

import com.github.javafaker.Faker;
import com.petyes.api.Login;
import com.petyes.config.App;
import com.petyes.pages.BasePage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ProfileTests extends TestBase{



    @Test
    @DisplayName("Просмотр своего профиля")
    void viewProfileTest() {
        BasePage basePage = new BasePage();
        Login login = new Login();

        String token = login.loginByAPI(App.config.customerPhoneNumberAPI(), App.config.userPassword());
        int user_id = login.getUserId(token);
        basePage
                .openPage("/user/" + user_id)
                .checkHeaderH4("Покупатель Автотест");
    }

    @Test
    @DisplayName("Редактирование своего профиля (о себе)")
    void editProfileTest() {
        Faker faker = new Faker();
        BasePage basePage = new BasePage();
        Login login = new Login();

        String aboutMe = faker.chuckNorris().fact();

        String token = login.loginByAPI(App.config.customerPhoneNumberAPI(), App.config.userPassword());
        int user_id = login.getUserId(token);

        basePage
                .openPage("/settings/info")
                .clearTextarea()
                .enterValueInTextarea(aboutMe)
                .clickOnButton("Сохранить изменения")
                .checkGreenMessage()
                .openPage("/user/" + user_id)
                .checkBlockDisplay(aboutMe);
    }
}
