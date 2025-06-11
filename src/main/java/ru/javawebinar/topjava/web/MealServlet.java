package ru.javawebinar.topjava.web;


import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.model.MealCollection;
import ru.javawebinar.topjava.util.MealsManager;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    List<Meal> meals = Arrays.asList(
        new Meal(LocalDateTime.of(2020, Month.JANUARY,30, 10, 0), "Завтрак", 500, 1),
        new Meal(LocalDateTime.of(2020, Month.JANUARY,30, 13, 0), "Обед", 1000, 2),
        new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500, 3),
        new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100,4),
        new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000, 5),
        new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500, 6),
        new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410, 7)
    );
    MealsManager mealsManager = MealCollection.getMealCollecion();
    private static final Logger logg = getLogger(MealServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logg.debug("MealServlet");
        mealsManager.addAll(meals);
        List<MealTo> mealTo = MealsUtil.filteredByStreams(mealsManager.getAll(), LocalTime.of(0, 0), LocalTime.of(23, 59), 2000);
        request.setAttribute("mealTo", mealTo);
        request.getRequestDispatcher("/Meals.jsp").forward(request, response);
    }
}
