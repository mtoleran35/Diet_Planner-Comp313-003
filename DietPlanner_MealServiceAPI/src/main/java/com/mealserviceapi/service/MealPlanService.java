package com.mealserviceapi.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mealserviceapi.model.Meal;
import com.mealserviceapi.model.MealPlan;
import com.mealserviceapi.model.MealPlanDetail;
import com.mealserviceapi.model.UserInfo;
import com.mealserviceapi.repository.MealPlanDetailRepository;
import com.mealserviceapi.repository.MealPlanRepository;
import com.mealserviceapi.repository.MealRepository;

@Service
public class MealPlanService {

	@Autowired
	private MealRepository mealRepository;
	
	@Autowired
	private MealPlanRepository mealPlanRepository;
	
	@Autowired
	private MealPlanDetailRepository mealPlanDetailRepository;
	
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
	
	//Save meal plan function
	public void saveMealPlan(UserInfo userInfo) {
		//From form: list of meals, selected days
		//From user: id
		
		//Get user id
		Long accountId = userInfo.getId();
		
		//Get local date to create meal plan name
		LocalDate today = LocalDate.now();
		String dateToday = today.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
		String mealPlanName = userInfo.getDietPreference() + "_" + dateToday;
		
		//Get list of meals from user info
		List<Meal> meals = userInfo.getMealPlan();
		
		//Get total values of macros
		int totalCalories = 0;
		int totalCarb = 0;
		int totalFat = 0;
		int totalProtein = 0;
		
		for (Meal meal : meals) {
			totalCalories += meal.getCalories();
        	totalCarb += meal.getCarbohydrate();
        	totalFat += meal.getFat();
        	totalProtein += meal.getProtein();
        }
		
		//Save new meal plan into meal_plan table
		MealPlan newMealPlan = new MealPlan(accountId,mealPlanName,totalCalories,totalCarb,totalFat,totalProtein);
		mealPlanRepository.save(newMealPlan);
		
		//Get latest meal_plan id
		MealPlan latestMealPlan = mealPlanRepository.findFirstByAccountIdOrderByMealPlanIdDesc(accountId);
		
		//Insert into meal_plan_detail table, with latest meal_plan id
		//Involves nested for loop in list of meals and list of selected days, to insert each meal
		
		//List of selected days for the meal plan
		List<String> selectedDays = userInfo.getSelectedDays();
		for (Meal meal : meals) {
			for (String day : selectedDays) {
				MealPlanDetail mealPlanDetail = new MealPlanDetail(meal.getMealId(),1,day,latestMealPlan);
				mealPlanDetailRepository.save(mealPlanDetail);
			}
		}
	} 
}
