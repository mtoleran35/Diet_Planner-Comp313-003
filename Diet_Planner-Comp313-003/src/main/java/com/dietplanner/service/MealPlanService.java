package com.dietplanner.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dietplanner.model.MealPlan;
import com.dietplanner.repository.MealPlanRepository;

import jakarta.transaction.Transactional;

@Service
public class MealPlanService {
    @Autowired
    private MealPlanRepository mealPlanRepository;

    @Transactional
    public List<MealPlan> getUserMealPlans(Long accountId) {
        return mealPlanRepository.getUserMealPlans(accountId); // Assume this method runs the stored procedure
    }
}
