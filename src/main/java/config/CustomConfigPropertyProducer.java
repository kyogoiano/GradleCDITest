package config;

import org.apache.deltaspike.core.api.config.DeltaSpikeConfig;
import org.apache.deltaspike.core.spi.config.BaseConfigPropertyProducer;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import java.util.logging.Logger;

/**
 * Created by andrade on 09/04/15.
 */
@ApplicationScoped
public class CustomConfigPropertyProducer extends BaseConfigPropertyProducer {

    private static final Logger LOG = Logger.getLogger(CustomConfigPropertyProducer.class.getName());

    @Produces
    @Dependent
    @Property2
    public Long produceProperty2(InjectionPoint injectionPoint){

        String configuredValue = getStringPropertyValue(injectionPoint);
        if(configuredValue == null){
            return null;
        }

        Property2 metaData = getAnnotation(injectionPoint, Property2.class);

        if(metaData.logValue()){
            LOG.info("value of property 2: " + configuredValue);
        }

        //TODO  integrate with HandleHandler of DeltaSpike

        return Long.parseLong(configuredValue);
    }

    @Produces
    @Dependent
    @Property2WithInverseSupport
    public Long produceInverseProperty2(InjectionPoint injectionPoint){
        String configuredValue = getStringPropertyValue(injectionPoint);
        if(configuredValue == null){
            return null;
        }


        //TODO integrate with the HandledHandler of DeltaSpike
        Long result = Long.parseLong(configuredValue);

        Property2WithInverseSupport metaData = getAnnotation(injectionPoint, Property2WithInverseSupport.class);

        if(metaData.inverseConvert()){
            return result * -1;
        }

        return result;
    }

    @Produces
    @Dependent
    @Location
    public LocationId produceLocationId(InjectionPoint injectionPoint){
        String configuredValue = getStringPropertyValue(injectionPoint);

        /*
        //alternative to @ConfigProperty#defaultValue
        if (configuredValue == null)
        {
        return LocationId.LOCATION_X;
        }
        */
        return LocationId.valueOf(configuredValue.trim().toUpperCase());
    }


}
