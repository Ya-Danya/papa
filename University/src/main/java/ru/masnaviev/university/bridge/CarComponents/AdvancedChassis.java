package ru.masnaviev.university.bridge.CarComponents;

import ru.masnaviev.university.bridge.enums.ControlMechanism;
import ru.masnaviev.university.bridge.enums.TransmissionType;
import ru.masnaviev.university.bridge.enums.Undercarriage;
import ru.masnaviev.university.bridge.interfaces.Chassis;

public class AdvancedChassis implements Chassis {
    Undercarriage undercarriage = Undercarriage.HEAVYWEIGHT;
    TransmissionType transmission = TransmissionType.HYDRO;
    ControlMechanism controlMechanism = ControlMechanism.ADVANCED;
    int price = 400000;
    int id = 21;

    @Override
    public Undercarriage getUndercarriageType() {
        return null;
    }

    @Override
    public TransmissionType getTransmissionType() {
        return null;
    }

    @Override
    public ControlMechanism getControllMechanism() {
        return null;
    }

    @Override
    public String toString() {
        return "\nAdvancedChassis{" +
                "\nundercarriage=" + undercarriage +
                "\ntransmission=" + transmission +
                "\ncontrolMechanism=" + controlMechanism +
                '}';
    }

    @Override
    public int getComponentID() {
        return id;
    }

    @Override
    public int getPrice() {
        return price;
    }
}
