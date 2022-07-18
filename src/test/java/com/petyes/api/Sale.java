package com.petyes.api;

import com.petyes.config.App;
import com.petyes.models.PetData;
import io.qameta.allure.Step;

import static io.restassured.RestAssured.given;

public class Sale {
    @Step("Продать питомца по API")
    public int salePetByAPI(boolean deliverable, boolean sell_for_free, boolean is_not_for_breeding, int not_for_breeding_price, int pet_id) {
        Login login = new Login();
        String token = login.loginByAPI(App.config.breederPhoneNumberAPI(), App.config.userPassword());

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
                .post("/api/pet/sell_request/add")
                .then()
                .statusCode(200)
                .extract()
                .body().jsonPath().getJsonObject("id");
        return id;
    }

    @Step("Снять питомца с продажи по API")
    public void cancelPetSaleByAPI(int pet_id) {
        Login login = new Login();
        String token = login.loginByAPI(App.config.breederPhoneNumberAPI(), App.config.userPassword());

        PetData petData = PetData.builder()
                .pet_id(pet_id)
                .build();
        given()
                .contentType("application/json;charset=UTF-8")
                .header("Authorization", "Bearer " + token)
                .body(petData)
                .when()
                .post("/api/pet/sell_request/remove")
                .then()
                .statusCode(200);
    }
}
