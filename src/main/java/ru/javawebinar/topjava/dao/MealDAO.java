package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.database.CRUDMealDB;
import ru.javawebinar.topjava.database.MealsDB;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

public class MealDAO implements CRUDMealDB {
    @Override
    public void add(Meal meal) {
        MealsDB.add(meal.getDateTime(), meal.getDescription(), meal.getCalories());
    }

    @Override
    public boolean delete(Integer id) {
        return MealsDB.delete(id);
    }

    @Override
    public boolean update(Meal meal) {
        return MealsDB.update(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories());
    }

    @Override
    public List<Meal> getAll() {
        return MealsDB.getAll();
    }

    @Override
    public Meal get(Integer id) {
        return MealsDB.get(id);
    }
}
