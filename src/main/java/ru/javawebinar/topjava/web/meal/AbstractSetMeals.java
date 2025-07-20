package ru.javawebinar.topjava.web.meal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.web.SecurityUtil;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkIsNew;

@Controller
public abstract class AbstractSetMeals {

    static final Logger log = LoggerFactory.getLogger(AbstractSetMeals.class);

    @Autowired
    MealService mealService;

    @PostMapping("/meals")
    public String createMeal(HttpServletRequest request) {
        Meal meal = new Meal(LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

        if (StringUtils.hasLength(request.getParameter("id"))) {
            int userId = SecurityUtil.authUserId();
            assureIdConsistent(meal, getId(request));
            log.info("update {} for user {}", meal, userId);
            mealService.update(meal, userId);
            return "redirect:/meals";
        } else {
            int userId = SecurityUtil.authUserId();
            checkIsNew(meal);
            log.info("create {} for user {}", meal, userId);
            mealService.create(meal, userId);
            return "redirect:/meals";
        }
    }
    int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }

    @GetMapping("/meals")
    public String getMeals(RedirectAttributes attributes,
                           @RequestParam Map<String, String> param,
                           @RequestParam(required = false) String action) {
        log.info("/meals");
        param.forEach(attributes::addAttribute);
        if (action != null) {
            return "redirect:/" + action;
        } else return "redirect:/getAll";
    }
}


