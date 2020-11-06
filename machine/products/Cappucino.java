package machine.products;

public class Cappucino implements CoffeeRecipe {
    @Override
    public int waterML() {
        return 200;
    }

    @Override
    public int milkML() {
        return 100;
    }

    @Override
    public int coffeeBeansG() {
        return 12;
    }

    @Override
    public int price() {
        return 6;
    }
}
