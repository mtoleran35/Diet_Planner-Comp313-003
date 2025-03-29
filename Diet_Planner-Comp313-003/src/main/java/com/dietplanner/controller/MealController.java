package com.dietplanner.controller;

import com.dietplanner.dto.Meal;
import com.dietplanner.service.MealApiService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/meals")
@PreAuthorize("hasRole('ADMIN')")
public class MealController {

    private final MealApiService mealApiService;

    public MealController(MealApiService mealApiService) {
        this.mealApiService = mealApiService;
    }

    // ✅ Load Admin Dashboard and Fetch Meals
    @GetMapping("/admin-dashboard")
    public String adminDashboard(Model model) {
        List<Meal> meals = mealApiService.fetchAllMeals();
        model.addAttribute("meals", meals != null ? meals : Collections.emptyList()); // ✅ Prevents null issues
        return "admin-dashboard";
    }

    // ✅ Show Add Meal Form
    @GetMapping("/add")
    public String showAddMealForm(Model model) {
        model.addAttribute("meal", new Meal());
        return "add-meal";
    }

    // ✅ Process Meal Creation & Redirect to Admin Dashboard
    @PostMapping("/add")
    public String addMeal(@ModelAttribute Meal meal) {
        mealApiService.addMeal(meal);
        return "redirect:/meals/admin-dashboard"; // Correct redirection
    }

    // ✅ Show Edit Meal Form
    @GetMapping("/edit/{mealId}")
    public String showEditMealForm(@PathVariable Long mealId, Model model) {
        Meal meal = mealApiService.getMealById(mealId);
        if (meal == null) {
            return "redirect:/meals/admin-dashboard"; // Handle missing meal
        }
        model.addAttribute("meal", meal);
        return "edit-meal";
    }
    
    // ✅ Canceling Edit
    @GetMapping("/edit/cancel")
    public String cancelEdit() {
        return "redirect:/meals/admin-dashboard";
    }

    // ✅ Process Meal Update & Redirect
    @PostMapping("/edit/{mealId}")
    public String updateMeal(@PathVariable Long mealId, @ModelAttribute Meal meal) {
        mealApiService.updateMeal(mealId, meal);
        return "redirect:/meals/admin-dashboard";
    }

    // ✅ Delete Meal & Redirect
    @GetMapping("/delete/{mealId}")
    public String deleteMeal(@PathVariable Long mealId) {
        mealApiService.deleteMeal(mealId);
        return "redirect:/meals/admin-dashboard";
    }
    
    @GetMapping("/statusUpdate/{mealId}")
    public String statusMealUpdate(@PathVariable Long mealId) {
    	mealApiService.changeStatus(mealId);
        return "redirect:/meals/admin-dashboard";
    }
}