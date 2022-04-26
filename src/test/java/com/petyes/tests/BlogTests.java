package com.petyes.tests;

import com.petyes.api.Login;
import com.petyes.config.App;
import com.petyes.pages.ArticlePage;
import com.petyes.pages.BasePage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BlogTests extends TestBase {

    @Test
    @DisplayName("Опубликовать статью")
    void createArticleTest() {
        BasePage basePage = new BasePage();
        Login login = new Login();
        ArticlePage articlePage = new ArticlePage();

        login
                .loginByAPI(App.config.customerPhoneNumberAPI(), App.config.userPassword());
        basePage
                .openPage("/knowledge/articles")
                .checkHeaderH2("Статьи")
                .clickOnButton("Опубликовать статью")
                .enterValue(0,"Тестовое название статьи");
        articlePage
                .selectFirstValueFromDropdown();
        basePage
                .chooseRadio("Кошки");
        articlePage
                .enterValue("Тестовая аннотация статьи")
                .enterText("Тестовый текст статьи")
                .uploadPicture("Загрузить обложку", "test.jpg");
    }
}
