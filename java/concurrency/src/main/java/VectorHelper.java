import java.util.Vector;

/**
 * Created by ymyue on 8/7/16.
 */
public class VectorHelper {
    public static Object getLast(Vector list) {
        synchronized (list) {
            int lastIndex = list.size()-1;
            return list.get(lastIndex);
        }
    }

    public static void deleteLast(Vector list) {
        synchronized (list) {
            int lastIndex = list.size()-1;
            list.remove(lastIndex);
        }
    }
}
