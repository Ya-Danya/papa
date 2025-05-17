package ru.masnaviev.university.game.unit;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import ru.masnaviev.university.game.Team;
import ru.masnaviev.university.game.buffs.ImprovedWeaponDecorator;
import ru.masnaviev.university.game.buffs.ShieldDecorator;
import ru.masnaviev.university.game.interfaces.ICanBeCloned;
import ru.masnaviev.university.game.interfaces.SpecialAbility;
@JsonTypeName("LightInfantry")
public class LightInfantry extends Unit implements ICanBeCloned, SpecialAbility {
    public static final int DEFAULT_MAX_HEALTH = 100;

    public LightInfantry(int health, int defense, int meleeAttack, int cost, boolean isClone) {
        super(health, DEFAULT_MAX_HEALTH, defense, meleeAttack, cost, isClone);
    }

    @JsonCreator
    public LightInfantry(
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

    public LightInfantry(int health, int defense, int meleeAttack, int cost) {
        super(health, DEFAULT_MAX_HEALTH, defense, meleeAttack, cost, false);
    }

    @Override
    public Unit cloneUnit() {
        return new LightInfantry(getHealth(), getDefense(), getMeleeAttack(), getCost(), true);
    }

    private void applyRandomBuff(Team team, int position, Unit target) {
        int random = (int)(Math.random() * 3);
        Unit buffedUnit;

        switch (random) {
            case 0:
                buffedUnit = new ImprovedWeaponDecorator(target);
                break;
            case 1:
                buffedUnit = new ShieldDecorator(target);
                break;
            default:
                return;
        }

        team.getUnits().set(position, buffedUnit);
    }

    @Override
    public void applyAbility(int[] position, Team attackingTeam, Team defendingTeam) {
        int currentPosition = position[0];
        if (currentPosition > 0) {
            Unit frontUnit = attackingTeam.getUnits().get(currentPosition - 1);

            if (frontUnit instanceof HeavyInfantry) {
                applyRandomBuff(attackingTeam, currentPosition - 1, frontUnit);
            }
        }
    }
}
