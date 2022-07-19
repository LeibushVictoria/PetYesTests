package com.petyes.api;

import com.petyes.config.App;
import io.qameta.allure.Step;
import com.petyes.models.PetData;

import java.text.SimpleDateFormat;
import java.util.Date;

import static io.restassured.RestAssured.given;

public class Pet {

    @Step("Создание питомца по API")
    public int createPetByAPI(boolean avatar_id, int specialization_id, String nickname, Date dateBirth, int sex, int color, int is_neutered, int breed) {
        Login login = new Login();
        String token = login.loginByAPI(App.config.breederPhoneNumber(), App.config.userPassword());

        SimpleDateFormat formaterBirth = new SimpleDateFormat("dd.MM.yyyy");
        String birth = formaterBirth.format(dateBirth);

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
                .post("/api/pet/create")
                .then()
                .statusCode(200)
                .extract()
                .body().jsonPath().getJsonObject("pet_id");
        return id;
    }

    @Step("Удаление питомца по API")
    public void deletePetByAPI(int id) {
        Login login = new Login();
        String token = login.loginByAPI(App.config.breederPhoneNumber(), App.config.userPassword());

        PetData petData = PetData.builder()
                .pet_id(id)
                .build();
        given()
                .contentType("application/json;charset=UTF-8")
                .header("Authorization", "Bearer " + token)
                .body(petData)
                .when()
                .post("/api/pet/remove")
                .then()
                .statusCode(200);
    }
}
