package ru.masnaviev.university.game.unit;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import ru.masnaviev.university.game.Team;
import ru.masnaviev.university.game.interfaces.ICanBeCloned;
import ru.masnaviev.university.game.interfaces.SpecialAbility;

@JsonTypeName("Healer")
public class Healer extends Unit implements SpecialAbility, ICanBeCloned {
    public static final int DEFAULT_MAX_HEALTH = 90;
    public static final int DEFAULT_HEAL_POWER = 25;
    public static final double DEFAULT_HEAL_CHANCE = 0.7;
    public static final int DEFAULT_HEAL_RANGE = 25;

    private final int healPower;
    private final double healChance;
    private final int range;

    public Healer(int health, boolean isClone) {
        super(health, DEFAULT_MAX_HEALTH, 5, 5, 120, isClone);
        this.healPower = DEFAULT_HEAL_POWER;
        this.healChance = DEFAULT_HEAL_CHANCE;
        this.range = DEFAULT_HEAL_RANGE;
    }

    @JsonCreator
    public Healer(
            @JsonProperty("health") int health,
            @JsonProperty("maxHealth") int maxHealth,
            @JsonProperty("defense") int defense,
            @JsonProperty("meleeAttack") int meleeAttack,
            @JsonProperty("cost") int cost,
            @JsonProperty("isClone") boolean isClone,
            @JsonProperty("healPower") int healPower,
            @JsonProperty("healChance") double healChance,
            @JsonProperty("range") int range) {
        super(health, maxHealth, defense, meleeAttack, cost, isClone);
        this.healPower = healPower;
        this.healChance = healChance;
        this.range = range;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public void applyAbility(int[] i, Team attackingTeam, Team defendingTeam) {

        for (int j = Math.max(i[0] - range, 0); j < i[0]; j++) {
            Unit target = attackingTeam.getUnits().get(j);

            if (target.health < target.maxHealth) {
                if (Math.random() < healChance) {
                    target.setHealth(Math.min(target.health + healPower, target.maxHealth));
                }
                return;
            }
        }
    }

    @Override
    public Unit cloneUnit() {
        return new Healer(getHealth(), true);
    }

}

