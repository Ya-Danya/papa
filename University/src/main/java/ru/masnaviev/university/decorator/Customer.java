package ru.masnaviev.university.decorator;

import ru.masnaviev.university.decorator.interfaces.GiftComponent;
import ru.masnaviev.university.decorator.interfaces.GiftDecorator;

import java.util.Scanner;

public class Customer {
    private String name;
    GiftComponent gift;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Customer(String name) {
        this.name = name;
        this.gift = new SimpleGift();
    }
}
