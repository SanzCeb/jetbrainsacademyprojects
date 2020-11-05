package machine;

import java.util.stream.IntStream;

public class CoffeeMaker {
    private int milkML;
    private int waterML;
    private int coffeeBeansG;

    public CoffeeMaker(int milkML, int waterML, int coffeeBeansG) {
        this.milkML = milkML;
        this.waterML = waterML;
        this.coffeeBeansG = coffeeBeansG;
    }

    int getRemainingCupsOfCoffee() {
        return getNumOfCupsOfCoffePerIngredient().min().orElse(0);
    }

    private IntStream getNumOfCupsOfCoffePerIngredient() {
        return IntStream.of(getNumOfCupsOfCoffeeWithCurrentMilk(),
                getNumOfCupsOfCoffeeWithCurrentWater(),
                getNumOfCupsOfCoffeeWithCurrentCoffeeBeans());
    }

    private int getNumOfCupsOfCoffeeWithCurrentCoffeeBeans() {
        return coffeeBeansG / CoffeeRecipe.COFFEE_BEANS_G;
    }

    private int getNumOfCupsOfCoffeeWithCurrentWater() {
        return waterML / CoffeeRecipe.WATER_ML;
    }

    private int getNumOfCupsOfCoffeeWithCurrentMilk() {
        return milkML / CoffeeRecipe.MILK_ML;
    }


}
