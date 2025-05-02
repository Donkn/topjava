package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        List<UserMealWithExcess>  usersMealWithExcesses = new ArrayList<>();

        Map<LocalDate, Integer>  mealMap = new HashMap<>();
        int factCalories;
        boolean excess = false;

        for (UserMeal meal : meals) {
            LocalDateTime localDateTime = meal.getDateTime();
            LocalDate localDate = localDateTime.toLocalDate();


            int calories = meal.getCalories();

            if (mealMap.containsKey(localDate)) {
                Integer totalCalories = mealMap.get(localDate) + calories;
                mealMap.put(localDate, totalCalories);
            } else {
                mealMap.put(localDate, calories);
            }
        }

        for (UserMeal meal : meals) {
            String description = meal.getDescription();
            LocalTime localTime = meal.getDateTime().toLocalTime();
            int calories = meal.getCalories();
            factCalories = mealMap.get(meal.getDateTime().toLocalDate() );

            if(factCalories > caloriesPerDay) {
                excess = true;
            }

            boolean flagFilter = TimeUtil.isBetweenHalfOpen(localTime, startTime, endTime);


           if (flagFilter) {

                UserMealWithExcess userMealWithExcess = new UserMealWithExcess(meal.getDateTime(), description, calories, excess);
                usersMealWithExcesses.add(userMealWithExcess);
            }

        }
        return usersMealWithExcesses;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> mealMapEx = meals.stream().collect(
                Collectors.groupingBy(meal -> meal.getDateTime().toLocalDate(),
                Collectors.summingInt(UserMeal::getCalories) ));

        // TODO Implement by streams

        List<UserMealWithExcess> userMealWithExcesses = meals.stream().filter( meal ->
                 TimeUtil.isBetweenHalfOpen( meal.getDateTime().toLocalTime(), startTime, endTime))
                .map( meal -> {
                    Integer caloriesDay = mealMapEx.get(meal.getDateTime().toLocalDate() );
                    boolean excess = caloriesDay > caloriesPerDay;
                    return new UserMealWithExcess(meal.getDateTime(), meal.getDescription(),meal.getCalories(), excess);
                }).collect(Collectors.toList());
        return  userMealWithExcesses;

    }
}
