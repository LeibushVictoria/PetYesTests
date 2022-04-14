package models;

import io.qameta.allure.internal.shadowed.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestData {
    private int specialization_id;
    private int price_min;
    private int price_max;
    private boolean important_price;
    private Cities cities;
    private Set<Integer> colors;
    private String sex;
    private AgeRange age_range;
    private boolean buy_for_free;
    private boolean is_not_for_breeding;
    private boolean views;
    private boolean has_passport;
    private boolean is_vaccined;
    private boolean is_castrated;
    private boolean is_chipped;
    private String description;
    private String get_date;
    private int breed_id;
}