CREATE DATABASE dietplannerdb;

use dietplannerdb;
CREATE TABLE account
(accountid int PRIMARY KEY AUTO_INCREMENT,
accounttype varchar(30),
firstname varchar(30),
lastname varchar(50),
weight decimal(9,2),
caloricintakegoal int,
dietpreference varchar(30),
username varchar(30),
password varchar(30));

use dietplannerdb;
CREATE TABLE meal
(mealid int PRIMARY KEY AUTO_INCREMENT,
mealname varchar(50),
calories int,
carbohydrate int,
fat int,
protein int,
status varchar(50));

use dietplannerdb;
CREATE TABLE meal_plan
(mealplanid int PRIMARY KEY AUTO_INCREMENT,
accountid int, FOREIGN KEY(accountid) REFERENCES account(accountid),
mealplanname varchar(50),
totalcalories int,
totalcarbohydrate int,
totalfat int,
totalprotein int);

use dietplannerdb;
CREATE TABLE meal_plan_detail
(mealplandetailid int PRIMARY KEY AUTO_INCREMENT,
mealplanid int, FOREIGN KEY(mealplanid) REFERENCES meal_plan(mealplanid),
mealid int, FOREIGN KEY(mealid) REFERENCES meal(mealid),
mealquantity int,
assignedday varchar(30));
