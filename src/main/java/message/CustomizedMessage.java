package message;

import org.apache.deltaspike.core.api.message.MessageBundle;
import org.apache.deltaspike.core.api.message.MessageContextConfig;
import org.apache.deltaspike.core.api.message.MessageTemplate;

/**
 * Created by andrade on 10/04/15.
 */
@MessageBundle
public interface CustomizedMessage {

    @MessageTemplate("{hello}")
    String hello();
}
