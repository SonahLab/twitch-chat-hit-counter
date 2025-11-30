package com.sonahlab.twitch_chat_hit_counter_course.model;

import static com.sonahlab.twitch_chat_hit_counter_course.model.Stat.*;

/**
 * Defines the Potion type.
 *
 * This enum will be used in Module 1 Exercise 2 when creating HTTP requests to handle different state actions on a
 * generic Fantasy Game like {@link GameCharacter}.
 * */
public enum Potion {
    HP_POTION(HP, 50),
    MP_POTION(MP, 50);

    private Stat stat;
    private int statIncrement;

    Potion(Stat stat, int statIncrement) {
        this.stat = stat;
        this.statIncrement = statIncrement;
    }

    public Stat getStat() {
        return stat;
    }

    public int getStatIncrement() {
        return statIncrement;
    }
}
