package com.petyes.api;

import com.petyes.config.App;
import com.petyes.helpers.AllureRestAssuredFilter;
import com.petyes.models.PetData;
import com.petyes.pages.components.CalendarComponent;
import io.qameta.allure.Step;

import java.text.SimpleDateFormat;
import java.util.Date;

import static io.restassured.RestAssured.given;

public class Auction {

    @Step("Создание аукциона по API")
    public int createAuctionByAPI(Date start, Date finish, int start_cost, int availability_type, int blitz_price, boolean auto_renew, boolean is_fixed) {
        Login login = new Login();
        Pet pet = new Pet();
        CalendarComponent calendarComponent = new CalendarComponent();
        SimpleDateFormat formaterAuction = new SimpleDateFormat("dd.MM.yyyy HH:mm z");

        Date birth = calendarComponent.getOtherDate(-20);

        String started_at = formaterAuction.format(start);
        String finished_at = formaterAuction.format(finish);

        String token = login.loginByAPI(App.config.breederPhoneNumber(), App.config.userPassword());
        int pet_id = pet.createPetByAPI(false, "autoTestAuctionCat", birth, 0, 1);

        int id = given()
                .filter(AllureRestAssuredFilter.withCustomTemplates())
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
                .post("/api/auction/create_and_publish")
                .then()
                .statusCode(200)
                .extract()
                .body().jsonPath().getJsonObject("id");
        return id;
    }

    @Step("Удаление аукциона по API")
    public void deleteAuctionByAPI(int id) {
        Login login = new Login();
        String token = login.loginByAPI(App.config.breederPhoneNumber(), App.config.userPassword());

        PetData petData = PetData.builder()
                .auction(id)
                .build();
        given()
                .filter(AllureRestAssuredFilter.withCustomTemplates())
                .contentType("application/json;charset=UTF-8")
                .header("Authorization", "Bearer " + token)
                .body(petData)
                .when()
                .log().all()
                .delete("/api/auction/delete")
                .then()
                .log().all()
                .statusCode(204);
    }
}