package com.mealserviceapi.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="meal_plan")
@Getter
@Setter
@NoArgsConstructor
public class MealPlan {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mealplanid")
    private Long mealPlanId;
	
	//Should be foreign key. Test if this works first
	@Column(name = "accountid")
    private Long accountId;
	
    @Column(name = "mealplanname")
    private String mealPlanName;
    
    @Column(name = "totalcalories")
    private int totalCalories;
    
    @Column(name = "totalcarbohydrate")
    private int totalCarbohydrate;
    
    @Column(name = "totalfat")
    private int totalFat;
    
    @Column(name = "totalprotein")
    private int totalProtein;
    
    @OneToMany(mappedBy = "mealPlan")
	private List<MealPlanDetail> mealPlanDetail = new ArrayList<>();

	public MealPlan(Long accountId, String mealPlanName, int totalCalories, int totalCarbohydrate, int totalFat,
			int totalProtein) {
		this.accountId = accountId;
		this.mealPlanName = mealPlanName;
		this.totalCalories = totalCalories;
		this.totalCarbohydrate = totalCarbohydrate;
		this.totalFat = totalFat;
		this.totalProtein = totalProtein;
	}

}
