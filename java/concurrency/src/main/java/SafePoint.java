import org.apache.http.annotation.GuardedBy;
import org.apache.http.annotation.ThreadSafe;

/**
 * Created by ymyue on 8/7/16.
 */
@ThreadSafe
public class SafePoint {
    @GuardedBy("this") private int x, y;
    private SafePoint(int[] a) {this(a[0], a[1]);}
    private SafePoint(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public SafePoint(SafePoint p) {this(p.get());}
    public synchronized int[] get() {
        return new int[] {x, y};
    }

    public synchronized void set(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
