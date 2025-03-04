package com.dietplanner.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfo {
	private Long id;
	private String dietPreference;
	private Double weight;
	private Integer caloricIntakeGoal;
}
