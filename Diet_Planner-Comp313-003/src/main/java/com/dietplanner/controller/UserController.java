package com.dietplanner.controller;

import com.dietplanner.model.MealPlan;
import com.dietplanner.service.MealPlanService;
import com.dietplanner.model.User;
import com.dietplanner.service.UserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; //---stephanie
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
  
    @Autowired
    private MealPlanService mealPlanService;    
  
    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }
    
    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {
        try {
            //user.setAccounttype("USER");
            //user.setAccounttype(user.);
            userService.saveUser(user);
            return "redirect:/login";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "register"; // Reload registration page with error message
        }
    }
  
    @PostMapping("/update-profile")
    public String updateProfile(@AuthenticationPrincipal UserDetails user, @ModelAttribute User updatedUser) {
        //String username = principal.getName();
        userService.updateUserProfile(user.getUsername(), updatedUser);
        return "redirect:/dashboard"; // Redirect back to dashboard after updating
        //return "redirect:/settings?success";
    } 
}
