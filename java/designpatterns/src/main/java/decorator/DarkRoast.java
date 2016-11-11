package decorator;

/**
 * Created by ymyue on 8/7/16.
 */
public class DarkRoast extends Beverage {
    public DarkRoast() {
        description = "Dark Roast";
    }

    public double cost() {
        return .99;
    }
}
