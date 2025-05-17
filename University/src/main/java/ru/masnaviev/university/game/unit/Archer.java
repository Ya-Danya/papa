package ru.masnaviev.university.game.unit;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import ru.masnaviev.university.game.Team;
import ru.masnaviev.university.game.interfaces.ICanBeCloned;
import ru.masnaviev.university.game.interfaces.SpecialAbility;


@JsonTypeName("Archer")
public class Archer extends Unit implements SpecialAbility, ICanBeCloned {
    public static final int DEFAULT_MAX_HEALTH = 80;
    public static final int DEFAULT_RANGE = 3;
    public static final double DEFAULT_HIT_CHANCE = 0.8;
    public static final int DEFAULT_ARROW_DAMAGE = 15;

    private final int range;
    private double hitChance;
    private int arrow_damage;

    public Archer(int health, boolean isClone) {
        super(health, DEFAULT_MAX_HEALTH, 5, 10, 70, isClone);
        this.range = DEFAULT_RANGE;
        this.hitChance = DEFAULT_HIT_CHANCE;
        this.arrow_damage = DEFAULT_ARROW_DAMAGE;
    }

    @JsonCreator
    public Archer(
            @JsonProperty("health") int health,
            @JsonProperty("maxHealth") int maxHealth,
            @JsonProperty("defense") int defense,
            @JsonProperty("meleeAttack") int meleeAttack,
            @JsonProperty("cost") int cost,
            @JsonProperty("isClone") boolean isClone,
            @JsonProperty("range") int range,
            @JsonProperty("hitChance") double hitChance,
            @JsonProperty("arrowDamage") int arrowDamage) {
        super(health, maxHealth, defense, meleeAttack, cost, isClone);
        this.range = range;
        this.hitChance = hitChance;
        this.arrow_damage = arrowDamage;
    }

    @Override
    public void applyAbility(int[] i, Team attackingTeam, Team defendingTeam) {
        if (range <= i[0]) {
            return;
        } else {
            int enemy_team_range = Math.abs(range - i[0]);
            int target_index = (int)Math.floor(Math.random() * ++enemy_team_range);

            if (Math.random() < hitChance) {
                defendingTeam.getUnits().get(target_index).takeDamage(arrow_damage);
            }
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public Unit cloneUnit() {
        return new Archer(getHealth(), true);
    }
}
