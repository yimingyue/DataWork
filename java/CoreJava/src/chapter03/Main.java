package chapter03;

/**
 * Created by ymyue on 5/31/15.
 */
public class Main {
    double average(Measurable[] objects) {
        if (objects == null || objects.length == 0)
            return 0;
        double sum = 0.0;
        for (Measurable obj :objects) {
            sum += obj.getMeasure();
        }
        return sum / objects.length;
    }

    Measurable largest(Measurable[] objects) {
        double max = Double.MIN_VALUE;
        Measurable maxObj = null;
        for (Measurable obj : objects) {
            if (obj instanceof Employee) {
                Employee e = (Employee)obj;
                if (e.getSalary() > max) {
                    max = e.getSalary();
                    maxObj = obj;
                }
            }
        }
        return maxObj;
    }

    public static void main(String[] argv) {
        IntSequence s = IntSequence.of(3, 4, 5, 6);
        while (s.hasNext())
            System.out.print(s.next());
    }
}
