package ru.masnaviev.university.bridge.CarComponents;

import ru.masnaviev.university.bridge.interfaces.Engine;

public class V6Engine implements Engine {
    private final int horse_powers = 290;
    private final int bars = 6;
    private final int id = 6;
    private final int price = 30000;

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
        return "\nV6Engine{" +
                "\nhorse_powers=" + horse_powers +
                "\nbars=" + bars +
                "\nid=" + id +
                "\nprice=" + price +
                '}';
    }
}
