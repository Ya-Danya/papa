package ru.masnaviev.university.bridge.interfaces;

import ru.masnaviev.university.bridge.enums.ControlMechanism;
import ru.masnaviev.university.bridge.enums.TransmissionType;
import ru.masnaviev.university.bridge.enums.Undercarriage;

public interface Chassis extends CarComponent {
    Undercarriage getUndercarriageType();
    TransmissionType getTransmissionType();
    ControlMechanism getControllMechanism();
}
