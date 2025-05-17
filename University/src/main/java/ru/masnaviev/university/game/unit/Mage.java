package ru.masnaviev.university.game.unit;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import ru.masnaviev.university.game.Team;
import ru.masnaviev.university.game.interfaces.ICanBeCloned;
import ru.masnaviev.university.game.interfaces.SpecialAbility;


@JsonTypeName("Mage")
public class Mage extends Unit implements SpecialAbility {
    public static final int DEFAULT_MAX_HEALTH = 60;
    public static final int CLONE_RANGE = 2;
    public static final double CLONE_CHANCE = 0.4;

    private int range;
    private double clone_chance;

    @JsonCreator
    public Mage(int health, boolean isClone) {
        super(health, DEFAULT_MAX_HEALTH, 2, 5, 150, isClone);
        this.range = CLONE_RANGE;
        this.clone_chance = CLONE_CHANCE;
    }

    @JsonCreator
    public Mage(
            @JsonProperty("health") int health,
            @JsonProperty("maxHealth") int maxHealth,
            @JsonProperty("defense") int defense,
            @JsonProperty("meleeAttack") int meleeAttack,
            @JsonProperty("cost") int cost,
            @JsonProperty("isClone") boolean isClone,
            @JsonProperty("range") int range,
            @JsonProperty("cloneChance") double cloneChance) {
        super(health, maxHealth, defense, meleeAttack, cost, isClone);
        this.range = range;
        this.clone_chance = cloneChance;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public void applyAbility(int[] i, Team attackingTeam, Team defendingTeam) {

        for (int j = Math.max(i[0] - range, 0); j < i[0]; j++) {
            Unit target = attackingTeam.getUnits().get(j);

            if (!target.isClone && target instanceof ICanBeCloned) {
                if (Math.random() < clone_chance) {
                    attackingTeam.getUnits().add(i[0],((ICanBeCloned) target).cloneUnit());
                }
                i[0] = i[0] + 1;
                return;
            }
        }
    }
}
