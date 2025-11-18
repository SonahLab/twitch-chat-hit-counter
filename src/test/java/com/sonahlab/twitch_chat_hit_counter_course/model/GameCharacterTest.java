package com.sonahlab.twitch_chat_hit_counter_course.model;

import com.sonahlab.twitch_chat_hit_counter_course.utils.Potion;
import com.sonahlab.twitch_chat_hit_counter_course.utils.Stat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class GameCharacterTest {
    @Test
    @Tag("Module1")
    public void initTest() {
        GameCharacter character = new GameCharacter();
        Assertions.assertEquals(100, character.getStat(Stat.HP), "Default HP should be 100");
        Assertions.assertEquals(100, character.getStat(Stat.MP), "Default MP should be 100");
        assertThat(character.getInventory())
                .hasSize(2)
                .containsEntry(Potion.HP_POTION, 5)
                .containsEntry(Potion.MP_POTION, 5);

        // Validate the HP/MP setting logic (between 0 and 100)
        character.setHp(10000);
        Assertions.assertEquals(100, character.getStat(Stat.HP)); // Expected: 100 (hp should be between 0 and 100
                                                                           // we should ignore the previous 10000 set value)
        character.setHp(50);
        Assertions.assertEquals(50, character.getStat(Stat.HP));  // Expected: 50 (hp is valid between 0 and 100)

        character.setMp(10000);
        Assertions.assertEquals(100, character.getStat(Stat.MP)); // Expected: 100 (mp should be between 0 and 100
                                                                           // we should ignore the previous 10000 set value)

        character.setMp(0);
        Assertions.assertEquals(0, character.getStat(Stat.MP));   // Expected: 0 (mp is valid between 0 and 100)
    }

    @Test
    @Tag("Module1")
    public void takeDamageTest() {
        GameCharacter character = new GameCharacter();
        Assertions.assertEquals(50, character.takeDamage(50), "Example 1 failed");
        Assertions.assertEquals(0, character.takeDamage(110), "Example 2 failed");
    }

    @Test
    @Tag("Module1")
    public void consumePotionTest() {
        GameCharacter character = new GameCharacter();
        character.setHp(25);
        Assertions.assertEquals(75, character.consumePotion(Potion.HP_POTION));
        Assertions.assertEquals(75, character.getStat(Stat.HP));

        GameCharacter character2 = new GameCharacter();
        character2.setHp(99);
        Assertions.assertEquals(100, character2.consumePotion(Potion.HP_POTION));
        Assertions.assertEquals(100, character2.getStat(Stat.HP));

        GameCharacter character3 = new GameCharacter();
        character3.setMp(25);
        Assertions.assertEquals(75, character3.consumePotion(Potion.MP_POTION));
        Assertions.assertEquals(75, character3.getStat(Stat.MP));

        GameCharacter character4 = new GameCharacter();
        character4.setMp(99);
        Assertions.assertEquals(100, character4.consumePotion(Potion.MP_POTION));
        Assertions.assertEquals(100, character4.getStat(Stat.MP));

        GameCharacter character5 = new GameCharacter();
        character5.consumePotion(Potion.HP_POTION);
        character5.consumePotion(Potion.HP_POTION);
        character5.consumePotion(Potion.HP_POTION);
        character5.consumePotion(Potion.HP_POTION);
        character5.setHp(25);
        Assertions.assertEquals(75, character5.consumePotion(Potion.HP_POTION));
        Assertions.assertEquals(75, character5.getStat(Stat.HP));
        Assertions.assertFalse(character5.getInventory().containsKey(Potion.HP_POTION));

        GameCharacter character6 = new GameCharacter();
        character6.consumePotion(Potion.HP_POTION);
        character6.consumePotion(Potion.HP_POTION);
        character6.consumePotion(Potion.HP_POTION);
        character6.consumePotion(Potion.HP_POTION);
        character6.consumePotion(Potion.HP_POTION);
        character6.setHp(50);
        Assertions.assertEquals(-1, character6.consumePotion(Potion.HP_POTION));
        Assertions.assertFalse(character6.getInventory().containsKey(Potion.HP_POTION));
        Assertions.assertEquals(50, character6.getStat(Stat.HP));
    }

    @Test
    @Tag("Module1")
    public void getCharacterStateTest() {
        GameCharacter character = new GameCharacter();
        Map<String, Object> state = character.getCharacterState();
        Map<Potion, Integer> inventory = (Map<Potion, Integer>) state.get("INVENTORY");

        Assertions.assertEquals(100, state.get("HP"));
        Assertions.assertEquals(100, state.get("MP"));
        Assertions.assertEquals(5, inventory.get(Potion.HP_POTION));
        Assertions.assertEquals(5, inventory.get(Potion.MP_POTION));
    }
}
