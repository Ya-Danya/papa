package ru.masnaviev.university.decorator.decorators;

import ru.masnaviev.university.decorator.interfaces.*;

public class RibbonDecorator extends GiftDecorator {
    public RibbonDecorator(GiftComponent wrappedGift) {
        super(wrappedGift);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + ", с лентой";
    }

    @Override
    public int getCost() {
        return super.getCost() + 2;
    }
}