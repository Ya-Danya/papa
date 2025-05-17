package ru.masnaviev.university.decorator;

import ru.masnaviev.university.decorator.decorators.*;
import ru.masnaviev.university.decorator.interfaces.*;

import java.util.Scanner;

public class GiftDemo {
    public static void main(String[] args) {
        WrapperMan man = new WrapperMan();

        man.helpCustomer(new Customer("lesha"));
    }
}
