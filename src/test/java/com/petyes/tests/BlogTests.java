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
                .enterValueInInput("Название статьи","Автотестовое название статьи");
        articlePage
                .selectFirstValueFromDropdown();
        basePage
                .chooseRadio("Кошки")
                .enterValueInTextarea("Аннотация статьи", "Автотестовая аннотация статьи");
        articlePage
                .enterText("Автотестовый текст статьи");
        //basePage
                //.uploadFile("Загрузить обложку", "pet_avatar.jpg");
    }

    @Test
    @DisplayName("Работа фильтров")
    void filterArticlesTest() {
        BasePage basePage = new BasePage();
        Login login = new Login();
        ArticlePage articlePage = new ArticlePage();

        login
                .loginByAPI(App.config.customerPhoneNumberAPI(), App.config.userPassword());
        basePage
                .openPage("/knowledge/articles")
                .chooseRadio("Кошки")
                .openFilter("Категория")
                .chooseCheckbox("тест1");
        basePage
                .clickOnButton("Показать");
        articlePage
                .checkResult("Лылуто");
    }

    @Test
    @DisplayName("Просмотр статьи")
    void viewArticleTest() {
        BasePage basePage = new BasePage();
        Login login = new Login();
        ArticlePage articlePage = new ArticlePage();

        login
                .loginByAPI(App.config.customerPhoneNumberAPI(), App.config.userPassword());

    }
}
