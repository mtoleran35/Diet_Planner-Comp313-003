package com.dietplanner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dietplanner.service.MealPlanApiService;

@Controller
@RequestMapping("/mealplans")
public class MealPlanController {
	private final MealPlanApiService mealPlanApiService;
	
	public MealPlanController(MealPlanApiService mealPlanApiService) {
		this.mealPlanApiService = mealPlanApiService;
	}
	
	@GetMapping("/generate-mealplan")
	public String generateMealPlanForm(Model model) {
		return "generate-mealplan";
	}
}
