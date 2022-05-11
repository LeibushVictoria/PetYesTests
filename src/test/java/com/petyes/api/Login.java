package com.petyes.api;

import io.qameta.allure.Step;
import models.LoginData;
import org.openqa.selenium.Cookie;

import java.util.Map;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.restassured.RestAssured.given;

public class Login {

    @Step("Авторизация по API")
    public String loginByAPI(String phone, String password) {
        LoginData user = LoginData.builder()
                .phone(phone)
                .password(password)
                .remember(true)
                .build();
        String token = given()
                .contentType("application/json;charset=UTF-8")
                .body(user)
                .when()
                .post("https://leibush.pet-no.com/api/login")
                .then()
                .statusCode(200)
                .extract().as(LoginData.class).getToken();

        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("https://leibush.pet-no.com/api/user")
                .then()
                .statusCode(200);

        open("/_nuxt/img/ic_heart.78eb8bf.svg");
        getWebDriver().manage().addCookie(
                new Cookie("auth._token.local", token));
        return token;
    }

    @Step("Получить id авторизованного юзера")
    public int getUserId(String token) {
        Map<String, Integer> user = given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("https://leibush.pet-no.com/api/user")
                .then()
                .statusCode(200)
                .extract().body().jsonPath().getMap("user");
        int user_id = user.get("id");
        return user_id;
    }
}
