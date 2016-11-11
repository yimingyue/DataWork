import org.apache.http.annotation.ThreadSafe;

import java.util.Vector;

/**
 * Created by ymyue on 8/7/16.
 */
@ThreadSafe
public class BetterVector<E> extends Vector<E> {
    public synchronized boolean putIfAbsent(E x) {
        boolean absent = !contains(x);
        if (absent)
            add(x);
        return absent;
    }
}
