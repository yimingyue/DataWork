package chapter03;

/**
 * Created by ymyue on 5/31/15.
 */
public class Employee implements Measurable {
    private double performance;
    private  String name;
    private double salary;

    @Override
    public double getMeasure() {
        return performance;
    }

    public String getName() {
        return name;
    }

    public double getSalary() {
        return salary;
    }
}
