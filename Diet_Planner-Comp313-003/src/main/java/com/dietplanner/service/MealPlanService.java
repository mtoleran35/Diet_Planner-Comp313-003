package com.dietplanner.service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public Map<String, Object> getUserMealPlans(Long accountId) {
        List<MealPlan> mealPlans = mealPlanRepository.getUserMealPlans(accountId);
        Map<String, MealPlan> dailyTotals = groupByAssignedDay(mealPlans);
        return Map.of("mealPlans", mealPlans, "dailyTotals", dailyTotals);
    }
    
    private Map<String, MealPlan> groupByAssignedDay(List<MealPlan> mealPlans) {
        Map<String, MealPlan> dailyTotals = new HashMap<>();
        for (MealPlan meal : mealPlans) {
            String assignedDay = meal.getAssignedDay();
            dailyTotals.putIfAbsent(assignedDay, new MealPlan());
            MealPlan mealPlan = dailyTotals.get(assignedDay);
            mealPlan.setTotalCalories(mealPlan.getTotalCalories() + meal.getCalories());
            mealPlan.setTotalCarbohydrate(mealPlan.getTotalCarbohydrate() + meal.getCarbohydrate());
            mealPlan.setTotalProtein(mealPlan.getTotalProtein() + meal.getProtein());
            mealPlan.setTotalFat(mealPlan.getTotalFat() + meal.getFat());
        }

        return dailyTotals;
    }

    @Transactional
    public void deleteDailyTotal(Long userId, String assignedDay) {
        // Delete meal plans related to the assigned day for the user.
        mealPlanRepository.deleteMealPlansByDay(userId, assignedDay);
    }    

}