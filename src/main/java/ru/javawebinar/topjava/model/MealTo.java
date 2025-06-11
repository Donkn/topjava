package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;

public class MealTo {
    private final LocalDateTime dateTime;
    private final String description;
    private final int calories;
    private final boolean excess;
    private final Integer id;

    public MealTo(LocalDateTime dateTime, String description, int calories, boolean excess, Integer id) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.excess = excess;
        this.id = id;
    }
    public MealTo(LocalDateTime dateTime, String description, int calories, boolean excess) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.excess = excess;
        this.id = 0;
    }
    public Integer getId() {
        return id;
    }
    @Override
    public String toString() {
        return "MealTo{" +
                "dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", excess=" + excess +
                '}';
    }
    public int getCalories() {
        return calories;
    }
    public String getDescription() {
        return description;
    }
    public boolean isExcess() {
        return excess;
    }
    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
