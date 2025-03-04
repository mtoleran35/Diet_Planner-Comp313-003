package com.mealserviceapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mealserviceapi.model.MealPlan;

@Repository
public interface MealPlanRepository extends JpaRepository<MealPlan, Long> {
	MealPlan findFirstByAccountIdOrderByMealPlanIdDesc(Long accountId);
}
