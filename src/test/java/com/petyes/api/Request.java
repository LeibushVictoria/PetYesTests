package com.petyes.api;

import com.petyes.config.App;
import com.petyes.helpers.AllureRestAssuredFilter;
import io.qameta.allure.Step;
import com.petyes.models.AgeRangeData;
import com.petyes.models.CityData;
import com.petyes.models.RequestData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static io.restassured.RestAssured.given;

public class Request {

    @Step("Создание запроса по API")
    public int createRequestByAPI(int price_min, int price_max, boolean important_price,
                                  String address, String coordinate_lat, String coordinate_lng, int sex, int date_from, int date_to,
                                  boolean buy_for_free, boolean is_not_for_breeding, Date date) {
        Login login = new Login();
        String token = login.loginByAPI(App.config.customerPhoneNumber(), App.config.userPassword());

        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String get_date = formater.format(date);

        CityData cityData = CityData.builder()
                .address(address)
                .coordinate_lat(coordinate_lat)
                .coordinate_lng(coordinate_lng)
                .build();
        ArrayList<CityData> cities = new ArrayList<>();
        cities.add(cityData);
        AgeRangeData ageRangeData = AgeRangeData.builder()
                .date_from(date_from)
                .date_to(date_to)
                .build();
        ArrayList<AgeRangeData> ageRange = new ArrayList<>();
        ageRange.add(ageRangeData);
        ArrayList<Integer> colors = new ArrayList<>();
        RequestData requestData = RequestData.builder()
                .specialization_id(App.config.specialization())
                .price_min(price_min)
                .price_max(price_max)
                .important_price(important_price)
                .cities(cities)
                .colors(colors)
                .sex(sex)
                .age_range(ageRange)
                .buy_for_free(buy_for_free)
                .is_not_for_breeding(is_not_for_breeding)
                .get_date(get_date)
                .breed_id(App.config.breed())
                .build();
        int id = given()
                .filter(AllureRestAssuredFilter.withCustomTemplates())
                .header("Authorization", "Bearer " + token)
                .contentType("application/json;charset=UTF-8")
                .body(requestData)
                .when()
                .post("/api/pet/buy_request/add")
                .then()
                .statusCode(200)
                .extract()
                .body().jsonPath().getJsonObject("id");
        return id;
    }

    @Step("Удаление запроса по API")
    public void deleteRequestByAPI(int id) {
        Login login = new Login();
        String token = login.loginByAPI(App.config.customerPhoneNumber(), App.config.userPassword());

        RequestData requestData = RequestData.builder()
                .id(id)
                .build();
        given()
                .filter(AllureRestAssuredFilter.withCustomTemplates())
                .header("Authorization", "Bearer " + token)
                .contentType("application/json;charset=UTF-8")
                .body(requestData)
                .when()
                .post("/api/pet/buy_request/remove")
                .then()
                .statusCode(200);
    }
}
