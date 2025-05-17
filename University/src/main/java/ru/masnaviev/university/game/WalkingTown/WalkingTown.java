package ru.masnaviev.university.game.WalkingTown;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class WalkingTown {
    private int health;
    private final int maxHealth;

    public WalkingTown(int maxHealth) {
        this.maxHealth = maxHealth;
        this.health = maxHealth;
    }

    @JsonCreator
    public WalkingTown(
            @JsonProperty("health") int health,
            @JsonProperty("maxHealth") int maxHealth) {
        this.health = health;
        this.maxHealth = maxHealth;
    }

    public void takeDamage(int damage) {
        health = Math.max(health - damage, 0);
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public boolean isAlive() {
        return health > 0;
    }
}