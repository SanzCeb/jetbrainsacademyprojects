package machine;

enum CoffeeMachineState {
    IDLE("\nWrite action (buy, fill, take, remaining, exit):"),
    SELLING("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:"),
    FILLING_WATER("Write how many ml of water do you want to add:"),
    FILLING_MILK("Write how many ml of milk do you want to add:"),
    ADDING_COFFEE_BEANS("Write how many grams of coffee beans do you want to add:"),
    FILLING_CUPS("Write how many disposable cups of coffee do you want to add:");

    private final String response;

    CoffeeMachineState(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }
}
