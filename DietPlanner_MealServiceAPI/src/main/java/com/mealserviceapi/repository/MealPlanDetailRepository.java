package com.mealserviceapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mealserviceapi.model.MealPlanDetail;

@Repository
public interface MealPlanDetailRepository extends JpaRepository<MealPlanDetail, Long> {

}
