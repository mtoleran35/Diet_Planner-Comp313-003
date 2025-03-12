package com.dietplanner.service;
import java.util.regex.Pattern;

import com.dietplanner.model.User;
import com.dietplanner.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Argon2 Password Encoder

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword()) // Hashed password from database
                .roles(user.getAccounttype()) // Assign roles
                .build();
    }

    public User saveUser(User user) {
        // Check if the username already exists
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalArgumentException("User with this username already exists");
        }
        
        // Validate the password
        if (!isValidPassword(user.getPassword())) {
        	throw new IllegalArgumentException("Password must be at least 8 characters long, contain at least one number, and one uppercase letter.");
        }

        // Validation: Ensure no negative values for Weight and Caloric Intake Goal
        if (user.getWeight() != null && user.getWeight() < 0) {
            throw new IllegalArgumentException("Weight cannot be negative");
        }

        if (user.getCaloricIntakeGoal() != null && user.getCaloricIntakeGoal() < 0) {
            throw new IllegalArgumentException("Daily Caloric Intake Goal cannot be negative");
        }

        // Encrypt the password if not already hashed
        if (!user.getPassword().startsWith("$argon2id$")) { // Ensure password is hashed
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        return userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    // Method to get the current logged-in user
    public User getUserDetails() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return findByUsername(username); // Fetch the user by username from the database
    }

    // Method to update user profile
    public void updateUserProfile(String username, User updatedUser) {
        User existingUser = findByUsername(username); // Fetch existing user by username

        // Validation: Ensure no negative values for Weight and Caloric Intake Goal
        if (updatedUser.getWeight() != null && updatedUser.getWeight() < 0) {
            throw new IllegalArgumentException("Weight cannot be negative");
        }

        if (updatedUser.getCaloricIntakeGoal() != null && updatedUser.getCaloricIntakeGoal() < 0) {
            throw new IllegalArgumentException("Daily Caloric Intake Goal cannot be negative");
        }

        // Update user details
        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setLastName(updatedUser.getLastName());
        existingUser.setWeight(updatedUser.getWeight());
        existingUser.setCaloricIntakeGoal(updatedUser.getCaloricIntakeGoal());
        existingUser.setDietPreference(updatedUser.getDietPreference());

        // If the password is updated, hash it before saving
        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }

        userRepository.save(existingUser); // Save updated user to the database
    }

    // Change password
    public void updatePassword(String username, String currentPassword, String newPassword) {
        User user = findByUsername(username); // Fetch the user by username

        // Verify current password
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new IllegalArgumentException("Current password is incorrect");
        }

        // Encode and update the new password
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user); // Save the updated user
    }
    
    public boolean isValidPassword(String password) {
        // Regular expression for the validation
        String passwordPattern = "^(?=.*[A-Z])(?=.*\\d).{8,}$";

        return Pattern.matches(passwordPattern, password);
    }
}
