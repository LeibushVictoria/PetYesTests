package com.petyes.models;

import io.qameta.allure.internal.shadowed.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseData {
    private int request_id;
    private ArrayList<Integer> pet_ids;
}
