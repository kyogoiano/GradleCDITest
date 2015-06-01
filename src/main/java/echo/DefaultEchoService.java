package echo;

import interceptor.Audited;

import javax.enterprise.context.Dependent;
import javax.inject.Named;

/**
 * Created by andrade on 08/04/15.
 */

@Dependent
@Named("DefaultEchoService")
public class DefaultEchoService implements  EchoService {

    /**
     * {@inheritDoc}
     */
    @Override
    @Audited
    public String echo(String message) {
        return message;
    }
}
