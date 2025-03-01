package com.dietplanner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
    public String createPlanPage() {
        return "createplan"; // Renders create-plan.html
    }

}
