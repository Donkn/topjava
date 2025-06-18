package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import java.util.Collection;

import static ru.javawebinar.topjava.util.ValidationUtil.checkIsNew;

@Controller
public class MealRestController {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public Meal create(Integer usId, Meal meal) {
        log.info("create {}", meal);
        checkIsNew(meal);
        return service.create(usId, meal);
    }

    public Meal get(Integer usId, Integer mealId) {
        log.info("get {}", mealId);
        return service.get(usId, mealId);
    }

    public void delete(Integer usId, Integer mealId) {
        log.info("delete {}", mealId);
        service.delete(usId, mealId);
    }

    public Collection<Meal> getAll(Integer usId) {
        log.info("getAll");
        return service.getAll(usId);
    }

    public void update(Integer usId, Meal meal) {
        log.info("update {} with mtal={}", usId, meal);
        service.update(usId, meal);
    }
}