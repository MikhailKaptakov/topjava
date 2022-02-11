package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealController extends HttpServlet {
    private static final Logger log = getLogger(MealController.class);
    private static final String INSERT_OR_EDIT = "/mealsEdit.jsp";
    private static final String MEALS_LIST = "/meals.jsp";
    private final MealDao dao;

    public MealController() {
        super();
        dao = new MealDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        log.debug("redirect to mealController GET");
        request.setCharacterEncoding("UTF-8");
        String forward;
        String action = request.getParameter("action");

        if (action.equalsIgnoreCase("mealsList")){
            forward = MEALS_LIST;
            request.setAttribute("meals", MealsUtil.allMealsTo(dao.getAll()));
        } else if (action.equalsIgnoreCase("edit")){
            forward = INSERT_OR_EDIT;
            Integer mealId = Integer.parseInt(request.getParameter("mealId"));
            Meal meal = dao.get(mealId);
            request.setAttribute("meal", meal);
        } else if (action.equalsIgnoreCase("delete")){
        Integer mealId = Integer.parseInt(request.getParameter("mealId"));
        dao.delete(mealId);
        forward = MEALS_LIST;
        request.setAttribute("meals", MealsUtil.allMealsTo(dao.getAll()));
        } else {
            forward = INSERT_OR_EDIT;
        }
        request.getRequestDispatcher(forward).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        log.debug("redirect to mealController POST");
        request.setCharacterEncoding("UTF-8");
        LocalDateTime date = LocalDateTime.parse(request.getParameter("dateTime"),
                DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        Meal meal = new Meal(date,description,calories);
        String mealId = request.getParameter("mealId");
        if(mealId == null || mealId.isEmpty()) {
            dao.add(meal);
        }
        else
        {
            meal.setId(Integer.parseInt(mealId));
            dao.update(meal);
        }
        request.setAttribute("meals", MealsUtil.allMealsTo(dao.getAll()));
        request.getRequestDispatcher(MEALS_LIST).forward(request, response);
    }

}
