package com.dietplanner.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MealPlan {
    private String username;
    private String fullname;
    private String dietPreference;
    private Integer caloricIntakeGoal;
    private Double weight;
    private String mealPlanName;
    private String mealName;
    private Integer calories;
    private Integer carbohydrate;
    private Integer protein;
    private Integer fat;
    private Integer mealQuantity;
    private String assignedDay;
    
}
