package decorator;

import java.util.logging.Logger;

/**
 * Created by andrade on 13/04/15.
 */
public class CateringServiceImpl implements CateringService {
    private static final Logger LOG = Logger.getLogger(CateringService.class.getName());

    @Override
    public void orderCatering(Ticket ticket) {
        LOG.info("Catering ticket: " + ticket.getName());
    }
}
