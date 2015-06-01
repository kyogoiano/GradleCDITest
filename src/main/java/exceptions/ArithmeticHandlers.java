package exceptions;

import org.apache.deltaspike.core.api.exception.control.BeforeHandles;
import org.apache.deltaspike.core.api.exception.control.ExceptionHandler;
import org.apache.deltaspike.core.api.exception.control.Handles;
import org.apache.deltaspike.core.api.exception.control.event.ExceptionEvent;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by andrade on 10/04/15.
 */
@ExceptionHandler
public class ArithmeticHandlers {

    private static final Logger LOG = Logger.getLogger(ArithmeticHandlers.class.getName());

    void printExceptions(@Handles ExceptionEvent<ArithmeticException> event){
        LOG.log(Level.SEVERE, event.getException().getLocalizedMessage());
        LOG.throwing(event.getClass().getName(),
                event.getClass().getEnclosingMethod().getName(), event.getException());
        event.handledAndContinue();
    }

    void logExceptions(@BeforeHandles ExceptionEvent<ArithmeticException> event){
        LOG.warning("Warning: " + event.getException().getMessage());
    }
}
