package jp.seraphyware.cdiexample;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ロギングのインターセプタ.<br>
 * META-INF/beans.xmlに記述する.
 * @author seraphy
 */
@Logged
@Interceptor
public class LoggedInterceptor {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @AroundInvoke
    public Object logMethodEntry(InvocationContext invocationContext)
            throws Exception {
        logger.info("⇒ Entering method: "
                + invocationContext.getMethod().getName() + " in class "
                + invocationContext.getMethod().getDeclaringClass().getName());

        return invocationContext.proceed();
    }
}
