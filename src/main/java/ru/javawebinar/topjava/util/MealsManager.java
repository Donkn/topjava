package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealsManager {
    void insertMeal(Meal meal);

    Meal readMeal(Integer id);

    void updateMeal(Integer id, Meal meal);

    void deliteMeal(Integer id);

    List<Meal> getAll();

    void addAll(List<Meal> meals);
}
