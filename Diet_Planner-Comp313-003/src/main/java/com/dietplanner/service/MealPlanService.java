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
        Map<String, MealPlan> dailyTotals = new HashMap<>();
        Map<String, Integer> mealPlanIds = new HashMap<>();

        for (MealPlan meal : mealPlans) {
            String assignedDay = meal.getAssignedDay();
            
            // Store mealPlanId for the first occurrence of the assigned day
            mealPlanIds.putIfAbsent(assignedDay, meal.getMealPlanId());

            // Grouping by assigned day
            dailyTotals.putIfAbsent(assignedDay, new MealPlan());
            MealPlan mealPlan = dailyTotals.get(assignedDay);

            // ✅ Assign mealPlanId to the MealPlan object in dailyTotals
            mealPlan.setMealPlanId(mealPlanIds.get(assignedDay));

            // Aggregate nutritional values
            mealPlan.setTotalCalories(mealPlan.getTotalCalories() + meal.getCalories());
            mealPlan.setTotalCarbohydrate(mealPlan.getTotalCarbohydrate() + meal.getCarbohydrate());
            mealPlan.setTotalProtein(mealPlan.getTotalProtein() + meal.getProtein());
            mealPlan.setTotalFat(mealPlan.getTotalFat() + meal.getFat());
        }

        return Map.of(
            "mealPlans", mealPlans,
            "dailyTotals", dailyTotals, //Now includes mealPlanId
            "mealPlanIds", mealPlanIds
        );
    }    
       
    @Transactional
    public void deleteDailyTotal(Long userId, Integer planMealId) {
        // Delete meal plans related to the assigned day for the user.
        mealPlanRepository.deleteMealPlansByDay(userId, planMealId);
    }    

}