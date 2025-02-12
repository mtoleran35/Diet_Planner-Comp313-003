package com.mealserviceapi.controller;

import com.mealserviceapi.model.Meal;
import com.mealserviceapi.service.MealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/meals")
public class MealController {

    @Autowired
    private MealService mealService;

    @GetMapping
    public List<Meal> getAllMeals() {
        return mealService.getAllMeals();
    }

    @GetMapping("/{mealId}")
    public Optional<Meal> getMealById(@PathVariable Long mealId) {
        return mealService.getMealById(mealId);
    }

    @PostMapping
    public Meal addMeal(@RequestBody Meal meal) {
        return mealService.saveMeal(meal);
    }

    @PutMapping("/{mealId}")
    public Meal updateMeal(@PathVariable Long mealId, @RequestBody Meal updatedMeal) {
        return mealService.updateMeal(mealId, updatedMeal);
    }

    @DeleteMapping("/{mealId}")
    public String deleteMeal(@PathVariable Long mealId) {
        mealService.deleteMeal(mealId);
        return "Meal deleted successfully!";
    }
}