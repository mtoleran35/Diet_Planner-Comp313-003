package com.dietplanner.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

import com.dietplanner.model.MealPlan;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.StoredProcedureQuery;
import jakarta.transaction.Transactional;

@Repository
public class MealPlanRepository {
	
    @PersistenceContext
    private EntityManager entityManager;

    public List<MealPlan> getUserMealPlans(Long accountId) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("GetUserMealPlans");
        query.registerStoredProcedureParameter("p_accountid", Long.class, ParameterMode.IN);
        query.setParameter("p_accountid", accountId);
        
        List<Object[]> results = query.getResultList();
        List<MealPlan> mealPlans = new ArrayList<>();

        for (Object[] row : results) {
            MealPlan mealPlan = new MealPlan();
            mealPlan.setUsername((String) row[0]);
            mealPlan.setFullname((String) row[1]);
            mealPlan.setDietPreference((String) row[2]);
            mealPlan.setCaloricIntakeGoal((Integer) row[3]);
            mealPlan.setWeight((Double) row[4]);
            mealPlan.setMealPlanName((String) row[5]);
            mealPlan.setMealName((String) row[6]);
            mealPlan.setCalories((Integer) row[7]);
            mealPlan.setCarbohydrate((Integer) row[8]);
            mealPlan.setProtein((Integer) row[9]);
            mealPlan.setFat((Integer) row[10]);
            mealPlan.setMealQuantity((Integer) row[11]);
            mealPlan.setAssignedDay((String) row[12]); // Adjust the index based on your SP result
            mealPlans.add(mealPlan);
        }

        return mealPlans;
    }
    
    @Transactional
    public void deleteMealPlansByDay(Long userId, String assignedDay) {
        String sql = "DELETE FROM meal_plan_detail mpd WHERE mpd.assignedday = :assignedDay AND mpd.mealplanid IN (SELECT mp.mealplanid FROM meal_plan mp WHERE mp.accountid = :userId)";
        
        try {
            Query query = entityManager.createNativeQuery(sql);
            query.setParameter("assignedDay", assignedDay);
            query.setParameter("userId", userId);
            int rowsDeleted = query.executeUpdate();

            System.out.println(rowsDeleted + " records deleted.");
        } catch (Exception e) {
            // Handle exception (e.g., log it)
            System.err.println("Error while deleting records: " + e.getMessage());
        }
    }    
}
