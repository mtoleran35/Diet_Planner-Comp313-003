package com.dietplanner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import com.dietplanner.service.UserService;
import com.dietplanner.model.User;

@Controller
public class DashboardController {

    private final UserService userService;

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
        model.addAttribute("dietPreferences", appUser.getDietPreference());
        
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
