package extension.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by andrade on 16/04/15.
 */

import static java.lang.annotation.ElementType.METHOD;

@Retention(value = RetentionPolicy.RUNTIME)
@Target({ METHOD })
public @interface PropertyResolver {
}
