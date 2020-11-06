package machine.products;

public class Latte implements CoffeeRecipe {
    @Override
    public int waterML() {
        return 350;
    }

    @Override
    public int milkML() {
        return 75;
    }

    @Override
    public int coffeeBeansG() {
        return 20;
    }

    @Override
    public int price() {
        return 7;
    }
}
