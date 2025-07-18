package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import javax.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;
import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkIsNew;

@Controller
public class JspMealController extends AbstractSetMeals {

    @GetMapping("/create")
    public String createMeals(Model model) {
        Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @GetMapping("/update")
    public String updateMeals(@RequestParam(required = false) String id, Model model) {
        int userId = SecurityUtil.authUserId();
        log.info("get meal {} for user {}", id, userId);
        Meal meal = mealService.get(Integer.parseInt(id), userId);
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @GetMapping("/delete")
    public String deleteMeals(HttpServletRequest request) {
        int userId = SecurityUtil.authUserId();
        int id = getId(request);
        log.info("delete meal {} for user {}", id, userId);
        mealService.delete(id, userId);
        return "redirect:/meals";
    }

    @GetMapping("/filter")
    public String filterMeals(@RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate, @RequestParam(required = false) String startTime, @RequestParam(required = false) String endTime, Model model) {
        int userId = SecurityUtil.authUserId();
        log.info("getBetween dates({} - {}) time({} - {}) for user {}", startDate, endDate, startTime, endTime, userId);
        List<Meal> meals = mealService.getBetweenInclusive(parseLocalDate(startDate), parseLocalDate(endDate), userId);
        model.addAttribute("meals", MealsUtil.getFilteredTos(meals, SecurityUtil.authUserCaloriesPerDay(), parseLocalTime(startTime), parseLocalTime(endTime)));
        return "meals";
    }

    @GetMapping("/getAll")
    public String getAll(Model model) {
        int userId = SecurityUtil.authUserId();
        log.info("getAll for user{}", userId);
        model.addAttribute("meals", MealsUtil.getTos(mealService.getAll(userId), SecurityUtil.authUserCaloriesPerDay()));
        return "meals";
    }
}


