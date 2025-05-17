package ru.masnaviev.university.adapter;

interface OldPaymentGateway {
    void processPayment(double amount, String currency);
}
// Старая платежная система
class LegacyPaymentSystem implements OldPaymentGateway {
    @Override
    public void processPayment(double amount, String currency) {
        System.out.println("Обработка платежа на сумму " + amount + " " + currency + " через старую платежную систему");
    }
}

// Новая платежная система
class NewPaymentProvider {
    public void makePayment(String currency, double amount) {
        System.out.println("Обработка платежа на сумму " + amount + " " + currency + " через новый платежный провайдер");
    }
}

// Новая другая платежная система
class AlternativePaymentProvider {
    public void sendPayment(double amount, String currency) {
        System.out.println("Отправка платежа на сумму " + amount + " " + currency + " через другой новый провайдер");
    }
}

class NewPaymentProviderAdapter implements OldPaymentGateway {
    private final NewPaymentProvider paymentProvider;

    public NewPaymentProviderAdapter(NewPaymentProvider paymentProvider) {
        this.paymentProvider = paymentProvider;
    }

    @Override
    public void processPayment(double amount, String currency) {
        paymentProvider.makePayment(currency, amount);
    }
}

class AlternativePaymentProviderAdapter implements OldPaymentGateway {
    private final AlternativePaymentProvider paymentProvider;

    public AlternativePaymentProviderAdapter(AlternativePaymentProvider paymentProvider) {
        this.paymentProvider = paymentProvider;
    }

    @Override
    public void processPayment(double amount, String currency) {
        paymentProvider.sendPayment(amount, currency);
    }
}

class Client {
    private final OldPaymentGateway paymentGateway;

    public Client(OldPaymentGateway paymentGateway) {
        this.paymentGateway = paymentGateway;
    }

    public void makePayment(double amount, String currency) {
        paymentGateway.processPayment(amount, currency);
    }
}

public class AdapterDemo {
    public static void main(String[] args) {
        AdapterDemo demo = new AdapterDemo();
        demo.useClientWithLegacyPaymentSystem();
        demo.useClientWithNewPaymentProvider();
        demo.useClientWithAlternativePaymentProvider();
    }

    private void useClientWithLegacyPaymentSystem() {
        OldPaymentGateway oldGateway = new LegacyPaymentSystem();
        Client client = new Client(oldGateway);
        client.makePayment(100.0, "USD");
    }

    private void useClientWithNewPaymentProvider() {
        OldPaymentGateway adaptedGateway = new NewPaymentProviderAdapter(new NewPaymentProvider());
        Client client = new Client(adaptedGateway);
        client.makePayment(200.0, "EUR");
    }

    private void useClientWithAlternativePaymentProvider() {
        OldPaymentGateway adaptedGateway = new AlternativePaymentProviderAdapter(new AlternativePaymentProvider());
        Client client = new Client(adaptedGateway);
        client.makePayment(300.0, "GBP");
    }
}
