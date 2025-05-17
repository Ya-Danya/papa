package ru.masnaviev.university.bridge.CarComponents;

import ru.masnaviev.university.bridge.enums.BodyColor;
import ru.masnaviev.university.bridge.enums.BodyModel;
import ru.masnaviev.university.bridge.interfaces.Body;

public class PremiumBody implements Body {
    int id = 3;
    BodyColor color = BodyColor.BLACK;
    BodyModel model = BodyModel.EXTENDED;
    int price = 1000000;


    @Override
    public BodyColor getColor() {
        return color;
    }

    @Override
    public BodyModel getModel() {
        return model;
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
        return "\nSportBody{" +
                "\nid=" + id +
                "\ncolor=" + color +
                "\nmodel=" + model +
                "\nprice=" + price +
                '}';
    }
}
