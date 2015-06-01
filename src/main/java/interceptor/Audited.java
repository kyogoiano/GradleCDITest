package interceptor;

import javax.interceptor.InterceptorBinding;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

/**
 * Created by andrade on 14/04/15.
 */
@InterceptorBinding
@Target({TYPE, METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Audited {
}
