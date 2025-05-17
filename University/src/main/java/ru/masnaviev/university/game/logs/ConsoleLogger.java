package ru.masnaviev.university.game.logs;

import ru.masnaviev.university.game.interfaces.GameLogger;

public class ConsoleLogger implements GameLogger {
    @Override
    public void log(String message) {
        System.out.println("[LOG] " + message);
    }

    @Override
    public void close() {}
}
