package machine;

public enum CoffeeMakerResponse {
    ENOUGH_RESOURCES("I have enough resources, making you a coffee!"),
    NOT_ENOUGH_MILK("Sorry, not enough milk!"),
    NOT_ENOUGH_WATER("Sorry, not enough water!"),
    NOT_ENOUGH_COFFEE_BEANS("Sorry, not enough coffee beans!"),
    NOT_ENOUGH_DISPOSABLE_CUPS("Sorry, not enough disposable cups!");

    private final String message;

    CoffeeMakerResponse(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }

}
