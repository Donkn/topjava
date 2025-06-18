package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFound;

import java.util.Collection;

@Service
public class MealService {

    private final MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(Integer usId, Meal meal) {
        return repository.save(usId, meal);
    }

    public Meal get(Integer usId, Integer mealId) {
        return repository.get(usId, mealId);
    }

    public void delete(Integer usId, Integer mealId) {
        repository.delete(usId, mealId);
    }

    public Collection<Meal> getAll(Integer usId) {
        return repository.getAll(usId);
    }

    public void update(Integer usId, Meal meal) {
        checkNotFound(repository.save(usId, meal), meal.getId());
    }
}