package ru.masnaviev.university.bridge.CarComponents;

import ru.masnaviev.university.bridge.enums.BodyColor;
import ru.masnaviev.university.bridge.enums.BodyModel;
import ru.masnaviev.university.bridge.interfaces.Body;

public class BasicBody implements Body {
    int id = 1;
    BodyColor color = BodyColor.WHITE;
    BodyModel model = BodyModel.SLIM;
    int price = 450000;


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
        return "\nBasicBody{" +
                "\nid=" + id +
                "\ncolor=" + color +
                "\nmodel=" + model +
                "\nprice=" + price +
                '}';
    }
}
