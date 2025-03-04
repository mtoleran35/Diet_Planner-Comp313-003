package com.mealserviceapi.model;

public class UserInfo {
	
	private Long id;
	private String dietPreference;
	private Double weight;
	private Integer caloricIntakeGoal;
	
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
	
	public UserInfo() {}
	
	public UserInfo(Long id, String dietPreference, Double weight, Integer caloricIntakeGoal) {
		this.id = id;
		this.dietPreference = dietPreference;
		this.weight = weight;
		this.caloricIntakeGoal = caloricIntakeGoal;
	}
	
	
	
}
