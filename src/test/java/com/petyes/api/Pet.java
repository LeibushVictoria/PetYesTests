package com.petyes.api;

import com.petyes.config.App;
import io.qameta.allure.Step;

import static io.restassured.RestAssured.given;

public class Pet {

    @Step("Создание питомца по API")
    public int createPetByAPI(boolean avatar_id, int specialization_id, String nickname, String birth, int sex, int is_neutered, int breed) {
        Login login = new Login();
        String token = login.loginByAPI(App.config.breederPhoneNumberAPI(), App.config.userPassword());
        int id = given()
                .contentType("multipart/form-data")
                .header("Authorization", "Bearer " + token)
                .multiPart("avatar_id", avatar_id)
                .multiPart("specialization_id", specialization_id)
                .multiPart("nickname", nickname)
                .multiPart("birth", birth)
                .multiPart("sex", sex)
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
}
