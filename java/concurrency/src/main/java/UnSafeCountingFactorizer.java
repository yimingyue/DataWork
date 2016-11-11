import org.apache.http.annotation.NotThreadSafe;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by ymyue on 8/8/16.
 */
@NotThreadSafe
public class UnSafeCountingFactorizer implements Servlet {
    private long count = 0;

    public long getCount() {
        return count;
    }

    public void init(ServletConfig config) throws ServletException {

    }

    public ServletConfig getServletConfig() {
        return null;
    }

    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        ++count;
    }

    public String getServletInfo() {
        return null;
    }

    public void destroy() {

    }
}
