package echo;

import javax.enterprise.context.ApplicationScoped;

/**
 * Created by andrade on 08/04/15.
 */
@ApplicationScoped
public class ToLowerCaseEchoService implements EchoService{
    /**
     * {@inheritDoc}
     */
    @Override
    public String echo(String message) {
        return message.toLowerCase();
    }
}
