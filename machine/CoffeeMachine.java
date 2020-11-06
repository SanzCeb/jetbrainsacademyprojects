package machine;

import machine.products.*;
import java.util.Scanner;

public class CoffeeMachine {
    final static Scanner STDIN = new Scanner(System.in);
    final static CoffeeMachine COFFEE_MACHINE = new CoffeeMachine();
    private CoffeeMaker coffeeMaker = new CoffeeMaker(540, 400, 120, 9);
    private int money = 550;

    public String askMoney() {
        return String.format("$%d of money", money);
    }
    public String askRemainingMilk() {
        return String.format("%d of milk", coffeeMaker.getMilkML());
    }

    public String askRemainingWater() {
        return String.format("%d of water", coffeeMaker.getWaterML());
    }

    public String askRemainingCoffeeBeans() {
        return String.format("%d of coffee beans", coffeeMaker.getCoffeeBeansG());
    }

    public String askRemainingCups() {
        return String.format("%d of disposable cups", coffeeMaker.getDisposableCups());
    }

    String tryBuyCoffee(CoffeeRecipe coffeeRecipe) {
        var response = coffeeMaker.tryMakeCoffee(coffeeRecipe);
        if (response == CoffeeMakerResponse.ENOUGH_RESOURCES) {
            money += coffeeRecipe.price();
        }
        return  response.toString();
    }

    private int takeMoney() {
        var moneyTaken = money;
        money = 0;
        return moneyTaken;
    }

    private void addCups(int cups) {
        coffeeMaker.addCups(cups);
    }

    private void addCoffeeBeans(int coffeeBeansG) {
        coffeeMaker.addCoffeBeans(coffeeBeansG);
    }

    private void fillMilk(int milkML) {
        coffeeMaker.fillMilk(milkML);
    }

    private void fillWater(int waterML) {
        coffeeMaker.fillWater(waterML);
    }


    public static void main(String[] args) {
        String action;
        do{
            System.out.println("\nWrite action (buy, fill, take, remaining, exit):");
            action = STDIN.nextLine();
            System.out.println();
            runAction(action);
        }while (!action.equals("exit"));
    }

    private static void runAction(String action) {
        switch (action) {
            case "buy":
                actionBuy();
                break;
            case "fill":
                actionFill();
                break;
            case "take":
                actionTake();
                break;
            case "remaining":
                printCoffeeMachineStats();
            default:
                break;
        }
    }

    private static void actionTake() {
        int money = COFFEE_MACHINE.takeMoney();
        System.out.printf("I gave you $%d%n", money);
    }

    private static void actionFill() {
        System.out.println("Write how many ml of water do you want to add:");
        var waterML = STDIN.nextInt();
        System.out.println("Write how many ml of milk do you want to add:");
        var milkML = STDIN.nextInt();
        System.out.println("Write how many grams of coffee beans do you want to add:");
        var coffeeBeansG = STDIN.nextInt();
        System.out.println("Write how many disposable cups do you want to add:");
        var cups = STDIN.nextInt();
        STDIN.nextLine();
        COFFEE_MACHINE.fillWater(waterML);
        COFFEE_MACHINE.fillMilk(milkML);
        COFFEE_MACHINE.addCoffeeBeans(coffeeBeansG);
        COFFEE_MACHINE.addCups(cups);
    }



    private static void actionBuy() {
        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
        var choice = STDIN.nextLine();
        System.out.println();
        switch (choice) {
            case "1":
                System.out.println(COFFEE_MACHINE.tryBuyCoffee(new Espresso()));
                break;
            case "2":
                System.out.println(COFFEE_MACHINE.tryBuyCoffee(new Latte()));
                break;
            case "3":
                System.out.println(COFFEE_MACHINE.tryBuyCoffee(new Cappucino()));
                break;
            case "back":
            default:
                break;
        }
    }

    private static void printCoffeeMachineStats() {
        System.out.println("The coffee machine has:");
        System.out.println(COFFEE_MACHINE.askRemainingWater());
        System.out.println(COFFEE_MACHINE.askRemainingMilk());
        System.out.println(COFFEE_MACHINE.askRemainingCoffeeBeans());
        System.out.println(COFFEE_MACHINE.askRemainingCups());
        System.out.println(COFFEE_MACHINE.askMoney());
    }
}
