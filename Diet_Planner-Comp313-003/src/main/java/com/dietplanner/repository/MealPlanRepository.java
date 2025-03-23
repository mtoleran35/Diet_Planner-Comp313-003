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
            mealPlan.setAssignedDay((String) row[12]);
            mealPlan.setMealPlanId(((Number) row[13]).intValue()); // Adjust the index based on your SP result
            mealPlans.add(mealPlan);
        }

        return mealPlans;
    }	

    @Transactional
    public void deleteMealPlansByDay(Long userId, Integer planMealId) {
        // 1. Delete from meal_plan_detail where assignedDay matches and meal plan belongs to userId        
    	String sql1 = "DELETE FROM meal_plan_detail mpd WHERE mpd.mealplanid = :planMealId AND mpd.mealplanid IN (SELECT mp.mealplanid FROM meal_plan mp WHERE mp.accountid = :userId)";
        
        // 2. Delete from meal_plan where mealplan's userId matches
        String sql2 = "DELETE FROM meal_plan mp WHERE mp.mealplanid = :planMealId AND mp.accountid = :userId";
        
        // 3. Update meal status to Inactive where meals matches userId
        String sql3 = "UPDATE meal m SET m.status = 'Inactive' WHERE m.mealid IN (SELECT mpd.mealid FROM meal_plan_detail mpd WHERE mpd.mealplanid = :planMealId AND mpd.mealplanid IN (SELECT mp.mealplanid FROM meal_plan mp WHERE mp.accountid = :userId))";

        try {
            // Execute third update query
            Query query3 = entityManager.createNativeQuery(sql3);
            query3.setParameter("planMealId", planMealId);
            query3.setParameter("userId", userId);
            int rowsDeleted3 = query3.executeUpdate();
            System.out.println(rowsDeleted3 + " records updated from meal.");        	
        	
            // Execute first delete query
            Query query1 = entityManager.createNativeQuery(sql1);
            query1.setParameter("planMealId", planMealId);
            query1.setParameter("userId", userId);
            int rowsDeleted1 = query1.executeUpdate();
            System.out.println(rowsDeleted1 + " records deleted from meal_plan_detail.");

            // Execute second delete query
            Query query2 = entityManager.createNativeQuery(sql2);
            query2.setParameter("planMealId", planMealId);
            query2.setParameter("userId", userId);
            int rowsDeleted2 = query2.executeUpdate();
            System.out.println(rowsDeleted2 + " records deleted from meal_plan.");
            
        } catch (Exception e) {
            // Handle exception (e.g., log it)
            System.err.println("Error while deleting records: " + e.getMessage());
        }
    }    
}