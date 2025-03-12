DELIMITER $$

CREATE DEFINER=`root`@`localhost` PROCEDURE `GetUserMealPlans`(IN p_accountid INT)
BEGIN
    SELECT 
        a.username, 
        CONCAT(a.firstname, ', ', a.lastname) AS fullname, -- Fixed concatenation
        a.dietpreference,
        a.caloricintakegoal,
        a.weight,
        mp.mealplanname,
        m.mealname,
        m.calories,
        m.carbohydrate,
        m.protein,
        m.fat,
        mpd.mealquantity,
        mpd.assignedday,
		mpd.mealplanid
    FROM meal_plan mp
    JOIN account a ON mp.accountid = a.accountid
    JOIN meal_plan_detail mpd ON mp.mealplanid = mpd.mealplanid
    JOIN meal m ON mpd.mealid = m.mealid
    WHERE a.accountid = p_accountid -- Fixed condition
    ORDER BY mp.mealplanid, mpd.assignedday;

END$$

DELIMITER ;