package ru.javawebinar.topjava.database;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MealsDB {
    private final static AtomicInteger counter = new AtomicInteger(0);
    private final static List<Meal> mealsDB = Collections.synchronizedList(new ArrayList<>());

    static {
        add(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0),
                "Завтрак", 500);
        add(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0),
                "Обед", 1000);
        add(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0),
                                "Ужин", 500);
        add(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0),
                        "Еда на граничное значение", 100);
        add(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0),
                        "Завтрак", 1000);
        add(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0),
                        "Обед", 500);
        add(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0),
                "Ужин", 410);
    }

    public static void add(LocalDateTime dateTime, String description, int calories) {
        Integer id = counter.incrementAndGet();
        mealsDB.add(new Meal(dateTime, description, calories, id));
    }

    public static void delete(Integer id) throws NotFoundException {
        Meal meal = get(id);
        mealsDB.remove(meal);
    }

    public static List<Meal> getAll() {
        return mealsDB;
    }

    private static Meal get(Integer id) throws NotFoundException {
        for (Meal meal : mealsDB) {
            if (meal.getId().equals(id)) {
                return meal;
            }
        }
        throw new NotFoundException();
    }
}
