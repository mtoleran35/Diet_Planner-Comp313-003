package com.dietplanner.controller;
import com.dietplanner.model.User;
import com.dietplanner.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(User user, Model model) {
        try {
            userService.saveUser(user);
            model.addAttribute("success", true);
            //return "redirect:/login";
            return "register"; 
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "register";
        }
    }

    @PostMapping("/update-profile")
    public String updateProfile(@AuthenticationPrincipal UserDetails user, User updatedUser) {
        userService.updateUserProfile(user.getUsername(), updatedUser);
        return "redirect:/dashboard";
    }

    @GetMapping("/change-password")
    public String showChangePasswordPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        try {
            if (userDetails == null) {
                throw new Exception("AuthenticationPrincipal is null");
            }
            User currentUser = userService.findByUsername(userDetails.getUsername());
            model.addAttribute("user", currentUser); // Add user to the model
        } catch (Exception e) {
            // Log the error for debugging purposes
            System.err.println("Error loading user: " + e.getMessage());
            model.addAttribute("user", null); // Handle the case where the user is null
        }
        model.addAttribute("successMessage", ""); // Prepare success message
        model.addAttribute("errorMessage", ""); // Prepare error message
        return "change-password";
    }

    @PostMapping("/change-password")
    public String changePassword(@AuthenticationPrincipal UserDetails userDetails,
                                 @RequestParam String currentPassword,
                                 @RequestParam String newPassword,
                                 @RequestParam String confirmPassword,
                                 Model model) {
    	// Validate the password
        if (!userService.isValidPassword(newPassword)) {
        	model.addAttribute("errorMessage", "Password must be at least 8 characters long, contain at least one number, and one uppercase letter.");
            return "change-password";
        }
        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("errorMessage", "New Password and Confirm Password do not match");
            return "change-password";
        }
        try {
            userService.updatePassword(userDetails.getUsername(), currentPassword, newPassword);
            model.addAttribute("successMessage", "Password updated successfully!");
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
        }
        return "change-password";
    }
}
