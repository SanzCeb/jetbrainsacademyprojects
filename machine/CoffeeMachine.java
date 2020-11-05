package machine;

import java.util.Scanner;

public class CoffeeMachine {
    public static void main(String[] args) {
        var scanner = new Scanner(System.in);

        System.out.println("Write how many cups of coffee you will need");
        var cups = scanner.nextInt();
        System.out.printf("For %d cups of coffee you will need:%n", cups);
        System.out.printf("%d ml of water%n", cups * CoffeeRecipe.WATER_ML);
        System.out.printf("%d ml of milk%n", cups * CoffeeRecipe.MILK_ML);
        System.out.printf("%d g of coffee beans%n", cups * CoffeeRecipe.COFFEE_BEANS_G);

    }
}
