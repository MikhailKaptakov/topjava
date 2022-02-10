package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.database.CRUDMealDB;
import ru.javawebinar.topjava.database.MealsDB;
import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public class MealDao implements CRUDMealDB {
    private final MealsDB connection;

    public MealDao() {
        this.connection = MealsDB.getInstance();
    }

    @Override
    public void add(Meal meal) {
        connection.add(meal.getDateTime(), meal.getDescription(), meal.getCalories());
    }

    @Override
    public boolean delete(Integer id) {
        return connection.delete(id);
    }

    @Override
    public boolean update(Meal meal) {
        return connection.update(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories());
    }

    @Override
    public List<Meal> getAll() {
        return connection.getAll();
    }

    @Override
    public Meal get(Integer id) {
        return connection.get(id);
    }
}
