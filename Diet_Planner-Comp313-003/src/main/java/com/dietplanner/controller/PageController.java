package com.dietplanner.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.dietplanner.model.User;
import com.dietplanner.service.UserService;
import com.dietplanner.model.MealPlan;
import com.dietplanner.service.MealPlanService;

@Controller
public class PageController {
	

	@Autowired
    private UserService userService;

    @Autowired
    private MealPlanService mealPlanService;	
    
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

//    @GetMapping("/myplans")
//    public String myPlansPage() {
//        return "myplans"; // Renders my-plans.html
//    }

    @GetMapping("/myplans")
    public String showMyPlansPage(@AuthenticationPrincipal UserDetails user, Model model) {
        User appUser = userService.findByUsername(user.getUsername());
        
        List<MealPlan> mealPlans = mealPlanService.getUserMealPlans(appUser.getId()); // Fetch meal plans for the authenticated user
        
        model.addAttribute("username", appUser.getUsername());
        model.addAttribute("dietPreference", appUser.getDietPreference());
        model.addAttribute("caloricIntakeGoal", appUser.getCaloricIntakeGoal());
        model.addAttribute("weight", appUser.getWeight());
        model.addAttribute("mealPlans", mealPlans); // Add meal plans to the model
        
        return "myplans"; // Return the myplans.html view
    }    
    
    @GetMapping("/createplan")
    public String createPlanPage() {
        return "createplan"; // Renders create-plan.html
    }

}
