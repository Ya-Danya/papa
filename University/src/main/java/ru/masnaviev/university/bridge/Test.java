package ru.masnaviev.university.bridge;

public class Test {
    public static void main(String[] args) {
        CarConfigurator cc = new CarConfigurator();
        Car basic_car = cc.buildBasicModel();

        System.out.println("basic_car = " + basic_car);
        Car premium_car = cc.buildPremiumModel();
        System.out.println("premium_car = " + premium_car);
        Car sprot_car = cc.buildSportModel();
        System.out.println("sprot_car = " + sprot_car);
    }
}
