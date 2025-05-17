package ru.masnaviev.university.decorator.interfaces;

public abstract class GiftDecorator implements GiftComponent {
    protected GiftComponent wrappedGift;

    public GiftDecorator(GiftComponent wrappedGift) {
        this.wrappedGift = wrappedGift;
    }

    public GiftComponent getWrappedGift() {
        return wrappedGift;
    }

    @Override
    public String getDescription() {
        return wrappedGift.getDescription();
    }

    @Override
    public int getCost() {
        return wrappedGift.getCost();
    }
}
