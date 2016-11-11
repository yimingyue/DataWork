import org.apache.http.annotation.Immutable;

/**
 * Created by ymyue on 8/7/16.
 */
@Immutable
public class Point {
    public final int x, y;
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
