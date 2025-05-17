package ru.masnaviev.university.decorator;

import ru.masnaviev.university.decorator.interfaces.GiftComponent;
import ru.masnaviev.university.decorator.interfaces.GiftDecorator;

import java.util.Scanner;

public class WrapperMan {
    private static final DecoratorFactory factory = new DecoratorFactory("ru.masnaviev.university.decorator.decorators");
    private static final Scanner scanner = new Scanner(System.in);

    public void helpCustomer(Customer customer) {
        getRequest(customer);

        System.out.println(customer.getName() + " получил упакованный подарок");
        System.out.println(customer.gift.getDescription());
        System.out.println("Суммарная стоимость = " + customer.gift.getCost());
    }

    private void getRequest(Customer customer) {
        factory.printDecoratorsInfo();
        System.out.println("Введите 0 - завершить");
        String request = scanner.nextLine();

        if (request.equals("0")) {
            return;
        }

        customer.gift = factory.createDecorator(request, customer.gift);
        getRequest(customer);
    }
}
