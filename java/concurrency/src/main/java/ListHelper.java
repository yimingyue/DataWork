import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by ymyue on 8/7/16.
 */
public class ListHelper<E> {
    public List<E> list = Collections.synchronizedList(new ArrayList<E>());

    public boolean putIfAbsent(E x) {
        synchronized (list) {
            boolean absent = ! list.contains(x);
            if (absent)
                list.add(x);
            return absent;
        }
    }
}
