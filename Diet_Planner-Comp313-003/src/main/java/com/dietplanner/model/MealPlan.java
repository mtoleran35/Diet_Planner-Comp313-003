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
    
    private int totalCalories;
    private int totalCarbohydrate;
    private int totalProtein;
    private int totalFat;

    
    public int getTotalCalories() {
        return totalCalories;
    }

    public int getTotalCarbohydrate() {
        return totalCarbohydrate;
    }

    public int getTotalProtein() {
        return totalProtein;
    }

    public int getTotalFat() {
        return totalFat;
    }
    
    public void setTotalCalories(Integer totalCalories) {
        this.calories = totalCalories;
    }

    public void setTotalCarbohydrate(Integer totalCarbohydrate) {
        this.carbohydrate = totalCarbohydrate;
    }

    public void setTotalProtein(Integer totalProtein) {
        this.protein = totalProtein;
    }

    public void setTotalFat(Integer totalFat) {
        this.fat = totalFat;
    }
    
}
