package com.petyes.api;

import com.petyes.config.App;
import com.petyes.config.AuthConfig;
import com.petyes.helpers.AllureRestAssuredFilter;
import io.qameta.allure.Step;
import com.petyes.models.PetData;

import java.text.SimpleDateFormat;
import java.util.Date;

import static io.restassured.RestAssured.given;

public class Pet {

    @Step("Создание питомца по API")
    public int createPetByAPI(boolean avatar_id, String nickname, Date dateBirth, int sex, int is_neutered) {
        SimpleDateFormat formaterBirth = new SimpleDateFormat("dd.MM.yyyy");
        String birth = formaterBirth.format(dateBirth);

        int id = given()
                .filter(AllureRestAssuredFilter.withCustomTemplates())
                .contentType("multipart/form-data")
                .header("Authorization", "Bearer " + AuthConfig.breederToken)
                .multiPart("avatar_id", avatar_id)
                .multiPart("specialization_id", App.config.specialization())
                .multiPart("nickname", nickname)
                .multiPart("birth", birth)
                .multiPart("sex", sex)
                .multiPart("is_neutered", is_neutered)
                .multiPart("breed", App.config.breed())
                .when()
                .post("/api/pet/create")
                .then()
                .statusCode(200)
                .extract()
                .body().jsonPath().getJsonObject("pet_id");
        return id;
    }

    @Step("Удаление питомца по API")
    public void deletePetByAPI(int id) {
        PetData petData = PetData.builder()
                .pet_id(id)
                .build();
        given()
                .filter(AllureRestAssuredFilter.withCustomTemplates())
                .contentType("application/json;charset=UTF-8")
                .header("Authorization", "Bearer " + AuthConfig.breederToken)
                .body(petData)
                .when()
                .post("/api/pet/remove")
                .then()
                .statusCode(200);
    }
}
