package machine.products;

public class Espresso implements CoffeeRecipe {

    @Override
    public int waterML() {
        return 250;
    }

    @Override
    public int milkML() {
        return 0;
    }

    @Override
    public int coffeeBeansG() {
        return 16;
    }

    @Override
    public int price() {
        return 4;
    }
}
