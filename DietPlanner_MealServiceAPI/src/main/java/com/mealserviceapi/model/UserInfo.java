package com.mealserviceapi.model;

import java.util.List;

public class UserInfo {
	
	private Long id;
	private String dietPreference;
	private Double weight;
	private Integer caloricIntakeGoal;
	private List<Meal> mealPlan;
	private List<String> selectedDays;
	private List<String> mealIds; //Needed to pass list of meals from view to controller
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDietPreference() {
		return dietPreference;
	}
	public void setDietPreference(String dietPreference) {
		this.dietPreference = dietPreference;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	public Integer getCaloricIntakeGoal() {
		return caloricIntakeGoal;
	}
	public void setCaloricIntakeGoal(Integer caloricIntakeGoal) {
		this.caloricIntakeGoal = caloricIntakeGoal;
	}
	public List<Meal> getMealPlan() {
		return mealPlan;
	}
	public void setMealPlan(List<Meal> mealPlan) {
		this.mealPlan = mealPlan;
	}
	public List<String> getSelectedDays() {
		return selectedDays;
	}
	public void setSelectedDays(List<String> selectedDays) {
		this.selectedDays = selectedDays;
	}

	public UserInfo() {}
	
	public UserInfo(Long id, String dietPreference, Double weight, Integer caloricIntakeGoal, 
			List<Meal> mealPlan, List<String> selectedDays) {
		this.id = id;
		this.dietPreference = dietPreference;
		this.weight = weight;
		this.caloricIntakeGoal = caloricIntakeGoal;
		this.mealPlan = mealPlan;
		this.selectedDays = selectedDays;
	}
	
	
	
}
