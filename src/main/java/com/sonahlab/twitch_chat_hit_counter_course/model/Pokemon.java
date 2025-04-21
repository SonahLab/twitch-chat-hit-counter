package com.sonahlab.twitch_chat_hit_counter_course.model;

/**
 * A simple Pokemon record to be used in Module 1, Exercise 2.
 * */
public class Pokemon {
    private final String name;
    private final String type;
    private int level;
    private int health;

    public Pokemon(String name, String type, int level, int health) {
        this.name = name;
        this.type = type;
        this.level = level;
        this.health = health;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getLevel() {
        return level;
    }

    public int getHealth() {
        return health;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public String toString() {
        return String.format("Pokemon{name='%s', type='%s', level=%d, health=%d}", name, type, level, health);
    }
}
