package config;

import org.apache.deltaspike.core.api.config.PropertyFileConfig;

import javax.enterprise.context.ApplicationScoped;

/**
 * Created by andrade on 09/04/15.
 *
 * Allows to use a different file than apache-deltaspike.properties
 */

@ApplicationScoped
public class CustomPropertyFileConfig implements PropertyFileConfig {


    @Override
    public String getPropertyFileName() {
        return "META-INF/log.properties";
    }

    @Override
    public boolean isOptional() {
        return false;
    }
}
