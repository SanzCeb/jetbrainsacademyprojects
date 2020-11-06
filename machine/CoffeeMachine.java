package machine;

import machine.products.*;
import java.util.Scanner;

public class CoffeeMachine {
    final static Scanner STDIN = new Scanner(System.in);
    final static CoffeeMachine COFFEE_MACHINE = new CoffeeMachine();
    private CoffeeMaker coffeeMaker = new CoffeeMaker(540, 400, 120, 9);
    private int money = 550;

    public int getMoney() {
        return money;
    }
    public int getMilk() {
        return coffeeMaker.getMilkML();
    }

    public int getWaterML() {
        return coffeeMaker.getWaterML();
    }

    public int getCoffeeBeansG() {
        return coffeeMaker.getCoffeeBeansG();
    }

    public int getCups() {
        return coffeeMaker.getCups();
    }

    void buyCoffee(CoffeeRecipe coffeeRecipe) {
        if (coffeeMaker.makeCoffee(coffeeRecipe) ){
            money += coffeeRecipe.price();
        }
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
        printCoffeeMachine();
        System.out.println("Write action (buy, fill, take):");
        runAction(STDIN.nextLine());
        System.out.println();
        printCoffeeMachine();
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
        COFFEE_MACHINE.fillWater(waterML);
        COFFEE_MACHINE.fillMilk(milkML);
        COFFEE_MACHINE.addCoffeeBeans(coffeeBeansG);
        COFFEE_MACHINE.addCups(cups);
    }



    private static void actionBuy() {
        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino:");
        var choice = STDIN.nextInt();
        switch (choice) {
            case 1:
                COFFEE_MACHINE.buyCoffee(new Espresso());
                break;
            case 2:
                COFFEE_MACHINE.buyCoffee(new Latte());
                break;
            case 3:
                COFFEE_MACHINE.buyCoffee(new Cappucino());
                break;
            default:
                break;
        }
    }

    private static void printCoffeeMachine() {
        System.out.println("The coffee machine has:");
        System.out.printf("%d of water%n", COFFEE_MACHINE.getWaterML());
        System.out.printf("%d of milk%n", COFFEE_MACHINE.getMilk());
        System.out.printf("%d of coffee beans%n", COFFEE_MACHINE.getCoffeeBeansG());
        System.out.printf("%d of disposable cups%n", COFFEE_MACHINE.getCups());
        System.out.printf("%d of money%n", COFFEE_MACHINE.getMoney());
    }
}
