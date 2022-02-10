package ru.javawebinar.topjava.database;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class MealsDB {

    private static volatile MealsDB mealsDB;
    private final static AtomicInteger counter = new AtomicInteger(0);
    private final List<Meal> meals;

    private MealsDB() {
        meals = new CopyOnWriteArrayList<>();
        this.init();
    }

    public static MealsDB getInstance() {
        MealsDB localInstance = mealsDB;
        if (localInstance == null) {
            synchronized (MealsDB.class) {
                localInstance = mealsDB;
                if (localInstance == null) {
                    mealsDB = localInstance = new MealsDB();
                }
            }
        }
        return localInstance;
    }

    private void init() {
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

    public void add(LocalDateTime dateTime, String description, int calories) {
        Integer id = counter.incrementAndGet();
        Meal meal = new Meal(dateTime, description, calories);
        meal.setId(id);
        meals.add(meal);
    }

    public Meal get(Integer id)  {
        for (Meal meal : meals) {
            if (meal.getId().equals(id)) {
                return meal;
            }
        }
        return null;
    }
    public boolean delete(Integer id) {
        Meal meal = get(id);
        if (meal == null) {
            return false;
        }
        meals.remove(meal);
        return true;
    }

    public boolean update(Integer id, LocalDateTime dateTime, String description, int calories) {
        if (delete(id)) {
            Meal meal = new Meal(dateTime, description, calories);
            meal.setId(id);
            meals.add(meal);
            return true;
        } else {
            return false;
        }
    }

    public List<Meal> getAll() {
        return meals;
    }


}
