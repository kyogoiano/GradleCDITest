package echo;

import org.apache.deltaspike.core.api.exclude.Exclude;

/**
 * Created by andrade on 08/04/15.
 * This implementation can't be used as a CDI bean
 */
@Exclude
public class NoEchoService implements EchoService {

    /**
     * {@inheritDoc}
     */
    @Override
    public String echo(String message) {
        return null;
    }
}
