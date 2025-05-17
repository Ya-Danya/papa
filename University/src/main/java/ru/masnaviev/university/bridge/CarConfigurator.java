package ru.masnaviev.university.bridge;

import ru.masnaviev.university.bridge.CarComponents.*;

public class CarConfigurator {
    Car buildBasicModel() {
        Car car = new Car();
        car.setBody(new BasicBody());
        car.setChassis(new BasicChassis());
        car.setEngine(new V6Engine());
        return car;
    }

    Car buildSportModel() {
        Car car = new Car();
        car.setBody(new SportBody());
        car.setChassis(new AdvancedChassis());
        car.setEngine(new V12Engine());
        return car;
    }

    Car buildPremiumModel() {
        Car car = new Car();
        car.setBody(new SportBody());
        car.setChassis(new AdvancedChassis());
        car.setEngine(new V12Engine());
        return car;
    }
}
