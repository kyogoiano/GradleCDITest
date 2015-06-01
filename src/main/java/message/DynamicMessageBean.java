package message;

import org.apache.deltaspike.core.api.message.Message;
import org.apache.deltaspike.core.api.message.MessageContext;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.logging.Logger;

/**
 * Created by andrade on 09/04/15.
 */
@ApplicationScoped
public class DynamicMessageBean{


    private static final Logger LOG = Logger.getLogger(DynamicMessageBean.class.getName());

    @Inject
    private MessageContext messageContext;

    @Inject
    private CustomizedMessage customizedMessage;

    public void action(){
        Message message = this.messageContext.messageSource("message.Messages").message();
        LOG.info(message.template("{hello}").argument("World").argument("Deltaspike").toString());
        LOG.info("configured dynamic message: "  + message.toString());
        LOG.info("customized message: " + this.customizedMessage.hello());
    }

    public MessageContext getMessageContext() {
        return messageContext;
    }


}
