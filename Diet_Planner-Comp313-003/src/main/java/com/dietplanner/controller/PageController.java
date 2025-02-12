package com.dietplanner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/settings")
    public String settingsPage() {
        return "settings"; // Renders settings.html
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
