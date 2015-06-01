package config;

import org.apache.deltaspike.core.api.config.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * Created by andrade on 09/04/15.
 */
@ApplicationScoped
public class SettingsBean {

    @Inject
    @ConfigProperty(name= "property1")
    private Integer intProperty;

    @Inject
    @Location
    private LocationId locationId;

    private Long property2;

    private Long inverseProperty;

    protected SettingsBean(){
    }

    @Inject
    public SettingsBean(@Property2 Long property2){
        this.property2 = property2;
    }

    @Inject
    protected void init(@Property2WithInverseSupport(inverseConvert = true) Long inverseProperty){
        this.inverseProperty = inverseProperty;
    }

    public Integer getIntProperty() {
        return intProperty;
    }

    public LocationId getLocationId() {
        return locationId;
    }

    public Long getProperty2() {
        return property2;
    }

    public Long getInverseProperty() {
        return inverseProperty;
    }
}
