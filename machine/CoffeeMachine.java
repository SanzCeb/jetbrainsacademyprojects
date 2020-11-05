package machine;

import java.util.Scanner;

public class CoffeeMachine {

    public static void main(String[] args) {
        var scanner = new Scanner(System.in);


        System.out.println("Write how many ml of water the coffee machine has:");
        var waterML = scanner.nextInt();
        System.out.println("Write how many ml of milk beans the coffee machine has:");
        var milkML = scanner.nextInt();
        System.out.println("Write how many grams of coffee beans the coffee machine has:");
        var coffeeBeansG = scanner.nextInt();
        System.out.println("Write how many cups of coffee you will need:");
        var cups = scanner.nextInt();
        var availableCups = new CoffeeMaker(milkML, waterML, coffeeBeansG).getRemainingCupsOfCoffee();


        if (availableCups > cups) {
            System.out.printf("Yes, I can make that amount of coffee (and even %d more than that)%n", availableCups - cups);
        } else if (availableCups == cups) {
            System.out.println("Yes, I can make that amount of coffee");
        } else {
            System.out.printf("No, I can make only %d cup(s) of coffee%n", availableCups);
        }

    }
}
