package com.dietplanner.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.dietplanner.dto.Meal;

@Service
public class MealPlanApiService {
	
	private final RestTemplate restTemplate;
    private final String mealPlanServiceUrl = "http://localhost:9091/api/mealplans"; // Ensure correct URL
    
    public MealPlanApiService(RestTemplate restTemplate) {
    	this.restTemplate = restTemplate;
    }
    
    // Generate a meal plan
    public List<Meal> generateMealPlan(){
    	ResponseEntity<Meal[]> response = restTemplate.getForEntity(mealPlanServiceUrl, Meal[].class);
    	
    	if (response.getBody() == null) { 
            return Collections.emptyList(); // Return an empty list instead of null
        }
    	
    	return Arrays.asList(response.getBody());
    }
}
