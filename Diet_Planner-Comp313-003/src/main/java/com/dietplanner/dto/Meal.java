package com.dietplanner.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Meal {
    private Long mealId;
    private String mealName;
    private int calories;
    private int carbohydrate;
    private int fat;
    private int protein;
    private String status;

}