import javax.servlet.*;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by ymyue on 8/8/16.
 */
public class CountingFactorizer implements Servlet {
    private final AtomicLong count = new AtomicLong(0);

    public void init(ServletConfig config) throws ServletException {

    }

    public ServletConfig getServletConfig() {
        return null;
    }

    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        count.incrementAndGet();
    }

    public String getServletInfo() {
        return null;
    }

    public void destroy() {

    }
}
