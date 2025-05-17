package ru.masnaviev.university.game;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.masnaviev.university.game.WalkingTown.TownAdapter;
import ru.masnaviev.university.game.WalkingTown.WalkingTown;
import ru.masnaviev.university.game.unit.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import static ru.masnaviev.university.game.unit.Mage.DEFAULT_MAX_HEALTH;

public class Team {
    public LinkedList<Unit> getUnits() {
        return units;
    }

    private final LinkedList<Unit> units;  // Список юнитов в команде
    private int balance;  // Баланс команды
    private LinkedList<Unit> availableUnits;  // Доступные для покупки юниты
    private final int initialBalance;

    @JsonCreator
    public Team(
            @JsonProperty("units") LinkedList<Unit> units,
            @JsonProperty("initialBalance") int initialBalance) {
        this.units = units;
        this.initialBalance = initialBalance;
    }

    // Конструктор
    public Team(int initialBalance) {
        units = new LinkedList<>();
        this.initialBalance = initialBalance;
        this.balance = initialBalance;  // Инициализация баланса
        availableUnits = new LinkedList<>();
        initializeAvailableUnits();  // Инициализация списка доступных юнитов
    }

    // Инициализация доступных юнитов
    private void initializeAvailableUnits() {
        availableUnits.add(new LightInfantry(LightInfantry.DEFAULT_MAX_HEALTH, 10, 15, 50, false));  // Легкий пехотинец
        availableUnits.add(new HeavyInfantry(HeavyInfantry.DEFAULT_MAX_HEALTH, 30, 50, 100, false));  // Тяжелый пехотинец
        availableUnits.add(new Archer(Archer.DEFAULT_MAX_HEALTH, false));  // Лучник
        availableUnits.add(new Mage(DEFAULT_MAX_HEALTH, false));  // Маг
        availableUnits.add(new Healer(Healer.DEFAULT_MAX_HEALTH, false));  // Лекарь
        availableUnits.add(new TownAdapter(new WalkingTown(250))); // Гуляй город
    }

    public void displayAvailableUnits() {
        System.out.println("Доступные юниты для покупки:");
        int i = 1;
        for (Unit unit : availableUnits) {
            System.out.println(i + ". " + unit.getClass().getSimpleName() + " - Стоимость: " + unit.getCost() + " золота");
            i++;
        }
    }

    // Метод для покупки юнита
    public boolean buyUnit(int unitIndex) {
        Unit unit;

        switch (unitIndex) {
            case 1:
                unit = new LightInfantry(LightInfantry.DEFAULT_MAX_HEALTH, 10, 15, 50, false);  // Легкий пехотинец
                break;
            case 2:
                unit = new HeavyInfantry(HeavyInfantry.DEFAULT_MAX_HEALTH, 30, 50, 100, false);  // Тяжелый пехотинец
                break;
            case 3:
                unit = new Archer(Archer.DEFAULT_MAX_HEALTH, false);  // Лучник
                break;
            case 4:
                unit = new Mage(DEFAULT_MAX_HEALTH, false);  // Маг
                break;
            case 5:
                unit = new Healer(90, false);  // Лекарь
                break;
            case 6: // Для TownAdapter
                unit = new TownAdapter(new WalkingTown(250));
                break;
            default:
                System.out.println("Некорректный выбор.");
                return false;
        }

        // Проверка, хватает ли средств для покупки
        if (balance >= unit.getCost()) {
            units.add(unit);  // Добавляем юнита в команду
            balance -= unit.getCost();  // Списываем стоимость юнита с баланса
            System.out.println(unit.getClass().getSimpleName() + " куплен успешно.");
            return true;
        } else {
            System.out.println("Недостаточно средств для покупки юнита.");
            return false;
        }
    }

    public List<String> getUnitStatus() {
        List<String> statuses = new ArrayList<>();
        for (Unit unit : units) {
            String status;
            if (unit instanceof TownAdapter) {
                status = "WalkingTown " + unit.getHealthString();
            } else {
                status = unit.getDisplayName() + ": " + unit.getHealthString();
            }
            statuses.add(status);
        }
        return statuses;
    }

    public int getInitialBalance() {
        return initialBalance;
    }

    public void removeDeadUnits() {
        units.removeIf(unit -> !unit.isAlive());  // Удаляет все юниты, у которых здоровье <= 0
    }

    // Пример для покупки юнитов через консоль
    public void startBuyingProcess() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nТекущий баланс: " + balance);
            displayAvailableUnits();
            System.out.print("Выберите номер юнита для покупки или 0 для выхода: ");
            int choice;
            try {
                choice = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Пожалуйста, введите корректный номер.");
                scanner.next(); // Очистка ввода
                continue;
            }
            if (choice == 0) {
                break;  // Выход из цикла покупок
            }
            buyUnit(choice);  // Покупка выбранного юнита
        }
    }
}
