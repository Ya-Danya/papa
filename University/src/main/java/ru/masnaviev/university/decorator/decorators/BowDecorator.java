package ru.masnaviev.university.decorator.decorators;

import ru.masnaviev.university.decorator.interfaces.*;

public class BowDecorator extends GiftDecorator {
    public BowDecorator(GiftComponent wrappedGift) {
        super(wrappedGift);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + ", с бантом";
    }

    @Override
    public int getCost() {
        return super.getCost() + 3;
    }
}
