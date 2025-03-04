package com.dietplanner.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfo {
	private Long id;
	private String dietPreference;
	private Double weight;
	private Integer caloricIntakeGoal;
	private List<Meal> mealPlan;
	private List<String> selectedDays;
	private List<String> mealIds; //Needed to pass list of meals from view to controller
	
}
