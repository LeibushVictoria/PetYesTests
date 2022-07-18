package com.petyes.api;

import com.petyes.config.App;
import com.petyes.models.ResponseData;
import io.qameta.allure.Step;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;

public class Response {

    @Step("Отклик на запрос по API")
    public void responseByAPI(int request_id, int pet_id) {
        Login login = new Login();
        String token = login.loginByAPI(App.config.breederPhoneNumberAPI(), App.config.userPassword());

        ArrayList<Integer> pet_ids = new ArrayList<>();
        pet_ids.add(pet_id);
        ResponseData responseData = ResponseData.builder()
                .request_id(request_id)
                .pet_ids(pet_ids)
                .build();
        given()
                .header("Authorization", "Bearer " + token)
                .contentType("application/json;charset=UTF-8")
                .body(responseData)
                .when()
                .post("/api/response/add")
                .then()
                .statusCode(200);
    }
}
