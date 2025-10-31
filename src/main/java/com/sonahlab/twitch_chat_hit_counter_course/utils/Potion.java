package com.sonahlab.twitch_chat_hit_counter_course.utils;

import static com.sonahlab.twitch_chat_hit_counter_course.utils.Stat.HP;
import static com.sonahlab.twitch_chat_hit_counter_course.utils.Stat.MP;

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
