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
		//Details should be from user account
		
		//HARD-CODED USER DETAILS
		//Diet Preference
		String dietPreference = "High Protein";
		//Weight
		double weight = 152;
		//Caloric Intake Goal
		int caloricIntakeGoal = 2000;
		
		return mealPlanService.generateMealPlan(userInfo); //Could pass Account model instead
		
	}
	
}
