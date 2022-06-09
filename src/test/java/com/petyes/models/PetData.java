package com.petyes.models;

import io.qameta.allure.internal.shadowed.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class PetData {
    private boolean deliverable;
    private boolean sell_for_free;
    private boolean is_not_for_breeding;
    private int not_for_breeding_price;
    private int pet_id;
    private int auction_id;
}
