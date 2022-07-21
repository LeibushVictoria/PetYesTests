package com.petyes.api;

import com.petyes.helpers.AllureRestAssuredFilter;
import io.qameta.allure.Step;
import com.petyes.models.LoginData;
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
                .filter(AllureRestAssuredFilter.withCustomTemplates())
                .contentType("application/json;charset=UTF-8")
                .body(user)
                .when()
                .post("/api/login")
                .then()
                .statusCode(200)
                .extract().as(LoginData.class).getToken();
        return token;
    }

    @Step("Установить куку в браузере")
    public void setCookie(String token) {
        open("/_nuxt/img/ic_heart.78eb8bf.svg");
        getWebDriver().manage().addCookie(new Cookie("auth._token.local", token));
    }

    @Step("Получить id авторизованного юзера")
    public int getUserId(String token) {
        Map<String, Integer> user = given()
                .filter(AllureRestAssuredFilter.withCustomTemplates())
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/api/user")
                .then()
                .statusCode(200)
                .extract().body().jsonPath().getMap("user");
        int user_id = user.get("id");
        return user_id;
    }
}
