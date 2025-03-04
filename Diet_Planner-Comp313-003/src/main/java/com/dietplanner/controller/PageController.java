package com.dietplanner.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.dietplanner.dto.Meal;
import com.dietplanner.dto.UserInfo;
import com.dietplanner.model.User;
import com.dietplanner.service.UserService;

@Controller
public class PageController {
	
	@Autowired
    private UserService userService;

    // Display settings page with current user details
    @GetMapping("/settings")
    public String settingsPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (userDetails != null) {
            User appUser = userService.findByUsername(userDetails.getUsername());
            model.addAttribute("user", appUser);
            model.addAttribute("username", appUser.getUsername());
        }
        return "settings";  // Load settings page
    }

    @GetMapping("/myplans")
    public String myPlansPage() {
        return "myplans"; // Renders my-plans.html
    }

    @GetMapping("/createplan")
    public String createPlanPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
    	//Pass user info to model object, then display on page
    	if (userDetails != null) {
            User appUser = userService.findByUsername(userDetails.getUsername());
            List<Meal> meals = new ArrayList<>();
            
            //Create userInfo dto to store needed user info for generating a meal plan
            UserInfo userInfo = new UserInfo();
            userInfo.setId(appUser.getId());
            userInfo.setDietPreference(appUser.getDietPreference());
            userInfo.setWeight(appUser.getWeight());
            userInfo.setCaloricIntakeGoal(appUser.getCaloricIntakeGoal());
            
            //Get user details to display appropriate help text
            String dietPreference = appUser.getDietPreference();
            double weight = appUser.getWeight();
            int caloricIntakeGoal = appUser.getCaloricIntakeGoal();
            
            //Determine help text depending on diet preference
            String helpText = "";
            
            switch(dietPreference) {
            	case "HIGH PROTEIN":
            		helpText = "Total protein amount should be at least >=  " + weight + "g";
            		break;
            	case "LOW CARB":
            		helpText = "Total calories from carb should not exceed  " + (caloricIntakeGoal * .15) + " kcal (Within 15% of caloric intake goal) - Formula: Total Carb(g) * 4";
            		break;
            	case "KETO":
            		helpText = "Total calories from fat should not exceed  " + (caloricIntakeGoal * .7) + " kcal (Within 70% of caloric intake goal) - Formula: Total Fat(g) * 9";
            		break;
            		
            }
            
            //Pass values to model
            model.addAttribute("user", appUser);
            model.addAttribute("username", appUser.getUsername());
            model.addAttribute("caloricIntakeGoal", caloricIntakeGoal);
            model.addAttribute("dietPreference", dietPreference);
            model.addAttribute("helpText", helpText);
            model.addAttribute("weight", weight);
            model.addAttribute("meals", meals);
            model.addAttribute("totalCalories", 0);
            model.addAttribute("totalCarb", 0);
            model.addAttribute("totalFat", 0);
            model.addAttribute("totalProtein", 0);
            //Pass userInfo to model. To be used in saving meal plan
            model.addAttribute("userInfo", userInfo);
        }
        return "createplan"; // Renders create-plan.html
    }

}
