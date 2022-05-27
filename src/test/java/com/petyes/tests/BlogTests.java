package com.petyes.tests;

import com.petyes.api.Login;
import com.petyes.config.App;
import com.petyes.pages.ArticlePage;
import com.petyes.pages.BasePage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BlogTests extends TestBase {

    @Test
    @DisplayName("Создать статью")
    void createArticleTest() {
        BasePage basePage = new BasePage();
        Login login = new Login();
        ArticlePage articlePage = new ArticlePage();

        String token = login.loginByAPI(App.config.customerPhoneNumberAPI(), App.config.userPassword());

        login
                .setCookie(token);
        basePage
                .openPage("/knowledge/article/new")
                .uploadFile(0, "pet_avatar.jpg")
                .enterValueInInput("name","Автотестовое название статьи");
        articlePage
                .selectCategory("autotest");
        basePage
                .chooseRadio("Кошки")
                .enterValueInTextarea("annotation", "Автотестовая аннотация статьи");
        articlePage
                .enterText("Автотестовый текст статьи");
        basePage
                .clickOnButton("Опубликовать")
                .clickOnButton("Смотреть все статьи");
        articlePage
                .checkCreateArticles("Статья успешно создана")
                .checkCreateArticles("Смотреть все статьи");
    }

    @Test
    @DisplayName("Работа фильтров")
    void filterArticlesTest() {
        BasePage basePage = new BasePage();
        Login login = new Login();
        ArticlePage articlePage = new ArticlePage();

        String token = login.loginByAPI(App.config.customerPhoneNumberAPI(), App.config.userPassword());

        login
                .setCookie(token);
        basePage
                .openPage("/knowledge/articles")
                .chooseRadio("Кошки")
                .openFilter("Категория")
                .chooseCheckbox("autotest");
        basePage
                .clickOnButton("Показать");
        articlePage
                .checkResult("Автотестовое название статьи");
    }
}
