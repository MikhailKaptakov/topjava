package ru.javawebinar.topjava.database;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MealsDB {
    /**TODO
     * переделать базу так, чтобы создавался один инстанс объект базы данных, методы следовательно - не статические.
     * А в класе MealDAO, появится поле объекта бд, получаемое через getInstance. Инициализацию начальных данных
     * проводить в отдельном методе init() в приватном конструкторе. Конструктор защитить от многопоточности.
      */
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
        Meal meal = new Meal(dateTime, description, calories);
        meal.setId(id);
        mealsDB.add(meal);
    }

    public static Meal get(Integer id)  {
        for (Meal meal : mealsDB) {
            if (meal.getId().equals(id)) {
                return meal;
            }
        }
        return null;
    }
    public static boolean delete(Integer id) {
        Meal meal = get(id);
        if (meal == null) {
            return false;
        }
        mealsDB.remove(meal);
        return true;
    }

    public static boolean update(Integer id, LocalDateTime dateTime, String description, int calories) {
        if (delete(id)) {
            Meal meal = new Meal(dateTime, description, calories);
            meal.setId(id);
            mealsDB.add(meal);
            return true;
        } else {
            return false;
        }
    }

    public static List<Meal> getAll() {
        return mealsDB;
    }


}
