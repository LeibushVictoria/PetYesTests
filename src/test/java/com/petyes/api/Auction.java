package com.petyes.api;

import io.qameta.allure.Step;

import static io.restassured.RestAssured.given;

public class Auction {

    @Step("Создание аукциона по API")
    public int createAuctionByAPI(String token, String started_at, String finished_at, int start_cost, int availability_type,
                                  int blitz_price, boolean auto_renew, boolean is_fixed) {
        Pet pet = new Pet();
        int pet_id = pet.createPetByAPI(token, false, 13, "autoTestAuctionCat", "10.03.2022", 0, 1007, 1,597);
        int id = given()
                .contentType("multipart/form-data")
                .header("Authorization", "Bearer " + token)
                .multiPart("started_at", started_at)
                .multiPart("finished_at", finished_at)
                .multiPart("start_cost", start_cost)
                .multiPart("availability_type", availability_type)
                .multiPart("blitz_price", blitz_price)
                .multiPart("auto_renew", auto_renew)
                .multiPart("is_fixed", is_fixed)
                .multiPart("lots[0][start_cost]", start_cost)
                .multiPart("lots[0][blitz_price]", blitz_price)
                .multiPart("lots[0][lot_content][0][pet]", pet_id)
                .when()
                .post("https://leibush.pet-no.com/api/auction/create_and_publish")
                .then()
                .statusCode(200)
                .extract()
                .body().jsonPath().getJsonObject("id");
        return id;
    }
}