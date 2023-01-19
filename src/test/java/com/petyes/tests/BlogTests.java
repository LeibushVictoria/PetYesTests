package com.petyes.tests;

import com.petyes.api.Login;
import com.petyes.domain.DataBuilder;
import com.petyes.domain.ItemsForLogin;
import com.petyes.pages.ArticlePage;
import com.petyes.pages.BasePage;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

public class BlogTests extends TestBase {

    @EnumSource(ItemsForLogin.class)
    @ParameterizedTest(name = "Создать статью: {0}")
    @Tag("regression")
    void createArticleTest(ItemsForLogin items) {
        BasePage basePage = new BasePage();
        Login login = new Login();
        ArticlePage articlePage = new ArticlePage();

        login
                .setCookie(items.getToken());
        basePage
                .openPage("/knowledge/article/new")
                .enterValueInInput("name","Автотестовое название статьи");
        articlePage
                .selectCategory(DataBuilder.categoryName);
        basePage
                .chooseRadio("Кошки")
                .enterValueInTextarea("annotation", "Автотестовая аннотация статьи");
        articlePage
                .enterText("Автотестовый текст статьи")
                .uploadFile("pet_photo.jpg");
        basePage
                .clickOnButton("Опубликовать");
        articlePage
                .checkCreateArticles("Статья успешно создана");
        basePage
                .clickOnButton("Смотреть все статьи")
                .checkHeader(2, "Статьи");
    }

    @EnumSource(ItemsForLogin.class)
    @ParameterizedTest(name = "Работа фильтров: {0}")
    @Tag("smoke")
    @Tag("regression")
    void filterArticlesTest(ItemsForLogin items) {
        BasePage basePage = new BasePage();
        Login login = new Login();

        int article_id = DataBuilder.article_id;

        login
                .setCookie(items.getToken());
        basePage
                .openPage("/knowledge/articles")
                .chooseRadio("Кошки")
                .openFilter("Категория")
                .chooseCheckbox(DataBuilder.categoryName)
                .clickOnButton("Показать")
                .checkLinkById(article_id);
    }
}
