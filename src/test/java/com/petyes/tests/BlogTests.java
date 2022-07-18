package com.petyes.tests;

import com.petyes.api.Blog;
import com.petyes.api.Login;
import com.petyes.config.App;
import com.petyes.domain.ItemsForLogin;
import com.petyes.pages.ArticlePage;
import com.petyes.pages.BasePage;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

public class BlogTests extends TestBase {

    @EnumSource(ItemsForLogin.class)
    @ParameterizedTest(name = "Создать статью: {0}")
    void createArticleTest(ItemsForLogin items) {
        BasePage basePage = new BasePage();
        Login login = new Login();
        Blog blog = new Blog();
        ArticlePage articlePage = new ArticlePage();

        blog.addBlogCategoryByAPI("autotest", "#666666");

        String token = login.loginByAPI(items.getPhoneNumber(), App.config.userPassword());

        login
                .setCookie(token);
        basePage
                .openPage("/knowledge/article/new")
                .enterValueInInput("name","Автотестовое название статьи");
        articlePage
                .selectCategory("autotest");
        basePage
                .chooseRadio("Кошки")
                .enterValueInTextarea("annotation", "Автотестовая аннотация статьи");
        articlePage
                .enterText("Автотестовый текст статьи")
                .uploadFile("pet_photo.jpg");
        basePage
                .clickOnButton("Опубликовать")
                .clickOnButton("Смотреть все статьи");
        articlePage
                .checkCreateArticles("Статья успешно создана")
                .checkCreateArticles("Смотреть все статьи");
    }

    @EnumSource(ItemsForLogin.class)
    @ParameterizedTest(name = "Работа фильтров: {0}")
    void filterArticlesTest(ItemsForLogin items) {
        BasePage basePage = new BasePage();
        Login login = new Login();
        Blog blog = new Blog();
        ArticlePage articlePage = new ArticlePage();

        blog.addBlogCategoryByAPI("autotest", "#666666");

        String token = login.loginByAPI(items.getPhoneNumber(), App.config.userPassword());

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
