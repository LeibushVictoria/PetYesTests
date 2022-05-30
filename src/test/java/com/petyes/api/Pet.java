package com.petyes.api;

import io.qameta.allure.Step;
import com.petyes.models.PetData;

import static io.restassured.RestAssured.given;

public class Pet {

    @Step("Создание питомца по API")
    public int createPetByAPI(String token, boolean avatar_id, int specialization_id, String nickname, String birth, int sex, int color, int is_neutered, int breed) {
        int id = given()
                .contentType("multipart/form-data")
                .header("Authorization", "Bearer " + token)
                .multiPart("avatar_id", avatar_id)
                .multiPart("specialization_id", specialization_id)
                .multiPart("nickname", nickname)
                .multiPart("birth", birth)
                .multiPart("sex", sex)
                .multiPart("color", color)
                .multiPart("is_neutered", is_neutered)
                .multiPart("breed", breed)
                .when()
                .post("https://leibush.pet-no.com/api/pet/create")
                .then()
                .statusCode(200)
                .extract()
                .body().jsonPath().getJsonObject("pet_id");
        return id;
    }

    @Step("Продать питомца по API")
    public int sellPetByAPI(String token, boolean deliverable, boolean sell_for_free, boolean is_not_for_breeding, int not_for_breeding_price, int pet_id) {
        PetData petData = PetData.builder()
                .deliverable(deliverable)
                .sell_for_free(sell_for_free)
                .is_not_for_breeding(is_not_for_breeding)
                .not_for_breeding_price(not_for_breeding_price)
                .pet_id(pet_id)
                .build();
        int id = given()
                .contentType("application/json;charset=UTF-8")
                .header("Authorization", "Bearer " + token)
                .body(petData)
                .when()
                .post("https://leibush.pet-no.com/api/pet/sell_request/add")
                .then()
                .statusCode(200)
                .extract()
                .body().jsonPath().getJsonObject("id");
        return id;
    }
}
