package com.mealserviceapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mealserviceapi.model.Meal;
import com.mealserviceapi.model.UserInfo;
import com.mealserviceapi.service.MealPlanService;

@RestController
@RequestMapping("/api/mealplans")
public class MealPlanController {

	@Autowired
	MealPlanService mealPlanService;
	
	@PostMapping()
	public List<Meal> generateMealPlan(@RequestBody UserInfo userInfo) {
		//Takes user info and uses it as basis to generate meal plan
		return mealPlanService.generateMealPlan(userInfo); 
	}
	
	@PostMapping("/savemealplan")
	public void saveMealPlan(@RequestBody UserInfo userInfo) {
		mealPlanService.saveMealPlan(userInfo);
	}
	
}
