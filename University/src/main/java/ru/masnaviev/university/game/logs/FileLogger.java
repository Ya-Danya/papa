package ru.masnaviev.university.game.logs;

import ru.masnaviev.university.game.interfaces.GameLogger;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileLogger implements GameLogger {
    private final FileWriter writer;
    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public FileLogger(String filename) throws IOException {
        this.writer = new FileWriter(filename, true);
        log("Начало новой игровой сессии");
    }

    @Override
    public void log(String message) {
        String timestamp = LocalDateTime.now().format(formatter);
        try {
            writer.write(String.format("[%s] %s\n", timestamp, message));
            writer.flush();
        } catch (IOException e) {
            System.err.println("Ошибка записи в лог: " + e.getMessage());
        }
    }

    @Override
    public void close() {
        try {
            log("Завершение игровой сессии");
            writer.close();
        } catch (IOException e) {
            System.err.println("Ошибка закрытия логгера: " + e.getMessage());
        }
    }
}