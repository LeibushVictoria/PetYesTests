package com.petyes.tests;

import com.petyes.api.Login;
import com.petyes.config.App;
import com.petyes.pages.BasePage;
import com.petyes.pages.KnowledgePage;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Feature("Каталог пород")
public class KnowledgeTests extends TestBase {

    @Test
    @DisplayName("Подобрать породу")
    void chooseABreedTest() {
        BasePage basePage = new BasePage();
        Login login = new Login();
        KnowledgePage knowledgePage = new KnowledgePage();

        login
                .loginByAPI(App.config.customerPhoneNumberAPI(), App.config.userPassword());
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
                .checkHeaderH2("Вам подходят следующие породы");
        knowledgePage
                .checkBreedsDisplay("Бенгальская");
    }

    @Test
    @DisplayName("Сравнить породы")
    void compareBreedsTest() {
        BasePage basePage = new BasePage();
        Login login = new Login();
        KnowledgePage knowledgePage = new KnowledgePage();

        login
                .loginByAPI(App.config.customerPhoneNumberAPI(), App.config.userPassword());
        basePage
                .openPage("/knowledge/breeds");
        knowledgePage
                .addToComparison("Абиссинская")
                .addToComparison("Австралийская дымчатая")
                .addToComparison("Австралийский мист")
                .openComparison();
        basePage
                .clickOnButton("Перейти к сравнению");
    }

    @Test
    @DisplayName("Работа фильтров")
    void filterBreedsTest() {
        BasePage basePage = new BasePage();
        Login login = new Login();
        KnowledgePage knowledgePage = new KnowledgePage();

        login
                .loginByAPI(App.config.customerPhoneNumberAPI(), App.config.userPassword());
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
    @DisplayName("Переход в создание запроса из раздела Каталог пород")
    void createRequestTest() {
        BasePage basePage = new BasePage();
        Login login = new Login();
        KnowledgePage knowledgePage = new KnowledgePage();

        login
                .loginByAPI(App.config.customerPhoneNumberAPI(), App.config.userPassword());
        basePage
                .openPage("/knowledge/breeds");
        knowledgePage
                .clickOnRequestButton("Абиссинская");
        basePage
                .checkHeaderH2("Создание запроса на подбор животного");
    }

    @Test
    @DisplayName("Просмотр породы")
    void viewBreedTest() {
        BasePage basePage = new BasePage();
        Login login = new Login();
        KnowledgePage knowledgePage = new KnowledgePage();

        login
                .loginByAPI(App.config.customerPhoneNumberAPI(), App.config.userPassword());
        basePage
                .openPage("/knowledge/breeds");
        knowledgePage
                .openBreed("Абиссинская");
        basePage
                .checkHeaderH2("Абиссинская")
                .clickOnButton("Полная информация")
                .checkHeaderH3("Подробные данные");
    }
}