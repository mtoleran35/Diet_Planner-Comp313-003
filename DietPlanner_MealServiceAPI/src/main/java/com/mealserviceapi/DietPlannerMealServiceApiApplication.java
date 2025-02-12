package com.mealserviceapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DietPlannerMealServiceApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DietPlannerMealServiceApiApplication.class, args);
		System.out.println("Spring Boot Diet Planner Meal Service API started");
	}

}
