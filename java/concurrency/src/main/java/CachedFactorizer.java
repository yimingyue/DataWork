import org.apache.http.annotation.GuardedBy;

import javax.servlet.*;
import java.io.IOException;
import java.math.BigInteger;

/**
 * Created by ymyue on 8/8/16.
 */
public class CachedFactorizer implements Servlet {
    @GuardedBy("this") private BigInteger lastNumber;
    @GuardedBy("this") private BigInteger[] lastFactors;
    @GuardedBy("this") private long hits;
    @GuardedBy("this") private long cacheHits;

    public synchronized long getHits() {return hits;}

    public synchronized double getCacheHitRatio() {
        return (double) cacheHits / (double) hits;
    }
    public void init(ServletConfig config) throws ServletException {

    }

    public ServletConfig getServletConfig() {
        return null;
    }

    public void service(ServletRequest req, ServletResponse resp) throws ServletException, IOException {
        BigInteger i = extractFromRequest(req);
        BigInteger[] factors = null;
        synchronized (this) {
            ++hits;
            if (i.equals(lastNumber)) {
                ++cacheHits;
                factors = lastFactors.clone();
            }
        }
        if (factors == null) {
            factors = factor(i);
            synchronized (this) {
                lastNumber = i;
                lastFactors = factors.clone();
            }
        }
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
