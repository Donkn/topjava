package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.web.MealServlet;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Map<Integer, Meal>> usMeals = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);
    @Autowired
    private UserRepository userRepository;
//  private UserRepository userRepository = MealServlet.userRepository;
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);

//    {
//        MealsUtil.meals.forEach(meal -> save(1, meal));
//        System.out.println("Репозиторий пользователей создан добавлен пользователь с именем= " + userRepository.get(1).getName());
//    }

    @Override
    public Meal save(Integer usId, Meal meal) {
        log.info("save{}", meal);
        ValidationUtil.checkNotFound(userRepository.get(usId), usId);
        Map<Integer, Meal> mealsMap = usMeals.computeIfAbsent(usId, k -> new HashMap<>());
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            mealsMap.put(meal.getId(), meal);
            usMeals.put(usId, mealsMap);
            return meal;
        }
        ValidationUtil.checkNotFound(usMeals.get(usId).get(meal.getId()), meal.getId());
        mealsMap.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        usMeals.put(usId, mealsMap);
        return meal;
    }

    @Override
    public boolean delete(Integer usId, int id) {
        log.info("delete{}", id);
        ValidationUtil.checkNotFound(userRepository.get(usId), usId);
        Map<Integer, Meal> mealsMap = usMeals.get(usId);
        return mealsMap.remove(id) != null;
    }

    @Override
    public Meal get(Integer usId, int id) {
        log.info("get{}", id);
        ValidationUtil.checkNotFound(userRepository.get(usId), usId);
        Map<Integer, Meal> mealsMap = usMeals.get(usId);
        return mealsMap.get(id);
    }

    @Override
    public Collection<Meal> getAll(Integer usId) {
        log.info("getAll{}", usId);
        ValidationUtil.checkNotFound(userRepository.get(usId), usId);
        Map<Integer, Meal> mealsMap = usMeals.get(usId);
        return mealsMap.values().stream().sorted(Comparator.comparing(Meal::getDateTime).reversed()).collect(Collectors.toList());
    }
}

