package interceptor;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.util.Arrays;
import java.util.logging.Logger;

/**
 * Created by andrade on 14/04/15.
 */
@Interceptor
@Audited
public class AuditInterceptor {

    private static final Logger LOG = Logger.getLogger(AuditInterceptor.class.getName());

    @AroundInvoke
    public Object audit(InvocationContext context) throws Exception {
        LOG.info("Executing: " + context.getMethod().getName() +
        " with args: " + Arrays.toString(context.getParameters()));
        return context.proceed();
    }
}
