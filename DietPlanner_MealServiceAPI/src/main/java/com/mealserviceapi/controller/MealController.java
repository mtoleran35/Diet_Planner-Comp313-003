package com.mealserviceapi.controller;

import com.mealserviceapi.model.Meal;
import com.mealserviceapi.service.MealService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public Meal addMeal(@Valid @RequestBody Meal meal) {
        if (meal.getStatus() == null || meal.getStatus().isBlank()) {
            meal.setStatus("Active");
        }
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
    
    @PutMapping("/change-status/{mealId}")
    public ResponseEntity<Meal> changeStatus(@PathVariable Long mealId) {
        Optional<Meal> mealOpt = mealService.getMealById(mealId);
        if (mealOpt.isPresent()) {
            Meal meal = mealOpt.get();
            // Toggle the status between "Active" and "Inactive"
            meal.setStatus(meal.getStatus().equals("Active") ? "Inactive" : "Active");
            Meal updatedMeal = mealService.saveMeal(meal);
            
            return ResponseEntity.ok(updatedMeal); // Return updated meal with 200 status
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Return 404 if not found
        }
   
    }

    
}