package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static boolean isBetweenHalfOpen(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) < 0;
    }

    public static boolean isBetweenDate(LocalDate date, LocalDate start, LocalDate end) {
        return  date.compareTo(start) >=0 && date.compareTo(end) <= 0;
    }

    public static boolean isBetweenDateTime(LocalDateTime ldt, LocalDateTime start, LocalDateTime end) {
        return isBetweenDate(ldt.toLocalDate(), start.toLocalDate(),end.toLocalDate())
                && isBetweenHalfOpen(ldt.toLocalTime(), start.toLocalTime(), end.toLocalTime());
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}

