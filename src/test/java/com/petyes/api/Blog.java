package com.petyes.api;

import com.petyes.config.App;
import com.petyes.helpers.AllureRestAssuredFilter;
import com.petyes.models.BlogData;
import io.qameta.allure.Step;

import java.io.File;

import static io.restassured.RestAssured.given;

public class Blog {
    @Step("Создать категорию статьи по API")
    public int addBlogCategoryByAPI(String name, String color) {
        Login login = new Login();
        String token = login.loginByAPI(App.config.adminPhoneNumber(), App.config.adminPassword());

        BlogData category = BlogData.builder()
                .name(name)
                .color(color)
                .build();
        int category_id = given()
                .filter(AllureRestAssuredFilter.withCustomTemplates())
                .header("Authorization", "Bearer " + token)
                .contentType("application/json")
                .body(category)
                .when()
                .post("/api/blog/category/add")
                .then()
                .statusCode(200)
                .extract()
                .body().jsonPath().getJsonObject("category_id");
        return category_id;
    }

    @Step("Создать статью по API")
    public int addArticleByAPI(String name, String text, File file, String annotation, int category_id, int specialization_id) {
        Login login = new Login();
        String token = login.loginByAPI(App.config.adminPhoneNumber(), App.config.adminPassword());

        int article_id = given()
                .filter(AllureRestAssuredFilter.withCustomTemplates())
                .contentType("multipart/form-data")
                .header("Authorization", "Bearer " + token)
                .multiPart("name", name)
                .multiPart("text", "{\"type\":\"doc\",\"content\":[{\"type\":\"paragraph\",\"attrs\":{\"textAlign\":\"left\"}," +
                        "\"content\":[{\"type\":\"text\",\"text\":\"" + text + "\"}]}]}")
                .multiPart("photo", file)
                .multiPart("annotation", annotation)
                .multiPart("is_approved", true)
                .multiPart("is_published", true)
                .multiPart("category_id", category_id)
                .multiPart("specialization_id", specialization_id)
                .when()
                .post("/api/blog/article_version/add")
                .then()
                .statusCode(200)
                .extract()
                .body().jsonPath().getJsonObject("id");
        return article_id;
    }

    @Step("Удалить категорию статьи по API")
    public void deleteBlogCategoryByAPI(int category_id) {
        Login login = new Login();
        String token = login.loginByAPI(App.config.adminPhoneNumber(), App.config.adminPassword());

        given()
                .filter(AllureRestAssuredFilter.withCustomTemplates())
                .header("Authorization", "Bearer " + token)
                .when()
                .post("/api/blog/category/delete/" + category_id)
                .then()
                .statusCode(200);
    }

    @Step("Удалить статью по API")
    public void deleteArticleByAPI(int article_id) {
        Login login = new Login();
        String token = login.loginByAPI(App.config.adminPhoneNumber(), App.config.adminPassword());

        given()
                .filter(AllureRestAssuredFilter.withCustomTemplates())
                .header("Authorization", "Bearer " + token)
                .when()
                .post("/api/blog/article/delete/" + article_id)
                .then()
                .statusCode(200);
    }
}
