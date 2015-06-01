package decorator;


import javax.enterprise.context.ApplicationScoped;
import java.util.logging.Logger;

/**
 * Created by andrade on 13/04/15.
 */

@ApplicationScoped
public class TicketServiceImpl implements TicketService {

    private static final Logger LOG = Logger.getLogger(TicketServiceImpl.class.getName());

    @Override
    public Ticket orderTicket(String name) {
        Ticket ticket = new Ticket(name);
        LOG.info("Ticket Service created new instance: " + ticket.getName());
        return ticket;
    }
}
