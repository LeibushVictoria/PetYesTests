package com.petyes.api;

import com.petyes.config.App;
import com.petyes.models.BlogData;
import io.qameta.allure.Step;

import static io.restassured.RestAssured.given;

public class Blog {
    @Step("Создать категорию статьи по API")
    public int addBlogCategoryByAPI(String name, String color) {
        Login login = new Login();
        String token = login.loginByAPI(App.config.managerPhoneNumber(), App.config.userPassword());

        BlogData category = BlogData.builder()
                .name(name)
                .color(color)
                .build();
        int category_id = given()
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

    @Step("Удалить категорию статьи по API")
    public void deleteBlogCategoryByAPI(int category_id) {
        Login login = new Login();
        String token = login.loginByAPI(App.config.managerPhoneNumber(), App.config.userPassword());

        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .post("/api/blog/category/delete/" + category_id)
                .then()
                .statusCode(200);
    }
}
