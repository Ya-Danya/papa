package ru.masnaviev.university.bridge.CarComponents;

import ru.masnaviev.university.bridge.interfaces.Engine;

public class V12Engine implements Engine {
    private final int horse_powers = 700;
    private final int bars = 12;
    private final int id = 12;
    private final int price = 100500;

    @Override
    public int getMaxHP() {
        return horse_powers;
    }

    @Override
    public int getBars() {
        return bars;
    }

    @Override
    public int getComponentID() {
        return id;
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "\nV12Engine{" +
                "\nhorse_powers=" + horse_powers +
                "\nbars=" + bars +
                "\nid=" + id +
                "\nprice=" + price +
                '}';
    }
}
