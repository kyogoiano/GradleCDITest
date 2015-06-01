package extension.annotations.test;

import extension.annotations.PropertyLocale;
import extension.annotations.PropertyResolver;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.BeanManager;
import java.util.Locale;

/**
 * Created by andrade on 16/04/15.
 */
@ApplicationScoped
public class Properties {
    @PropertyResolver
    public String resolveProperty(@PropertyLocale Locale locale, String key,
                                  BeanManager beanManager, OtherBean other) {

        // We could use the Locale, or any other bean that is injected into the
        // method in order to help us finding the correct property value.

        // We may go for a regular property file, a database, or any other
        // existent property container.

        // In this example we have hard coded the returned values.

        if (key.equals("property.one")) {
            return "ONE";
        } else if (key.equals("property.two")) {
            return "TWO " + other.getOtherBeanText();
        }

        return "NONE " + other.getOtherBeanText();
    }
}
