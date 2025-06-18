package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import java.util.Collection;

public interface MealRepository {
    Meal save(Integer usId, Meal meal);

    boolean delete(Integer usId, int id);

    Meal get(Integer usId, int id);

    Collection<Meal> getAll(Integer usId);
}
