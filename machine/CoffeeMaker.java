package machine;

import machine.products.CoffeeRecipe;

import java.util.stream.IntStream;

class CoffeeMaker {
    private int milkML;
    private int waterML;
    private int coffeeBeansG;
    private int disposableCups;
    public CoffeeMaker(int milkML, int waterML, int coffeeBeansG, int disposableCups) {
        this.milkML = milkML;
        this.waterML = waterML;
        this.coffeeBeansG = coffeeBeansG;
        this.disposableCups = disposableCups;
    }

    CoffeeMakerResponse tryMakeCoffee(CoffeeRecipe coffeeRecipe) {
        CoffeeMakerResponse response;
        if (getNumOfCupOfCoffees(coffeeRecipe) > 0) {
            fillMilk(-coffeeRecipe.milkML());
            fillWater(-coffeeRecipe.waterML());
            addCoffeBeans(-coffeeRecipe.coffeeBeansG());
            addCups(-1);
            response = CoffeeMakerResponse.ENOUGH_RESOURCES;
        } else if (disposableCups == 0){
            response = CoffeeMakerResponse.NOT_ENOUGH_DISPOSABLE_CUPS;
        } else if (getNumOfCupsOfCoffeeWithCurrentMilk(coffeeRecipe) == 0 ) {
            response = CoffeeMakerResponse.NOT_ENOUGH_MILK;
        } else if (getNumOfCupsOfCoffeeWithCurrentWater(coffeeRecipe) == 0) {
            response = CoffeeMakerResponse.NOT_ENOUGH_WATER;
        } else {
            response = CoffeeMakerResponse.NOT_ENOUGH_COFFEE_BEANS;
        }
        return response;
    }

    private int getNumOfCupOfCoffees(CoffeeRecipe coffeeRecipe) {
        return getNumOfCupsOfCoffePerIngredient(coffeeRecipe).min().orElse(0);
    }

    private IntStream getNumOfCupsOfCoffePerIngredient(CoffeeRecipe coffeeRecipe) {
        return IntStream.of(getNumOfCupsOfCoffeeWithCurrentMilk(coffeeRecipe),
                getNumOfCupsOfCoffeeWithCurrentWater(coffeeRecipe),
                getNumOfCupsOfCoffeeWithCurrentCoffeeBeans(coffeeRecipe),
                disposableCups);
    }

    int getNumOfCupsOfCoffeeWithCurrentCoffeeBeans(CoffeeRecipe coffeeRecipe) {
        return coffeeBeansG / coffeeRecipe.coffeeBeansG();
    }

    int getNumOfCupsOfCoffeeWithCurrentWater(CoffeeRecipe coffeeRecipe) {
        return waterML / coffeeRecipe.waterML();
    }

    int getNumOfCupsOfCoffeeWithCurrentMilk(CoffeeRecipe coffeeRecipe) {
        var coffeeRecipeMilkML = coffeeRecipe.milkML();
        return (coffeeRecipeMilkML > 0) ? milkML / coffeeRecipeMilkML : disposableCups;
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
        this.disposableCups += cups;
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

    public int getDisposableCups() {
        return disposableCups;
    }
}
