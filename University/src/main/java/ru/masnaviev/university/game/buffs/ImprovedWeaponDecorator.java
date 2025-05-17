package ru.masnaviev.university.game.buffs;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.masnaviev.university.game.interfaces.BuffDecorator;
import ru.masnaviev.university.game.unit.Unit;

public class ImprovedWeaponDecorator extends BuffDecorator {
    @JsonCreator
    public ImprovedWeaponDecorator(
            @JsonProperty("decoratedUnit") Unit decoratedUnit,
            @JsonProperty("buffHealth") int buffHealth,
            @JsonProperty("health") int health,
            @JsonProperty("maxHealth") int maxHealth,
            @JsonProperty("defense") int defense,
            @JsonProperty("meleeAttack") int meleeAttack,
            @JsonProperty("cost") int cost,
            @JsonProperty("clone") boolean isClone) {
        super(decoratedUnit, buffHealth, health, maxHealth, defense, meleeAttack, cost, isClone);
    }

    public ImprovedWeaponDecorator(Unit decoratedUnit) {
        super(decoratedUnit);
    }

    @Override
    public int getMeleeAttack() {
        return decoratedUnit.getMeleeAttack() + 10;
    }
}
