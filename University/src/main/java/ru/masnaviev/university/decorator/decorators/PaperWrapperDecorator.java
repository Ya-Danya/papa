package ru.masnaviev.university.decorator.decorators;

import ru.masnaviev.university.decorator.interfaces.*;

public class PaperWrapperDecorator extends GiftDecorator {
    public PaperWrapperDecorator(GiftComponent wrappedGift) {
        super(wrappedGift);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + ", Упаковано в бумагу";
    }

    @Override
    public int getCost() {
        return super.getCost() + 5;
    }
}

