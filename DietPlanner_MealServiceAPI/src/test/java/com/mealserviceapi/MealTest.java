package com.mealserviceapi;

import org.junit.jupiter.api.Test;

import com.mealserviceapi.model.Meal;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MealTest {

    private Meal meal;
    private List<Meal> mealList;

    @BeforeEach
    void setUp() {
        // Initialize the Meal object and a mock meal list before each test
        meal = new Meal(1L, "Pizza", 500, 60, 20, 30, "Active");

        mealList = new ArrayList<>();
        mealList.add(meal);
        mealList.add(new Meal(2L, "Salad", 300, 40, 10, 20, "Active"));
    }

    @Test
    void testMealCreation() {
        // Test creating a meal object with valid attributes
        assertNotNull(meal, "Meal object should not be null");
        assertEquals(1L, meal.getMealId(), "Meal ID should match");
        assertEquals("Pizza", meal.getMealName(), "Meal name should match");
        assertEquals(500, meal.getCalories(), "Calories should match");
        assertEquals(60, meal.getCarbohydrate(), "Carbohydrate should match");
        assertEquals(20, meal.getFat(), "Fat should match");
        assertEquals(30, meal.getProtein(), "Protein should match");
        assertEquals("Active", meal.getStatus(), "Status should match");
    }

    @Test
    void testMealUpdate() {
        // Test updating a meal object
        meal.setMealId(2L);
        meal.setMealName("Salad");
        meal.setCalories(300);
        meal.setCarbohydrate(40);
        meal.setFat(10);
        meal.setProtein(20);
        meal.setStatus("Inactive");

        assertEquals(2L, meal.getMealId(), "Updated meal ID should match");
        assertEquals("Salad", meal.getMealName(), "Updated meal name should match");
        assertEquals(300, meal.getCalories(), "Updated calories should match");
        assertEquals(40, meal.getCarbohydrate(), "Updated carbohydrate should match");
        assertEquals(10, meal.getFat(), "Updated fat should match");
        assertEquals(20, meal.getProtein(), "Updated protein should match");
        assertEquals("Inactive", meal.getStatus(), "Updated status should match");
    }

    @Test
    void testGetAllMeals() {
        // Test retrieving all meals
        List<Meal> allMeals = mealList;

        assertEquals(2, allMeals.size(), "There should be 2 meals in the list");
        assertEquals("Pizza", allMeals.get(0).getMealName(), "First meal name should match");
        assertEquals("Salad", allMeals.get(1).getMealName(), "Second meal name should match");
    }

    @Test
    void testGetMealById() {
        // Test retrieving a meal by its ID
        Optional<Meal> foundMeal = mealList.stream()
                .filter(m -> m.getMealId().equals(1L))
                .findFirst();

        assertTrue(foundMeal.isPresent(), "Meal with ID 1 should exist");
        assertEquals("Pizza", foundMeal.get().getMealName(), "Meal name should match");
    }

    @Test
    void testDeleteMeal() {
        // Test deleting a meal by ID
        Long mealIdToDelete = 1L;
        mealList.removeIf(m -> m.getMealId().equals(mealIdToDelete));

        assertEquals(1, mealList.size(), "There should be 1 meal left after deletion");
        assertEquals("Salad", mealList.get(0).getMealName(), "Remaining meal should be 'Salad'");
    }

  
}