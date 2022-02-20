package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int NOT_FOUND = 10;

    public static final Meal meal1 = new Meal(100003, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0),
            "Завтрак", 500 );
    public static final Meal meal2 = new Meal(100004, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0),
            "Обед", 1000 );
    public static final Meal meal3 = new Meal(100005, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0),
            "Ужин", 500 );
    public static final Meal meal4 = new Meal(100006, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0),
            "Еда на граничное значение", 100 );
    public static final Meal meal5 = new Meal(100007, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0),
            "Завтрак", 1000 );
    public static final Meal meal6 = new Meal(100008, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0),
            "Обед", 500 );
    public static final Meal meal7 = new Meal(100009, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0),
            "Ужин", 410 );
    public static final Meal mealAdmin1 = new Meal(100010, LocalDateTime.of(2021, Month.JUNE, 1, 14, 0),
            "Админ ланч", 510 );
    public static final Meal mealAdmin2 = new Meal(100011, LocalDateTime.of(2021, Month.JUNE, 1, 21, 0),
            "Админ ужин", 1500 );

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 30, 15, 0),
                "description", 55);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(meal1);
        updated.setDateTime(LocalDateTime.of(2020, Month.JANUARY, 30, 11, 0));
        updated.setDescription("Updated Description");
        updated.setCalories(100);
        return updated;
    }
    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparator().isEqualTo(expected);
    }
}
