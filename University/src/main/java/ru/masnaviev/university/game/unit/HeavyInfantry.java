package ru.masnaviev.university.game.unit;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import ru.masnaviev.university.game.interfaces.ICanBeCloned;

@JsonTypeName("HeavyInfantry")
public class HeavyInfantry extends Unit implements ICanBeCloned {
    public static final int DEFAULT_MAX_HEALTH = 200;

    public HeavyInfantry(int health, int defense, int meleeAttack, int cost, boolean isClone) {
        super(health, DEFAULT_MAX_HEALTH, defense, meleeAttack, cost, isClone);
    }

    @JsonCreator
    public HeavyInfantry(
            @JsonProperty("health") int health,
            @JsonProperty("maxHealth") int maxHealth,
            @JsonProperty("defense") int defense,
            @JsonProperty("meleeAttack") int meleeAttack,
            @JsonProperty("cost") int cost,
            @JsonProperty("isClone") boolean isClone) {
        super(health, maxHealth, defense, meleeAttack, cost, isClone);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public Unit cloneUnit() {
        return new HeavyInfantry(getHealth(), getDefense(), getMeleeAttack(), getCost(), true);
    }
}

