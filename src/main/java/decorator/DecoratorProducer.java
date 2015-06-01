package decorator;

import org.apache.deltaspike.core.spi.config.BaseConfigPropertyProducer;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import java.util.logging.Logger;

/**
 * Created by andrade on 14/04/15.
 */
@ApplicationScoped
public class DecoratorProducer extends BaseConfigPropertyProducer {

    private static final Logger LOG = Logger.getLogger(DecoratorProducer.class.getName());

    @Produces
    @Dependent
    public Ticket produceOrderTicket(InjectionPoint injectionPoint){
        String configuredValue = getStringPropertyValue(injectionPoint);
        if(configuredValue == null){
            return null;
        }

        return new Ticket(configuredValue);

    }
}
