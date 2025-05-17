package ru.masnaviev.university;

import java.util.ArrayList;
import java.util.List;

enum Event {
    MORNING_ROUTINE,
    AWAY_MODE,
    NIGHT_MODE,
    MOTION_DETECTED,
    TEMPERATURE_THRESHOLD_REACHED,
    ALARM_TRIGGERED,
    RECORDING_READY
}
// Было: Элементы не общаются между собой через Mediator.
// Он просто рассылает им действия и все, это больше похоже на фасад

// Стало: Элементы общаются с собой через медиатор

interface HomeMediator {
    void registerDevice(Device device);
    void notify(Event event, Device sender);
    void notify(Event event, Device sender, Object data);
    Object sendRequest(Device from, String deviceType, String request);
}

abstract class Device {
    protected HomeMediator mediator;
    protected String name;

    public Device(String name, HomeMediator m) {
        this.name = name;
        this.mediator = m;
        mediator.registerDevice(this);
    }

    public String getName() {
        return name;
    }

    public abstract void receive(Event event, Object data);
}

class SmartHomeMediator implements HomeMediator {
    private final List<Device> devices = new ArrayList<>();
    private double currentTemperature = 20.0;

    @Override
    public void registerDevice(Device d) {
        devices.add(d);
    }

    @Override
    public void notify(Event event, Device sender) {
        notify(event, sender, null);
    }

    @Override
    public void notify(Event event, Device sender, Object data) {
        String source = (sender == null ? "SYSTEM" : sender.getName());
        System.out.println("\n[Mediator] Event " + event + " from " + source + (data != null ? " with data: " + data : ""));

        switch (event) {
            case MORNING_ROUTINE -> devices.forEach(d -> d.receive(Event.MORNING_ROUTINE, null));
            case AWAY_MODE -> devices.forEach(d -> d.receive(Event.AWAY_MODE, null));
            case NIGHT_MODE -> devices.forEach(d -> d.receive(Event.NIGHT_MODE, null));
            case MOTION_DETECTED -> devices.stream()
                    .filter(d -> d instanceof Light || d instanceof AlarmSystem)
                    .forEach(d -> d.receive(Event.MOTION_DETECTED, null));
            case TEMPERATURE_THRESHOLD_REACHED -> devices.stream()
                    .filter(d -> d instanceof Thermostat || d instanceof SmartCurtains)
                    .forEach(d -> d.receive(Event.TEMPERATURE_THRESHOLD_REACHED, data));
            case ALARM_TRIGGERED -> devices.stream()
                    .filter(d -> d instanceof Camera)
                    .forEach(d -> d.receive(Event.MOTION_DETECTED, sender));
            case RECORDING_READY -> System.out.println("Запись доступна по ссылке: " + data);
        }
    }

    @Override
    public Object sendRequest(Device from, String deviceType, String request) {
        return devices.stream()
                .filter(d -> d.getClass().getSimpleName().equals(deviceType))
                .findFirst()
                .map(d -> {
                    if (d instanceof PresenceSensor && request.equals("CHECK_PRESENCE")) {
                        return ((PresenceSensor)d).checkPresence();
                    }
                    return null;
                })
                .orElse(null);
    }

    public void setTemperature(double temp) {
        currentTemperature = temp;
        if (temp > 25.0) {
            notify(Event.TEMPERATURE_THRESHOLD_REACHED, null, temp);
        }
    }
}

// 1. Умная сигнализация с камерами
class Camera extends Device {
    public Camera(String name, HomeMediator m) {
        super(name, m);
    }

    public String startRecording() {
        String recordingUrl = "https://camera/recording/" + System.currentTimeMillis();
        System.out.println(name + ": начата запись видео");
        return recordingUrl;
    }

    @Override
    public void receive(Event event, Object data) {
        if (event == Event.MOTION_DETECTED && data instanceof AlarmSystem) {
            String recordingUrl = startRecording();
            mediator.notify(Event.RECORDING_READY, this, recordingUrl);
        }
    }
}

class AlarmSystem extends Device {
    private boolean armed = false;

    public AlarmSystem(String name, HomeMediator m) {
        super(name, m);
    }

    @Override
    public void receive(Event event, Object data) {
        switch (event) {
            case AWAY_MODE -> arm();
            case MORNING_ROUTINE, NIGHT_MODE -> disarm();
            case MOTION_DETECTED -> {
                if (armed) triggerAlarm();
            }
        }
    }

    private void arm() {
        armed = true;
        System.out.println(name + ": сигнализация включена");
    }

    private void disarm() {
        armed = false;
        System.out.println(name + ": сигнализация отключена");
    }

    private void triggerAlarm() {
        System.out.println(name + ": Тревога! Обнаружено движение!");
        mediator.notify(Event.ALARM_TRIGGERED, this);
    }
}

// 2. Кофемашина + датчики присутствия
class PresenceSensor extends Device {
    public PresenceSensor(String name, HomeMediator m) {
        super(name, m);
    }

    public boolean checkPresence() {
        boolean someoneHere = Math.random() > 0.3;
        System.out.println(name + ": проверка присутствия - " + (someoneHere ? "кто-то есть" : "никого нет"));
        return someoneHere;
    }

    @Override
    public void receive(Event event, Object data) {
        // Датчик не реагирует на события
    }
}

class CoffeeMachine extends Device {
    public CoffeeMachine(String name, HomeMediator m) {
        super(name, m);
    }

    @Override
    public void receive(Event event, Object data) {
        if (event == Event.MORNING_ROUTINE) {
            boolean someoneInKitchen = (Boolean) mediator.sendRequest(this,
                    "PresenceSensor", "CHECK_PRESENCE");
            if (someoneInKitchen) {
                brewCoffee();
            } else {
                System.out.println(name + ": Никого нет на кухне, кофе не варю");
            }
        }
    }

    private void brewCoffee() {
        System.out.println(name + ": варю кофе ☕");
    }
}

// 3. Умные шторы + термостат
class SmartCurtains extends Device {
    private int openness = 100; // 100% открыты

    public SmartCurtains(String name, HomeMediator m) {
        super(name, m);
    }

    public void setOpenness(int percent) {
        openness = percent;
        System.out.println(name + ": установлена открытость " + percent + "%");
    }

    @Override
    public void receive(Event event, Object data) {
        if (event == Event.TEMPERATURE_THRESHOLD_REACHED) {
            double temp = (Double) data;
            setOpenness(temp > 25 ? 30 : 100);
        }
    }
}

class Thermostat extends Device {
    private double target = 22.0;

    public Thermostat(String name, HomeMediator m) {
        super(name, m);
    }

    @Override
    public void receive(Event event, Object data) {
        switch (event) {
            case MORNING_ROUTINE -> setTarget(21.0);
            case AWAY_MODE -> setTarget(18.0);
            case NIGHT_MODE -> setTarget(16.0);
            case TEMPERATURE_THRESHOLD_REACHED -> {
                double current = (Double) data;
                System.out.println(name + ": текущая температура " + current + "°C");
                if (current > 25) {
                    mediator.notify(Event.TEMPERATURE_THRESHOLD_REACHED, this, current);
                }
            }
        }
    }

    private void setTarget(double t) {
        target = t;
        System.out.println(name + ": целевая температура установлена " + target + "°C");
    }
}

class Light extends Device {
    private boolean on = false;

    public Light(String name, HomeMediator m) {
        super(name, m);
    }

    @Override
    public void receive(Event event, Object data) {
        switch (event) {
            case MORNING_ROUTINE -> turnOn();
            case AWAY_MODE, NIGHT_MODE -> turnOff();
            case MOTION_DETECTED -> turnOnTemporarily();
        }
    }

    private void turnOn() {
        on = true;
        System.out.println(name + ": свет включён");
    }

    private void turnOff() {
        on = false;
        System.out.println(name + ": свет выключён");
    }

    private void turnOnTemporarily() {
        System.out.println(name + ": движение — временно включаю свет");
    }
}

public class SmartHomeApp {
    public static void main(String[] args) {
        SmartHomeMediator mediator = new SmartHomeMediator();

        // Регистрация устройств
        new Light("Коридор", mediator);
        new Light("Кухня", mediator);
        new Thermostat("Термостат", mediator);
        new AlarmSystem("Сигнализация", mediator);
        new CoffeeMachine("Кофемашина", mediator);
        new Camera("Камера в коридоре", mediator);
        new PresenceSensor("Датчик кухни", mediator);
        new SmartCurtains("Шторы гостиной", mediator);

        // Тестирование сценариев
        System.out.println("\n=== Тестирование сценариев ===");

        // 1. Умная сигнализация с камерами
        System.out.println("\n1. Тест сигнализации и камер:");
        mediator.notify(Event.AWAY_MODE, null); // Включаем сигнализацию
        mediator.notify(Event.MOTION_DETECTED, null); // Обнаружено движение

        // 2. Кофемашина + датчики присутствия
        System.out.println("\n2. Тест кофемашины:");
        mediator.notify(Event.MORNING_ROUTINE, null); // Утренний режим

        // 3. Умные шторы + термостат
        System.out.println("\n3. Тест штор и термостата:");
        mediator.setTemperature(28.0); // Повышаем температуру
        mediator.setTemperature(22.0); // Понижаем температуру
    }
}