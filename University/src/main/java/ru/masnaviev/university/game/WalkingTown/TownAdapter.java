package ru.masnaviev.university.game.WalkingTown;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import ru.masnaviev.university.game.unit.Unit;

@JsonTypeName("TownAdapter")
public class TownAdapter extends Unit {
    private final WalkingTown town;

    public TownAdapter(WalkingTown town) {
        super(town.getHealth(),
                town.getMaxHealth(),
                0,    // defense
                0,    // meleeAttack
                75,    // cost
                false); // isClone
        this.town = town;
    }

    @JsonCreator
    public TownAdapter(
            @JsonProperty("town") WalkingTown town,
            @JsonProperty("health") int health,
            @JsonProperty("maxHealth") int maxHealth,
            @JsonProperty("defense") int defense,
            @JsonProperty("meleeAttack") int meleeAttack,
            @JsonProperty("cost") int cost,
            @JsonProperty("clone") boolean isClone) {
        super(health, maxHealth, defense, meleeAttack, cost, isClone);
        this.town = town;
    }

    @Override
    public void performMeleeAttack(Unit enemy) {
        // Город не атакует
    }

    @Override
    public void takeDamage(int damage) {
        town.takeDamage(damage);
        setHealth(town.getHealth()); // Синхронизируем состояние
    }

    @Override
    public String getHealthString() {
        return "[TOWN] " + super.getHealthString();
    }

    @Override
    public boolean isAlive() {
        return town.isAlive();
    }
}