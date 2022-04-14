package com.petyes.api;

import com.petyes.config.App;
import io.qameta.allure.Step;
import models.AgeRange;
import models.Cities;
import models.RequestData;

import java.util.Collections;

import static io.restassured.RestAssured.given;

public class Request {

    @Step("Создание запроса по API")
    public int createRequestByAPI() {
        Login login = new Login();
        String token = login.loginByAPI(App.config.customerPhoneNumberAPI(), App.config.userPassword());
        Cities cities = Cities.builder()
                .address("Санкт-Петербург")
                .coordinate_lat("59.939084")
                .coordinate_lng("30.315879")
                .build();
        AgeRange ageRange = AgeRange.builder()
                .date_from(0)
                .date_to(6)
                .build();
        RequestData requestData = RequestData.builder()
                .specialization_id(13)
                .price_min(0)
                .price_max(20000)
                .important_price(false)
                .cities(cities)
                .colors(Collections.singleton(1007))
                .age_range(ageRange)
                .buy_for_free(false)
                .is_not_for_breeding(true)
                .get_date("2022-04-14T08:56:15.706Z")
                .breed_id(597)
                .build();
        int id = given()
                .header("Authorization", "Bearer " + token)
                .contentType("application/json;charset=UTF-8")
                .body(requestData)
                .when()
                .post("https://leibush.pet-no.com/api/pet/buy_request/add")
                .then()
                .statusCode(200)
                .extract()
                .body().jsonPath().getJsonObject("id");
        return id;
    }
}
