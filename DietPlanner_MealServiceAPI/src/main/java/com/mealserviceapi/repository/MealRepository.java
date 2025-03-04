package com.mealserviceapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mealserviceapi.model.Meal;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {
	@Query("select m from Meal m where m.calories < :remainingCals and m.status='Active'")
	List<Meal> getEligibleMeals(@Param("remainingCals")int remainingCalories);
}