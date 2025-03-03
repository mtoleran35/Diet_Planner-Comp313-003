package com.dietplanner.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @GetMapping("/myplans")
    public String showMyPlansPage(@AuthenticationPrincipal UserDetails user, Model model) {
        User appUser = userService.findByUsername(user.getUsername());

        // Fetch meal plans and daily totals
        Map<String, Object> mealPlansData = mealPlanService.getUserMealPlans(appUser.getId());
        List<MealPlan> mealPlans = (List<MealPlan>) mealPlansData.get("mealPlans");
        Map<String, MealPlan> dailyTotals = (Map<String, MealPlan>) mealPlansData.get("dailyTotals");

        // Use the fullname from the User entity
        String fullname = appUser.getFullname();
        
        // Debugging: Print mealPlans and dailyTotals in logs
        System.out.println("Meal Plans Retrieved: " + mealPlans);
        System.out.println("Daily Totals: " + dailyTotals);

        // Add attributes to the model
        model.addAttribute("username", appUser.getUsername());
        model.addAttribute("fullname", fullname); // Pass fullname to the template
        model.addAttribute("dietPreference", appUser.getDietPreference());
        model.addAttribute("caloricIntakeGoal", appUser.getCaloricIntakeGoal());
        model.addAttribute("weight", appUser.getWeight());
        model.addAttribute("mealPlans", mealPlans);
        model.addAttribute("dailyTotals", dailyTotals);

        return "myplans"; // Return the myplans.html view
    }    
    
    @GetMapping("/createplan")
    public String createPlanPage() {
        return "createplan"; // Renders create-plan.html
    }

}
