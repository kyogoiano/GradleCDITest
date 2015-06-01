package config;

import org.apache.deltaspike.core.api.config.ConfigProperty;

import javax.enterprise.util.Nonbinding;
import javax.inject.Qualifier;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;

/**
 * Created by andrade on 09/04/15.
 */

@Target({PARAMETER, FIELD, METHOD, CONSTRUCTOR, ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented

@ConfigProperty(name = "property2")

@Qualifier
public @interface Property2WithInverseSupport {

    @Nonbinding
    boolean inverseConvert() default false;
}
