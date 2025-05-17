package ru.masnaviev.university.game;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import ru.masnaviev.university.game.WalkingTown.TownAdapter;
import ru.masnaviev.university.game.interfaces.BuffDecorator;
import ru.masnaviev.university.game.interfaces.GameLogger;
import ru.masnaviev.university.game.interfaces.SpecialAbility;
import ru.masnaviev.university.game.logs.ConsoleLogger;
import ru.masnaviev.university.game.unit.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

import static java.lang.Thread.sleep;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Game {
    private static Game instance;
    private final Team team1;
    private final Team team2;
    private boolean isTeam1Turn;
    private static final Scanner scanner = new Scanner(System.in);
    private GameLogger logger = new ConsoleLogger();

    // Приватный конструктор
    private Game(int initialBalance) {
        this.team1 = new Team(initialBalance);
        this.team2 = new Team(initialBalance);
        this.isTeam1Turn = new Random().nextBoolean();
    }

    public void setLogger(GameLogger logger) {
        this.logger = logger;
    }

    public static Game getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Game not initialized");
        }
        return instance;
    }


    @JsonCreator
    public Game(
            @JsonProperty("team1") Team team1,
            @JsonProperty("team2") Team team2,
            @JsonProperty("isTeam1Turn") boolean isTeam1Turn) {
        this.team1 = team1;
        this.team2 = team2;
        this.isTeam1Turn = isTeam1Turn;
        instance = this;
    }

    public static void initialize(int initialBalance) {
        instance = new Game(
                new Team(initialBalance),
                new Team(initialBalance),
                new Random().nextBoolean()
        );
    }

    public static void newGame(int initialBalance) {
        instance = new Game(
                new Team(initialBalance),
                new Team(initialBalance),
                new Random().nextBoolean()
        );
    }

    // Метод для получения экземпляра Singleton
    public static synchronized Game getInstance(int initialBalance) {
        if (instance == null) {
            instance = new Game(initialBalance);
        }
        return instance;
    }

    private static ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        return mapper;
    }

    public void saveToFile(String filename) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filename), this);
    }

    public static Game loadFromFile(String filename) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerSubtypes(
                LightInfantry.class,
                HeavyInfantry.class,
                TownAdapter.class,
                Archer.class,
                Healer.class,
                Mage.class
        );
        instance = mapper.readValue(new File(filename), Game.class);
        return instance;
    }

    private static ObjectMapper createConfiguredMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.setVisibility(mapper.getSerializationConfig()
                .getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withCreatorVisibility(JsonAutoDetect.Visibility.NONE));
        return mapper;
    }

    public static void main(String[] args) {
        System.out.println("Введите начальный баланс для обеих команд:");
        int initialBalance = scanner.nextInt();

        Game game = Game.getInstance(initialBalance);
        game.startGame();
    }

    public void startGame() {
        mainMenu();

        // Если игра была загружена, пропускаем этап покупки
        if (team1.getUnits().isEmpty() && team2.getUnits().isEmpty()) {
            startTeamBuying();
        }

        while (!team1.getUnits().isEmpty() && !team2.getUnits().isEmpty()) {
            if (makeTurn()) break;
        }
    }

    public void mainMenu() {
        while (true) {
            System.out.println("\n=== Главное меню ===");
            System.out.println("1. Новая игра");
            System.out.println("2. Загрузить игру");
            System.out.println("0. Выход");
            System.out.print("Выберите пункт: ");

            int selector = scanner.nextInt();

            switch (selector) {
                case 1:
                    // Сброс предыдущей игры
                    instance = new Game(team1.getInitialBalance());
                    return;
                case 2:
                    System.out.print("Введите имя файла для загрузки: ");
                    String fileName = scanner.next();
                    try {
                        Game loadedGame = Game.loadFromFile(fileName);
                        instance = loadedGame;
                        System.out.println("Игра успешно загружена!");
                        return;
                    } catch (IOException e) {
                        System.out.println("Ошибка загрузки: " + e.getMessage());
                    }
                    break;

                case 0:
                    System.exit(0);
                    break;

                default:
                    System.out.println("Неверный выбор, попробуйте снова");
            }
        }
    }

    private boolean makeTurn() {
        boolean turnCompleted = false;

        while (!turnCompleted) {
            printBattlefield();
            performRandomTurnOrder();

            System.out.println("\nКоманды управления:");
            System.out.println("1 - Сделать ход");
            System.out.println("save или 4 - Сохранить игру");
            System.out.println("0 - Выйти из игры");
            System.out.print("Введите команду: ");

            String input = scanner.nextLine().trim().toLowerCase();

            switch (input) {
                case "1":
                    // Выполнение хода
                    if (isTeam1Turn) {
                        performTeamAttack(team1, team2);
                    } else {
                        performTeamAttack(team2, team1);
                    }
                    turnCompleted = true;
                    break;

                case "save":
                case "4":
                    System.out.print("Введите имя файла для сохранения: ");
                    String filename = scanner.nextLine().trim();
                    try {
                        saveToFile(filename);
                        System.out.println("Игра успешно сохранена в файл: " + filename);
                    } catch (IOException e) {
                        System.out.println("Ошибка сохранения: " + e.getMessage());
                    }
                    break;

                case "0":
                    System.out.println("Выход из игры...");
                    return true;

                default:
                    System.out.println("Неизвестная команда, попробуйте снова.");
            }

            if (winCheck()) return true;
        }

        try {
            sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("Прерывание сна");
        }
        return false;
    }

    private boolean winCheck() {
        // Проверка условий победы после каждого действия
        if (team1.getUnits().isEmpty()) {
            System.out.println("\nПобеда команды 2!");
            return true;
        }
        if (team2.getUnits().isEmpty()) {
            System.out.println("\nПобеда команды 1!");
            return true;
        }
        return false;
    }

    private void startTeamBuying() {
        System.out.println("Команда 1, начните закупку юнитов:");
        team1.startBuyingProcess();
        System.out.println("Команда 2, начните закупку юнитов:");
        team2.startBuyingProcess();
    }

    private void performTeamAttack(Team attackingTeam, Team defendingTeam) {
        LinkedList<Unit> attackingUnits = attackingTeam.getUnits();
        LinkedList<Unit> defendingUnits = defendingTeam.getUnits();

        int i = 0;
        int j = 0;

        while (i < attackingUnits.size() || j < defendingUnits.size()) {
            if (i < attackingUnits.size()) {
                Unit attacker = attackingUnits.get(i);
                performUnitAction(attacker, new int[]{i}, attackingTeam, defendingTeam);
                i++;
            }

            if (j < defendingUnits.size()) {
                Unit defender = defendingUnits.get(j);
                performUnitAction(defender, new int[]{j}, defendingTeam, attackingTeam);
                j++;
            }

            attackingTeam.removeDeadUnits();
            defendingTeam.removeDeadUnits();

            updateBuffs(attackingTeam);
            updateBuffs(defendingTeam);

            i = Math.min(i, attackingUnits.size());
            j = Math.min(j, defendingUnits.size());
        }
    }

    private void updateBuffs(Team team) {
        LinkedList<Unit> units = team.getUnits();
        ListIterator<Unit> iterator = units.listIterator();

        while (iterator.hasNext()) {
            Unit unit = iterator.next();
            if (unit instanceof BuffDecorator) {
                BuffDecorator buffedUnit = (BuffDecorator) unit;
                if (buffedUnit.getBuffHealth() <= 0) {
                    iterator.set(buffedUnit.removeBuff());
                }
            }
        }
    }

    private void performUnitAction(Unit unit, int[] position, Team ownTeam, Team enemyTeam) {
        if (!unit.isAlive()) return;

        try {
            if (position[0] == 0) {
                Unit target = enemyTeam.getUnits().isEmpty() ? null : enemyTeam.getUnits().getFirst();
                if (target != null && target.isAlive()) {
                    performMeleeAttack(unit, target);
                }
            } else {
                if (unit instanceof SpecialAbility specialAbilityUnit) {
                    logger.log(String.format("%s использует специальную способность",
                            unit.getDisplayName()));
                    specialAbilityUnit.applyAbility(position, ownTeam, enemyTeam);
                }
            }
        } catch (Exception e) {
            logger.log("Ошибка выполнения действия: " + e.getMessage());
        }
    }

    private void performMeleeAttack(Unit attacker, Unit target) {
        int damage = attacker.getMeleeAttack();
        logger.log(String.format("%s атакует %s (Урон: %d)",
                attacker.getDisplayName(),
                target.getDisplayName(),
                damage));

        target.takeDamage(damage);

        logger.log(String.format("%s получает %d урона (Осталось HP: %d/%d)",
                target.getDisplayName(),
                damage,
                target.getHealth(),
                target.getMaxHealth()));
    }

    private void performRandomTurnOrder() {
        isTeam1Turn = new Random().nextBoolean();
        System.out.println("\nЖеребьевка: команда " + (isTeam1Turn ? "1" : "2") + " ходит первой в следующем раунде.");
    }

    private void printBattlefield() {
        System.out.println("\n-------------------------------------- Поле боя ---------------------------------------");
        List<String> team1Status = team1.getUnitStatus();
        List<String> team2Status = team2.getUnitStatus();

        int maxHeight = Math.max(team1Status.size(), team2Status.size());
        for (int i = 0; i < maxHeight; i++) {
            String left = (i < team1Status.size()) ? (team1Status.get(i)) : " ";
            String right = (i < team2Status.size()) ? team2Status.get(i) : " ";
            System.out.printf("| %-40s | %-40s |\n", left, right);
        }
        System.out.println("---------------------------------------------------------------------------------------");
    }
}