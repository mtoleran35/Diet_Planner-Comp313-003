package com.dietplanner.service;

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
                .password(user.getPassword())  // Hashed password from database
                .roles(user.getAccounttype()) // Assign roles
                .build();
    }

    public User saveUser(User user) {
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
}
