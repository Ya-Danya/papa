package ru.masnaviev.university.decorator;


import ru.masnaviev.university.decorator.interfaces.GiftComponent;

class SimpleGift implements GiftComponent {
    @Override
    public String getDescription() {
        return "Подарок";
    }

    @Override
    public int getCost() {
        return 10; // Базовая стоимость
    }
}
