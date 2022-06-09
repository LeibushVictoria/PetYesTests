package com.petyes.api;

import io.qameta.allure.Step;
import com.petyes.models.AgeRangeData;
import com.petyes.models.CityData;
import com.petyes.models.RequestData;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;

public class Request {

    @Step("Создание запроса по API")
    public int createRequestByAPI(String token, int specialization_id, int price_min, int price_max, boolean important_price,
                                  String address, String coordinate_lat, String coordinate_lng, int color, int sex, int date_from, int date_to,
                                  boolean buy_for_free, boolean is_not_for_breeding, String get_date, int breed_id) {
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
        colors.add(color);
        RequestData requestData = RequestData.builder()
                .specialization_id(specialization_id)
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
                .breed_id(breed_id)
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

    @Step("Удаление запроса по API")
    public void deleteRequestByAPI(String token, int id) {
        RequestData requestData = RequestData.builder()
                .id(id)
                .build();
        given()
                .header("Authorization", "Bearer " + token)
                .contentType("application/json;charset=UTF-8")
                .body(requestData)
                .when()
                .post("https://leibush.pet-no.com/api/pet/buy_request/remove")
                .then()
                .statusCode(200);
    }
}
