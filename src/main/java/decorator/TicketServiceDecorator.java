package decorator;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * Created by andrade on 13/04/15.
 */

@Decorator
public class TicketServiceDecorator implements TicketService {

    @Inject
    @Delegate
    private TicketService ticketService;

    @Inject
    private CateringService cateringService;

    @Override
    public Ticket orderTicket(String name) {
        Ticket ticket = ticketService.orderTicket(name);
        cateringService.orderCatering(ticket);

        return ticket;
    }
}
