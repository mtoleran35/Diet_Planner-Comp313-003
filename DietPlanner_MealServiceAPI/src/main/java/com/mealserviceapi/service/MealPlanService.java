package com.mealserviceapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mealserviceapi.model.Meal;
import com.mealserviceapi.model.UserInfo;
import com.mealserviceapi.repository.MealRepository;

@Service
public class MealPlanService {

	@Autowired
	private MealRepository mealRepository;
	
	//List of meals to store the generated meal plans
	List<Meal> mealList;
    //Track current total calories and macros of valid meals
	private int currentCalories;
	private int currentCarb;
	private int currentFat;
	private int currentProtein;
	
	//Add logic here to generate meal plans
	public List<Meal> generateMealPlan(UserInfo userInfo) {
		
		//Initialize user info
		String dietPreference = userInfo.getDietPreference();
		double weight = userInfo.getWeight();
		int caloricIntakeGoal = userInfo.getCaloricIntakeGoal();
		
		//Initialize meal list
		mealList = new ArrayList<Meal>();
		
		//Initialize variables of total calories and macros of valid meals
		currentCalories = 0;
		currentCarb = 0;
		currentFat = 0;
		currentProtein = 0;
		
		//Remaining calories
		int remainingCalories = caloricIntakeGoal;
		
		while(true) {
			
			//Recompute remaining calories
			remainingCalories = caloricIntakeGoal - currentCalories;
			
			//Get all meals from the database that is less than or equal to remaining calories and with an 'active' status
			List<Meal> meals = mealRepository.getEligibleMeals(remainingCalories);
			
			//Check if there is at least one eligible meal
			if(meals.size() > 0) {
				//Generate random number, use as index to pick one random meal
				Random rnd = new Random();
				int index = rnd.nextInt(0, meals.size());
				
				//Get the 'random' meal from the list using the random integer
				Meal meal = meals.get(index);
				
				//Add to meal details to current tracker
				currentCalories += meal.getCalories();
				currentCarb += meal.getCarbohydrate();
				currentFat += meal.getFat();
				currentProtein += meal.getProtein();
				
				//Add the meal to current list
				mealList.add(meal);
			}
			else {
				//Check diet preference
				//Check if condition is met, depending on diet preference
				//If condition is met, end loop
				if (dietPreference.equals("HIGH PROTEIN"))
				{
				    //Check if protein is met
				    if (currentProtein >= weight)
				    {
				        break;
				    }
				    else if (dietPreference.equals("LOW CARB"))
				    {
				        //Check if carb is met (total calories from carbs should be within 15% of caloric intake goal)
				        int carbCalories = currentCarb * 4;
				        if (carbCalories <= (caloricIntakeGoal * .15))
				        {
				            break;
				        }
				    }
				    else if (dietPreference.equals("KETO"))
				    {
				        //Check if fat is met (total calories from fat should be within 70% of caloric intake goal)
				        int fatCalories = currentFat * 9;
				        if (fatCalories <= (caloricIntakeGoal * .7))
				        {
				            break;
				        }
				    }
				    //If diet preference is 'Any'
				    else
				    {
				        break;
				    }
				    //If condition is not met, loop again
				    mealList.clear();
				    remainingCalories = caloricIntakeGoal;
				    currentCalories = 0;
				    currentCarb = 0;
				    currentFat = 0;
				    currentProtein = 0;
				}
			}
			
		}
		
		return mealList;
	}
}
