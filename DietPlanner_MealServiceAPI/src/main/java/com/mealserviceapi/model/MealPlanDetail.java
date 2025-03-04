package com.mealserviceapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="meal_plan_detail")
@Getter
@Setter
@NoArgsConstructor
public class MealPlanDetail {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mealplandetailid")
    private Long mealPlanDetailId;
	
	@Column(name = "mealid")
    private Long mealId;
	
	@Column(name = "mealquantity")
    private int mealQuantity;
	
	@Column(name = "assignedday")
    private String assignedDay;
	
	@ManyToOne
	@JoinColumn(name = "mealplanid")
	private MealPlan mealPlan;
	

	public MealPlanDetail(Long mealId, int mealQuantity, String assignedDay, MealPlan mealPlan) {
		this.mealId = mealId;
		this.mealQuantity = mealQuantity;
		this.assignedDay = assignedDay;
		this.mealPlan = mealPlan;
	}
	
	
}
