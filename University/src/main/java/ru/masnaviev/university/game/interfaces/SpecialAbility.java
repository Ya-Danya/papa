package ru.masnaviev.university.game.interfaces;


import ru.masnaviev.university.game.Team;

public interface SpecialAbility {
    void applyAbility(int[] i, Team attackingTeam, Team defendingTeam);
}
