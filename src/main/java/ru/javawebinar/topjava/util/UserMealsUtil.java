package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesMap = new HashMap<>();
        List<UserMealWithExcess> userMealWithExcesses = new ArrayList<>();
        for (UserMeal meal: meals) {
            LocalDate date = meal.getDateTime().toLocalDate();
            if (caloriesMap.containsKey(date)) {
                int calories = caloriesMap.get(date);
                caloriesMap.put(date, calories + meal.getCalories());
            } else {
                caloriesMap.put(date,meal.getCalories());
            }
        }
        for (UserMeal meal : meals) {
            LocalTime time = meal.getDateTime().toLocalTime();
            LocalDate date = meal.getDateTime().toLocalDate();
            if (time.isAfter(startTime)&&time.isBefore(endTime)){
                if (caloriesMap.get(date) > caloriesPerDay) {
                    userMealWithExcesses.add(new UserMealWithExcess(meal,true));
                } else {
                    userMealWithExcesses.add(new UserMealWithExcess(meal, false));
                }
            }
        }
        return userMealWithExcesses;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesMap = new HashMap<>();
        List<UserMeal> copyMeals = meals.stream()
                .peek(w -> {
                    LocalDate date = w.getDateTime().toLocalDate();
                    if (caloriesMap.containsKey(date)) {
                        int calories = caloriesMap.get(date);
                        caloriesMap.put(date, calories + w.getCalories());
                    } else {
                    caloriesMap.put(date,w.getCalories());
                    }
                }).collect(Collectors.toList());
        return copyMeals.stream()
                .filter(w -> (w.getDateTime().toLocalTime().isAfter(startTime)&&w.getDateTime().toLocalTime().isBefore(endTime)))
                .map(w -> {
                    if (caloriesMap.get(w.getDateTime().toLocalDate()) > caloriesPerDay) {
                        return new UserMealWithExcess(w,true);
                    } else {
                        return new UserMealWithExcess(w, false);
                    }
                }).collect(Collectors.toList());
    }
}
