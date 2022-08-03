package com.petyes.tests;

import com.petyes.api.Login;
import com.petyes.config.AuthConfig;
import com.petyes.domain.ItemsForLogin;
import com.petyes.pages.BasePage;
import com.petyes.pages.KnowledgePage;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

@Feature("Каталог пород")
public class KnowledgeTests extends TestBase {

    @EnumSource(ItemsForLogin.class)
    @ParameterizedTest(name = "Подобрать породу: {0}")
    @Tag("regression")
    void chooseABreedTest(ItemsForLogin items) {
        BasePage basePage = new BasePage();
        Login login = new Login();
        KnowledgePage knowledgePage = new KnowledgePage();

        login
                .setCookie(items.getToken());
        basePage
                .openPage("/knowledge")
                .clickOnButton("Подобрать породу");
        knowledgePage
                .choosePetType();
        basePage
                .chooseRadio("Дома")
                .clickOnButton("Далее")
                .clickOnButton("Далее")
                .chooseRadio("Нет")
                .clickOnButton("Далее")
                .chooseCheckbox("Высокий")
                .clickOnButton("Далее")
                .chooseRadio("Да")
                .clickOnButton("Далее")
                .chooseRadio("Нет")
                .clickOnButton("Далее")
                .chooseRadio("Нет")
                .clickOnButton("Далее");
        knowledgePage
                .chooseBubble("Ласковый");
        basePage
                .clickOnButton("Далее");
        knowledgePage
                .chooseBubble("Ласковый питомец");
        basePage
                .clickOnButton("Завершить")
                .checkHeader(2,"Вам подходят следующие породы");
        knowledgePage
                .checkBreedsDisplay("Бенгальская");
    }

    @EnumSource(ItemsForLogin.class)
    @ParameterizedTest(name = "Сравнить породы: {0}")
    @Tag("regression")
    void compareBreedsTest(ItemsForLogin items) {
        BasePage basePage = new BasePage();
        Login login = new Login();
        KnowledgePage knowledgePage = new KnowledgePage();

        login
                .setCookie(items.getToken());
        basePage
                .openPage("/knowledge/breeds")
                .chooseRadio("Кошки")
                .clickOnSubmitButton();
        knowledgePage
                .addToComparison("Абиссинская")
                .addToComparison("Австралийская дымчатая")
                .addToComparison("Азиатская короткошерстная / малайская")
                .openComparison();
        basePage
                .clickOnButton("Перейти к сравнению");
        knowledgePage
                .checkComparison("Абиссинская")
                .checkComparison("Австралийская дымчатая")
                .checkComparison("Азиатская короткошерстная / малайская");
        basePage
                .clickOnButton("Очистить список");
    }

    @EnumSource(ItemsForLogin.class)
    @ParameterizedTest(name = "Работа фильтров: {0}")
    @Tag("regression")
    void filterBreedsTest(ItemsForLogin items) {
        BasePage basePage = new BasePage();
        Login login = new Login();
        KnowledgePage knowledgePage = new KnowledgePage();

        login
                .setCookie(items.getToken());
        basePage
                .openPage("/knowledge/breeds")
                .chooseRadio("Кошки")
                .openFilter("Длина шерсти")
                .chooseCheckbox("Короткая")
                .openFilter("Тип шерсти")
                .chooseCheckbox("Волнистая");
        knowledgePage
                .checkCounter("Показать 7");
        basePage
                .clickOnButton("Показать 7 пород");
        knowledgePage
                .checkResult("Девон-рекс")
                .checkResult("Корниш-рекс")
                .checkResult("Орегон-рекс");
    }

    @Test
    @Tag("smoke")
    @Tag("regression")
    @DisplayName("Переход в создание запроса из раздела Каталог пород")
    void createRequestTest() {
        BasePage basePage = new BasePage();
        Login login = new Login();
        KnowledgePage knowledgePage = new KnowledgePage();

        login
                .setCookie(AuthConfig.customerToken);
        basePage
                .openPage("/knowledge/breeds");
        knowledgePage
                .clickOnRequestButton("Абиссинская");
        basePage
                .checkHeader(2,"Создание запроса на подбор животного");
    }

    @EnumSource(ItemsForLogin.class)
    @ParameterizedTest(name = "Просмотр породы: {0}")
    @Tag("regression")
    void viewBreedTest(ItemsForLogin items) {
        BasePage basePage = new BasePage();
        Login login = new Login();
        KnowledgePage knowledgePage = new KnowledgePage();

        login
                .setCookie(items.getToken());
        basePage
                .openPage("/knowledge/breeds");
        knowledgePage
                .openBreed("Абиссинская");
        basePage
                .checkHeader(2,"Абиссинская")
                .clickOnButton("Полная информация")
                .checkHeader(3,"Подробные данные");
    }
}