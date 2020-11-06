package machine;

import machine.products.*;
import java.util.Optional;
import java.util.Scanner;

public class CoffeeMachine {
    private final CoffeeMaker coffeeMaker = new CoffeeMaker(540, 400, 120, 9);
    private int money = 550;
    private CoffeeMachineState state = CoffeeMachineState.IDLE;

    String turnOn() {
        state = CoffeeMachineState.IDLE;
        return String.format("%s%n",state.getResponse());
    }

    String input(String userInput) {
        String message;
        switch (state) {
            case IDLE:
                message = doAction(userInput);
                break;
            case SELLING:
                message = trySellingCoffee(userInput);
                break;
            case FILLING_WATER:
                message = fillWater(userInput);
                break;
            case FILLING_MILK:
                message = fillMilk(userInput);
                break;
            case ADDING_COFFEE_BEANS:
                message = addCoffeeBeans(userInput);
                break;
            default:
                message = addDisposableCups(userInput);
                break;
        }
        return message;
    }

    private String addCoffeeBeans(String userInput) {
        coffeeMaker.addCoffeBeans(Integer.parseInt(userInput));
        state = CoffeeMachineState.FILLING_CUPS;
        return String.format("%s%n",state.getResponse());
    }

    private String addDisposableCups(String action) {
        coffeeMaker.addCups(Integer.parseInt(action));
        state = CoffeeMachineState.IDLE;
        return String.format("%s%n",state.getResponse());
    }

    private String trySellingCoffee(String action) {
        var optionalCoffeeRecipe = readRecipe(action);
        String message;
        if (optionalCoffeeRecipe.isPresent()) {
            message = tryBuyCoffee(optionalCoffeeRecipe.get());
        } else {
            state = CoffeeMachineState.IDLE;
            message = String.format("%n%s%n",state.getResponse());
        }
        return message;
    }

    private Optional<CoffeeRecipe> readRecipe(String input) {
        CoffeeRecipe coffeeRecipe;
        switch (input) {
            case "1":
                coffeeRecipe = new Espresso();
                break;
            case "2":
                coffeeRecipe = new Latte();
                break;
            case "3":
                coffeeRecipe = new Cappucino();
                break;
            default:
                coffeeRecipe = null;
                break;
        }
        return Optional.ofNullable(coffeeRecipe);
    }


    public String askMoney() {
        return String.format("$%d of money%n", money);
    }
    public String queryRemainingMilk() {
        return String.format("%d of milk%n", coffeeMaker.getMilkML());
    }

    public String queryRemainingWater() {
        return String.format("%d of water%n", coffeeMaker.getWaterML());
    }

    public String queryCoffeeBeans() {
        return String.format("%d of coffee beans%n", coffeeMaker.getCoffeeBeansG());
    }

    public String queryRemainingDisposableCups() {
        return String.format("%d of disposable cups%n", coffeeMaker.getDisposableCups());
    }

    String tryBuyCoffee(CoffeeRecipe coffeeRecipe) {
        var response = coffeeMaker.tryMakeCoffee(coffeeRecipe);
        if (response == CoffeeMakerResponse.ENOUGH_RESOURCES) {
            money += coffeeRecipe.price();
        }
        state = CoffeeMachineState.IDLE;
        return  String.format("%s%n%s%n",response, state.getResponse());
    }

    private int takeMoney() {
        var moneyTaken = money;
        money = 0;
        return moneyTaken;
    }

    private String fillMilk(String milkML) {
        coffeeMaker.fillMilk(Integer.parseInt(milkML));
        state = CoffeeMachineState.ADDING_COFFEE_BEANS;
        return String.format("%s%n",state.getResponse());
    }

    private String fillWater(String waterML) {
        coffeeMaker.fillWater(Integer.parseInt(waterML));
        state = CoffeeMachineState.FILLING_MILK;
        return String.format("%s%n",state.getResponse());
    }


    private String doAction(String action) {
        String message;
        switch (action) {
            case "buy":
                message = actionBuy();
                break;
            case "fill":
                message = actionFill();
                break;
            case "take":
                message = actionTake();
                break;
            default:
                message = collectCoffeeMachineStats();
                break;
        }
        return message;
    }

    private String actionBuy() {
        state = CoffeeMachineState.SELLING;
        return String.format("%n%s%n", state.getResponse());
    }


    private String actionTake() {
        return String.format("%nI gave you $%d%n%s%n", takeMoney(), state.getResponse());
    }

    private String actionFill() {
        state = CoffeeMachineState.FILLING_WATER;
        return String.format("%n%s%n", state.getResponse());
    }

    private String collectCoffeeMachineStats() {
        String outputFormat = "%nThe coffee machine has:%n%s%s%s%s%s%s%n";
        var remainingWater = queryRemainingWater();
        var remainingMilk = queryRemainingMilk();
        var remainingCoffeeBeans = queryCoffeeBeans();
        var remainingDisposableCups = queryRemainingDisposableCups();
        var money = askMoney();
        return String.format(outputFormat, remainingWater, remainingMilk,
                remainingCoffeeBeans, remainingDisposableCups, money, state.getResponse());
    }

    public static void main(String[] args) {
        var coffeeMachine = new CoffeeMachine();
        var scanner = new Scanner(System.in);
        String response = coffeeMachine.turnOn();
        String action;
        do{
            System.out.print(response);
            action = scanner.nextLine();
            response = coffeeMachine.input(action);
        }while (!action.equals("exit"));
    }

}
