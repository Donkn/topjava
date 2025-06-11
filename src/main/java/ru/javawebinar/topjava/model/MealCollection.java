package ru.javawebinar.topjava.model;
import ru.javawebinar.topjava.util.MealsManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MealCollection implements MealsManager {
    private final Map<Integer, Meal> mealMap;
    private static MealCollection instance;

    public MealCollection() {
        this.mealMap = new HashMap<>();
    }
    public static MealCollection getMealCollecion() {
        if (instance == null) {
            instance = new MealCollection();
        }
        return instance;
    }
    @Override
    public void insertMeal(Meal meal) {
        mealMap.put(meal.getId(), meal);
    }
    @Override
    public Meal readMeal(Integer id) {
        return mealMap.get(id);
    }
    @Override
    public void updateMeal(Integer id, Meal meal) {
        mealMap.replace(id, meal);
    }
    @Override
    public void deliteMeal(Integer id) {
        mealMap.remove(id);
    }
    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(mealMap.values());
    }
    @Override
    public void addAll(List<Meal> meals) {
        meals.forEach(meal -> mealMap.put(meal.getId(), meal));
    }
}
