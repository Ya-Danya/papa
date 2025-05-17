package ru.masnaviev.university.game.unit;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.*;
import ru.masnaviev.university.game.WalkingTown.TownAdapter;
import ru.masnaviev.university.game.buffs.*;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = LightInfantry.class, name = "lightInfantry"),
        @JsonSubTypes.Type(value = HeavyInfantry.class, name = "heavyInfantry"),
        @JsonSubTypes.Type(value = Archer.class, name = "archer"),
        @JsonSubTypes.Type(value = Mage.class, name = "mage"),
        @JsonSubTypes.Type(value = Healer.class, name = "healer"),
        @JsonSubTypes.Type(value = TownAdapter.class, name = "town"),
        @JsonSubTypes.Type(value = ImprovedWeaponDecorator.class, name = "improvedWeapon"),
        @JsonSubTypes.Type(value = ShieldDecorator.class, name = "shield"),
})
public abstract class Unit {
    protected final int maxHealth; // Максимальное здоровье (неизменяемое)
    protected int health; // Текущее здоровье
    protected int defense; // Защита
    protected int meleeAttack; // Ближний бой
    protected int cost; // Стоимость
    protected boolean isClone; // Клон или нет

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public void takeDamage(int damage) {
        int effectiveDamage = Math.max(damage - getDefense(), 0);  // Урон, после учета защиты
        setHealth(getHealth() - effectiveDamage);
    }

    @JsonCreator
    public Unit(
            @JsonProperty("health") int health,
            @JsonProperty("maxHealth") int maxHealth,
            @JsonProperty("defense") int defense,
            @JsonProperty("meleeAttack") int meleeAttack,
            @JsonProperty("cost") int cost,
            @JsonProperty("isClone") boolean isClone) {
        this.health = health;
        this.maxHealth = maxHealth;
        this.defense = defense;
        this.meleeAttack = meleeAttack;
        this.cost = cost;
        this.isClone = isClone;
    }

    // Ближняя атака
    public void performMeleeAttack(Unit enemy) {
        if (enemy == null || !enemy.isAlive()) {
            System.out.println("Цель мертва или не существует.");
            return;
        }
        enemy.takeDamage(meleeAttack);
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = Math.min(health, maxHealth); // Не превышает maxHealth
    }

    public int getMeleeAttack() {
        return meleeAttack;
    }

    public int getCost() {
        return cost;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public boolean isClone() {return isClone;}

    public int getMaxHealth() {return maxHealth;}

    public String getHealthString() {
        return health + "/" + maxHealth + " HP";
    }

    public int getDefense() {
        return defense;
    }

    public String getDisplayName() {
        return this.getClass().getSimpleName();
    }


}