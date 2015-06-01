package config;

import org.apache.deltaspike.core.api.config.ConfigProperty;

import javax.inject.Qualifier;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Created by andrade on 09/04/15.
 */


import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD, METHOD})
@Retention(RUNTIME)
@Documented

@ConfigProperty(name = "locationId", defaultValue = "LOCATION_X")

@Qualifier
public @interface Location {
}
