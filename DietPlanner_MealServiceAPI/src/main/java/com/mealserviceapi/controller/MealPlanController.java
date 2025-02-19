package com.mealserviceapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mealserviceapi.model.Meal;
import com.mealserviceapi.service.MealPlanService;

@RestController
@RequestMapping("/api/mealplans")
public class MealPlanController {

	@Autowired
	MealPlanService mealPlanService;
	
	@GetMapping()
	public List<Meal> generateMealPlan() {
		//Details should be from user account
		
		//HARD-CODED USER DETAILS
		//Diet Preference
		String dietPreference = "High Protein";
		//Weight
		double weight = 152;
		//Caloric Intake Goal
		int caloricIntakeGoal = 2000;
		
		return mealPlanService.generateMealPlan(dietPreference,weight,caloricIntakeGoal); //Could pass Account model instead
		
	}
	
//	//Proper implementation
//	@GetMapping()
//	public List<Meal> getAllMealDetails(@RequestBody User currentUser) {
//
//		return mealPlanService.generateMealPlan(currentUser); //Could pass Account model instead
//		
//	}
	
}
