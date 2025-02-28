package com.dietplanner.controller;

import com.dietplanner.model.User;
import com.dietplanner.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
  
    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user) {
        // Set default user role
        user.setAccounttype("USER");

        // Save user (UserService will handle hashing)
        userService.saveUser(user);

        return "redirect:/login";
    }
    
}
