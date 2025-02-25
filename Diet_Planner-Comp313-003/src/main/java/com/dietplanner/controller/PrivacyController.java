package com.dietplanner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller  // Add the @Controller annotation here
public class PrivacyController {

    @GetMapping("/privacy")
    public String showPrivacyPage() {
        return "privacy"; // Return Thymeleaf privacy.html
    }
}
