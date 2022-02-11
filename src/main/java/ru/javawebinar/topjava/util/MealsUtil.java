package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MealsUtil {
    public static final int CALORIES_DAY_LIMIT = 2000;

    public static void main(String[] args) {
        List<Meal> meals = new MealDao().getAll();
        List<MealTo> mealsTo = filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0),
                CALORIES_DAY_LIMIT);
        mealsTo.forEach(System.out::println);
    }

    public static List<MealTo> filteredByStreams(List<Meal> meals, LocalTime startTime,
                                                 LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
//                      Collectors.toMap(Meal::getDate, Meal::getCalories, Integer::sum)
                );

        return meals.stream()
                .filter(meal -> TimeUtil.isBetweenHalfOpen(meal.getTime(), startTime, endTime))
                .map(meal -> createTo(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    private static MealTo createTo(Meal meal, boolean excess) {
        MealTo mealTo = new MealTo(meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
        mealTo.setId(meal.getId());
        return mealTo;
    }

    public static List<MealTo> allMealsTo (List<Meal> meals) {
        return filteredByStreams(meals, LocalTime.MIN, LocalTime.MAX,MealsUtil.CALORIES_DAY_LIMIT);
    }
}
