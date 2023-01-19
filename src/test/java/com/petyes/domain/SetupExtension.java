package com.petyes.domain;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.petyes.api.Blog;
import com.petyes.api.Pet;
import com.petyes.api.Request;
import com.petyes.api.Sale;
import com.petyes.config.App;
import com.petyes.helpers.DriverSettings;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;

public class SetupExtension extends BaseSetupExtension {
    @Override
    void setup() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
        DriverSettings.configure();
        Configuration.baseUrl = App.config.webUrl();
        RestAssured.baseURI = App.config.apiUrl();

        DataBuilder dataBuilder = new DataBuilder();

        dataBuilder.setBreederToken();
        dataBuilder.setBreederId();
        dataBuilder.setCustomerToken();
        dataBuilder.setCustomerId();
        dataBuilder.setAdminToken();

        dataBuilder.setPetId();
        dataBuilder.setPetForSaleId();
        dataBuilder.setSaleId();

        dataBuilder.setRequestId();

        dataBuilder.setBlogCategoryId();
        dataBuilder.setArticleId();
    }

    @Override
    public void close() throws Throwable {
        Pet pet = new Pet();
        Sale sale = new Sale();
        Request request = new Request();
        Blog blog = new Blog();

        sale.cancelPetSaleByAPI(DataBuilder.petForSale_id);
        pet.deletePetByAPI(DataBuilder.pet_id);
        pet.deletePetByAPI(DataBuilder.petForSale_id);

        request.deleteRequestByAPI(DataBuilder.request_id);

        blog.deleteArticleByAPI(DataBuilder.article_id);
        blog.deleteBlogCategoryByAPI(DataBuilder.category_id);
    }
}
