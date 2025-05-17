package ru.masnaviev.university.game.interfaces;

import ru.masnaviev.university.game.unit.Unit;

public interface UnitFactory {
    Unit createUnit();
    String getUnitType();
}