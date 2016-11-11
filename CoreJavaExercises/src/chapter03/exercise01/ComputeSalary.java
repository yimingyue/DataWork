package chapter03.exercise01;

/**
 * Created by ymyue on 4/22/15.
 */
public class ComputeSalary {
    double averageMeasure(Measurable[] objects) {
        if (null == objects || objects.length == 0)
            return 0.0;
        double sum = 0.0;
        for (Measurable m : objects) {
            sum += m.getMeasure();
        }
        return sum / objects.length;
    }

    double baseSalary = 1000;

    double averageSalary(Employee[] employees) {
        return baseSalary * averageMeasure(employees);
    }

    private Measurable largest(Measurable[] objects) {
        if (objects == null || objects.length == 0)
            return null;
        Measurable max = objects[0];
        for (Measurable m : objects) {
            if (m.getMeasure() > max.getMeasure())
                max = m;
        }
        return max;
    }

    private Employee getNameOfEmployWithLargestSalary(Employee[] employees) {
        return (Employee)largest(employees);
    }
}
