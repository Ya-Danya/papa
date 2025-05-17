package ru.masnaviev.university.bridge.interfaces;

import ru.masnaviev.university.bridge.enums.BodyColor;
import ru.masnaviev.university.bridge.enums.BodyModel;

public interface Body extends CarComponent {
    BodyColor getColor();
    BodyModel getModel();
}
