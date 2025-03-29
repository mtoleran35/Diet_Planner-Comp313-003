package com.mealserviceapi.service;

import com.mealserviceapi.model.Meal;
import com.mealserviceapi.repository.MealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MealService {

    @Autowired
    private MealRepository mealRepository;

    public List<Meal> getAllMeals() {
        return mealRepository.findAll();
    }

    public Optional<Meal> getMealById(Long mealId) {
        return mealRepository.findById(mealId);
    }

    public Meal saveMeal(Meal meal) {
        return mealRepository.save(meal);
    }

    public Meal updateMeal(Long mealId, Meal updatedMeal) {
        return mealRepository.findById(mealId)
                .map(meal -> {
                    meal.setMealName(updatedMeal.getMealName());
                    meal.setCalories(updatedMeal.getCalories());
                    meal.setCarbohydrate(updatedMeal.getCarbohydrate());
                    meal.setFat(updatedMeal.getFat());
                    meal.setProtein(updatedMeal.getProtein());
                    return mealRepository.save(meal);
                })
                .orElseThrow(() -> new RuntimeException("Meal not found"));
    }

    public void deleteMeal(Long mealId) {
        Meal meal = mealRepository.findById(mealId)
                .orElseThrow(() -> new RuntimeException("Meal not found"));
        meal.setStatus("Inactive"); // Update the status
        mealRepository.save(meal); // Save the updated meal
    }

 // Method to change the status of a meal
    public Meal changeStatus(Long mealId) {
        Meal meal = mealRepository.findById(mealId)
                .orElseThrow(() -> new RuntimeException("Meal not found"));

        // If status is Inactive, change it to Active
        if ("Inactive".equalsIgnoreCase(meal.getStatus())) {
            meal.setStatus("Active");
            return mealRepository.save(meal);
        }

        // If the meal is already Active, no need to change, just return the same meal
        return meal;
    }

}