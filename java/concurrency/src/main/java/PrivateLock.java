import apple.laf.JRSUIConstants;
import org.apache.http.annotation.GuardedBy;


/**
 * Created by ymyue on 8/7/16.
 */
public class PrivateLock {
    private final Object myLock = new Object();
    @GuardedBy("myLock")
    JRSUIConstants.Widget widget;

    void someMethod() {
        synchronized (myLock) {

        }
    }
}
