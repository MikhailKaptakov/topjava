package ru.javawebinar.topjava.database;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface CRUDMealDB {

    void add(Meal meal);
    boolean delete(Integer id);
    boolean update(Meal meal);
    List<Meal> getAll();
    Meal get(Integer id);
}
