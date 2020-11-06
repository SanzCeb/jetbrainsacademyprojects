package machine;

import machine.products.CoffeeRecipe;

import java.util.stream.IntStream;

class CoffeeMaker {
    private int milkML;
    private int waterML;
    private int coffeeBeansG;
    private int cups;
    public CoffeeMaker(int milkML, int waterML, int coffeeBeansG, int cups) {
        this.milkML = milkML;
        this.waterML = waterML;
        this.coffeeBeansG = coffeeBeansG;
        this.cups = cups;
    }

    boolean makeCoffee(CoffeeRecipe coffeeRecipe) {
        var enoughStock = getRemainingCupsOfCoffee(coffeeRecipe) > 0;
        if (enoughStock) {
            fillMilk(-coffeeRecipe.milkML());
            fillWater(-coffeeRecipe.waterML());
            addCoffeBeans(-coffeeRecipe.coffeeBeansG());
            addCups(-1);
        }
        return enoughStock;
    }

    private int getRemainingCupsOfCoffee(CoffeeRecipe coffeeRecipe) {
        return getNumOfCupsOfCoffePerIngredient(coffeeRecipe).min().orElse(0);
    }

    private IntStream getNumOfCupsOfCoffePerIngredient(CoffeeRecipe coffeeRecipe) {
        return IntStream.of(getNumOfCupsOfCoffeeWithCurrentMilk(coffeeRecipe),
                getNumOfCupsOfCoffeeWithCurrentWater(coffeeRecipe),
                getNumOfCupsOfCoffeeWithCurrentCoffeeBeans(coffeeRecipe),
                cups);
    }

    private int getNumOfCupsOfCoffeeWithCurrentCoffeeBeans(CoffeeRecipe coffeeRecipe) {
        return coffeeBeansG / coffeeRecipe.coffeeBeansG();
    }

    private int getNumOfCupsOfCoffeeWithCurrentWater(CoffeeRecipe coffeeRecipe) {
        return waterML / coffeeRecipe.waterML();
    }

    private int getNumOfCupsOfCoffeeWithCurrentMilk(CoffeeRecipe coffeeRecipe) {
        var coffeeRecipeMilkML = coffeeRecipe.milkML();
        return (coffeeRecipeMilkML > 0) ? milkML / coffeeRecipeMilkML : cups;
    }

    public void fillWater(int waterML) {
        this.waterML += waterML;
    }

    public void fillMilk(int milkML) {
        this.milkML += milkML;
    }

    public void addCoffeBeans(int coffeeBeansG) {
        this.coffeeBeansG += coffeeBeansG;
    }

    public void addCups(int cups) {
        this.cups += cups;
    }

    public int getMilkML() {
        return milkML;
    }

    public int getWaterML() {
        return waterML;
    }

    public int getCoffeeBeansG() {
        return coffeeBeansG;
    }

    public int getCups() {
        return cups;
    }
}
