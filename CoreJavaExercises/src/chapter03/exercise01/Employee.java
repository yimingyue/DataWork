package chapter03.exercise01;

/**
 * Created by ymyue on 4/22/15.
 */
public class Employee implements Measurable {
    @Override
    public double getMeasure() {
            return performance;
    }

    private double performance;
}
