package com.dietplanner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;

import com.dietplanner.service.MealPlanService;
import com.dietplanner.service.UserService;
import com.dietplanner.model.MealPlan;
import com.dietplanner.model.User;

@Controller
public class DashboardController {

    private final UserService userService;
    
    @Autowired
    private MealPlanService mealPlanService;

    public DashboardController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal UserDetails user, Model model) {
        User appUser = userService.findByUsername(user.getUsername());
        model.addAttribute("username", appUser.getUsername());

     // Add user attributes to the model
        model.addAttribute("username", appUser.getUsername());
        model.addAttribute("firstName", appUser.getFirstName());
        model.addAttribute("lastName", appUser.getLastName());
        model.addAttribute("weight", appUser.getWeight());
        model.addAttribute("caloricIntakeGoal", appUser.getCaloricIntakeGoal());
        model.addAttribute("dietPreferences", appUser.getDietPreference()); // Ensure this is a List<String> if multiple

     // Call the method and get the result as a Map
        Map<String, Object> result = mealPlanService.getUserMealPlans(appUser.getId());
        // Extract mealPlans from the result map
        List<MealPlan> myplans = (List<MealPlan>) result.get("mealPlans");        
        model.addAttribute("myplans", myplans); // Add meal plans to the model
        
        if ("ADMIN".equalsIgnoreCase(appUser.getAccounttype())) {
            return "redirect:/admin-dashboard"; // Redirect to admin dashboard if ADMIN
        } else {
            return "dashboard"; // Load regular user dashboard
        }
    }

    @GetMapping("/admin-dashboard")
    public String adminDashboard(@AuthenticationPrincipal UserDetails user, Model model) {
        User appUser = userService.findByUsername(user.getUsername());
        model.addAttribute("username", appUser.getUsername());
        return "admin-dashboard";
    }
}
