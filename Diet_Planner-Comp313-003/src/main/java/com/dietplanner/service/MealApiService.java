package com.dietplanner.service;

import com.dietplanner.dto.Meal;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Collections;

@Service
public class MealApiService {

    private final RestTemplate restTemplate;
    private final String mealServiceUrl = "http://localhost:9091/api/meals"; // Ensure correct URL

    public MealApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // ✅ Fetch all meals (Ensure empty list instead of null)
    public List<Meal> fetchAllMeals() {
        ResponseEntity<Meal[]> response = restTemplate.getForEntity(mealServiceUrl, Meal[].class);
        
        if (response.getBody() == null) { 
            return Collections.emptyList(); // Return an empty list instead of null
        }
        
        return Arrays.asList(response.getBody());
    }
   
    // ✅ Fetch a single meal by ID with error handling
    public Meal getMealById(Long mealId) {
        String url = mealServiceUrl + "/" + mealId;
        try {
            return restTemplate.getForObject(url, Meal.class);
        } catch (Exception e) {
            return null; // Handle missing meal case
        }
    }

    // ✅ Add a new meal
    public void addMeal(Meal meal) {
        restTemplate.postForEntity(mealServiceUrl, meal, Meal.class);
    }

    // ✅ Update an existing meal
    public void updateMeal(Long mealId, Meal meal) {
        String url = mealServiceUrl + "/" + mealId;
        restTemplate.put(url, meal);
    }

    // ✅ Delete a meal by ID
    public void deleteMeal(Long mealId) {
        String url = mealServiceUrl + "/" + mealId;
        restTemplate.delete(url);
    }
}
