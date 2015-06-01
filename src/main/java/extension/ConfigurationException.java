package extension;

import extension.model.ConfigBean;

/**
 * Created by andrade on 15/04/15.
 */
public class ConfigurationException extends RuntimeException {
    public ConfigurationException(final Exception e) {
        super(e);
    }

    public ConfigurationException(final String message) {
        super(message);
    }
}
