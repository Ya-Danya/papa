package ru.masnaviev.university.bridge;

import ru.masnaviev.university.bridge.interfaces.*;

import java.util.Objects;

public class Car {
    private Engine engine;
    private Chassis chassis;
    private Body body;

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }


    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(engine, car.engine) && Objects.equals(chassis, car.chassis) && Objects.equals(body, car.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(engine, chassis, body);
    }

    public Chassis getChassis() {
        return chassis;
    }

    public void setChassis(Chassis chassis) {
        this.chassis = chassis;
    }

    @Override
    public String toString() {
        return "\nCar{" +
                "\nengine=" + engine +
                "\nchassis=" + chassis +
                "\nbody=" + body +
                "}\n\n";
    }
}
