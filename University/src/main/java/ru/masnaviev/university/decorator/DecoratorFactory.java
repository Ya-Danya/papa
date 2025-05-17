package ru.masnaviev.university.decorator;

import org.reflections.Reflections;
import ru.masnaviev.university.decorator.interfaces.GiftComponent;
import ru.masnaviev.university.decorator.interfaces.GiftDecorator;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DecoratorFactory {
    private final Map<String, Class<? extends GiftDecorator>> decorators = new HashMap<>();

    public DecoratorFactory(String packageName) {
        Reflections reflections = new Reflections(packageName);
        Set<Class<? extends GiftDecorator>> subTypes = reflections.getSubTypesOf(GiftDecorator.class);

        for (Class<? extends GiftDecorator> decoratorClass : subTypes) {
            if (Modifier.isAbstract(decoratorClass.getModifiers())) continue;
            String key = decoratorClass.getSimpleName().replace("Decorator", "").toLowerCase();
            decorators.put(key, decoratorClass);
        }
    }

    public GiftComponent createDecorator(String key, GiftComponent gift) {
        if (!decorators.containsKey(key)) {
            System.out.println("Decorator with this name is not found");
            return gift;
        }

        Class<? extends GiftDecorator> decoratorClass = decorators.get(key);

        try {
            return decoratorClass.getConstructor(GiftComponent.class).newInstance(gift);
        } catch (Exception e) {
            throw new RuntimeException("Creation failed for: " + key, e);
        }
    }

    public void printDecoratorsInfo() {
        GiftComponent baseComponent = new SimpleGift();
        System.out.println("\nСписок украшений (" + decorators.size() + "):");
        for (String key : decorators.keySet()) {
            try {
                GiftComponent decorated = createDecorator(key, baseComponent);
                System.out.printf(
                        "Название обертки: %-15s Описание: %-40s Стоимость: %d%n",
                        key,
                        decorated.getDescription(),
                        decorated.getCost() - 10
                );
            } catch (Exception e) {
                System.out.println("⚠ Error with '" + key + "': " + e.getMessage());
            }
        }
    }


    public void printRegisteredDecorators() {
        decorators.forEach((key, clazz) ->
                System.out.printf("Упаковка: %-15s Class: %s%n", key, clazz.getSimpleName())
        );
    }
}
