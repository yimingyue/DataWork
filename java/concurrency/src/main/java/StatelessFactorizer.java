import org.apache.http.annotation.ThreadSafe;

import javax.servlet.*;
import java.math.BigInteger;

/**
 * Created by ymyue on 8/8/16.
 */
@ThreadSafe
public class StatelessFactorizer implements Servlet {
    public void init(ServletConfig config) throws ServletException {

    }

    public ServletConfig getServletConfig() {
        return null;
    }

    public void service(ServletRequest req, ServletResponse resp) {
        BigInteger i = extractFromRequest(req);
        BigInteger[] factors = factor(i);
        encodeIntoResponse(resp, factors);
    }

    public String getServletInfo() {
        return null;
    }

    public void destroy() {

    }

    private BigInteger extractFromRequest(ServletRequest req) {
        return new BigInteger("2311434234234324324235234345");
    }

    private BigInteger[] factor(BigInteger i){
        return new BigInteger[10];
    }

    private void encodeIntoResponse(ServletResponse resp, BigInteger[] factors) {

    }
}
