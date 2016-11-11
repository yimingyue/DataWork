import javax.servlet.*;
import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by ymyue on 8/8/16.
 */
public class UnsafeCachingFactorizer implements Servlet {
    private final AtomicReference<BigInteger> lastNumer = new AtomicReference<BigInteger>();
    private final AtomicReference<BigInteger[]> lastFactors = new AtomicReference<BigInteger[]>();
    public void init(ServletConfig config) throws ServletException {

    }

    public ServletConfig getServletConfig() {
        return null;
    }

    public void service(ServletRequest req, ServletResponse resp) throws ServletException, IOException {
        BigInteger i = extractFromRequest(req);
        if (i.equals(lastNumer.get()))
            encodeIntoResponse(resp, lastFactors.get());
        else {
            BigInteger[] factors = factor(i);
            lastNumer.set(i);
            lastFactors.set(factors);
            encodeIntoResponse(resp, factors);
        }
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
