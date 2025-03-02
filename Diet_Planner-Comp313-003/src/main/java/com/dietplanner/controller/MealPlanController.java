package com.dietplanner.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dietplanner.dto.Meal;
import com.dietplanner.dto.UserInfo;
import com.dietplanner.model.User;
import com.dietplanner.service.MealPlanApiService;
import com.dietplanner.service.UserService;

@Controller
@RequestMapping("/createplan")
public class MealPlanController {
	private final MealPlanApiService mealPlanApiService;
	
	@Autowired
    private UserService userService;
	
	public MealPlanController(MealPlanApiService mealPlanApiService) {
		this.mealPlanApiService = mealPlanApiService;
	}
	
	@PostMapping("/generate-mealplan")
	public String generateMealPlanForm(@AuthenticationPrincipal UserDetails userDetails, Model model) {
		if (userDetails != null) {
			//Get user details
            User appUser = userService.findByUsername(userDetails.getUsername());
            
            //Create userInfo dto to store needed user info for generating a meal plan
            UserInfo userInfo = new UserInfo();
            userInfo.setId(appUser.getId());
            userInfo.setDietPreference(appUser.getDietPreference());
            userInfo.setWeight(appUser.getWeight());
            userInfo.setCaloricIntakeGoal(appUser.getCaloricIntakeGoal());
            
            //Get list of meals
            List<Meal> meals = mealPlanApiService.generateMealPlan(userInfo);
            //Get total values of macros
            int totalCalories = 0;
            int totalCarb = 0;
            int totalFat = 0;
            int totalProtein = 0;
            for (Meal meal : meals) {
            	totalCalories += meal.getCalories();
            	totalCarb += meal.getCarbohydrate();
            	totalFat += meal.getFat();
            	totalProtein += meal.getProtein();
            }
            //Pass values to model
            model.addAttribute("meals", meals);
            model.addAttribute("totalCalories", totalCalories);
            model.addAttribute("totalCarb", totalCarb);
            model.addAttribute("totalFat", totalFat);
            model.addAttribute("totalProtein", totalProtein);
        }
		return "fragments/meals";
	}
}
