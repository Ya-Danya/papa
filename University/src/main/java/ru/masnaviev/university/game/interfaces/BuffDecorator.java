package ru.masnaviev.university.game.interfaces;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.masnaviev.university.game.unit.Unit;

public abstract class BuffDecorator extends Unit {
    @JsonProperty("decoratedUnit")
    public Unit decoratedUnit;
    protected int buffHealth;

    @JsonCreator
    public BuffDecorator(
            @JsonProperty("decoratedUnit") Unit decoratedUnit,
            @JsonProperty("buffHealth") int buffHealth,
            @JsonProperty("health") int health,
            @JsonProperty("maxHealth") int maxHealth,
            @JsonProperty("defense") int defense,
            @JsonProperty("meleeAttack") int meleeAttack,
            @JsonProperty("cost") int cost,
            @JsonProperty("clone") boolean isClone) {
        super(health, maxHealth, defense, meleeAttack, cost, isClone);
        this.decoratedUnit = decoratedUnit;
        this.buffHealth = buffHealth;
    }

    public BuffDecorator(Unit decoratedUnit) {
        super(decoratedUnit.getHealth(),
                decoratedUnit.getMaxHealth(),
                decoratedUnit.getDefense(),
                decoratedUnit.getMeleeAttack(),
                decoratedUnit.getCost(),
                decoratedUnit.isClone());
        this.decoratedUnit = decoratedUnit;
        this.buffHealth = 10;
    }

    @Override
    public void takeDamage(int damage) {
        int remainingDamage = Math.max(damage - buffHealth, 0);
        buffHealth = Math.max(buffHealth - damage, 0);

        if (buffHealth <= 0) {
            decoratedUnit.takeDamage(remainingDamage);
        }
    }

    @Override
    public boolean isAlive() {
        return decoratedUnit.isAlive();
    }

    public Unit removeBuff() {
        return decoratedUnit;
    }

    public Unit getDecoratedUnit() {
        return decoratedUnit;
    }

    public void setDecoratedUnit(Unit decoratedUnit) {
        this.decoratedUnit = decoratedUnit;
    }

    public int getBuffHealth() {
        return buffHealth;
    }

    public void setBuffHealth(int buffHealth) {
        this.buffHealth = buffHealth;
    }

    @Override
    public String getDisplayName() {
        return "BuffedHeavyInfantry";
    }
}