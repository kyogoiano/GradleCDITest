package exceptions;

import org.apache.deltaspike.core.api.exception.control.event.ExceptionToCatchEvent;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;

/**
 * Created by andrade on 10/04/15.
 */
@ApplicationScoped
public class InventoryActions {

    @Inject
    private Event<ExceptionToCatchEvent> catchEvent;

    public Long aritmeticCalculation(Long input){
        try {
            return 10/input;
        } catch (ArithmeticException ae){
            catchEvent.fire(new ExceptionToCatchEvent(ae));
        }
        return input;
    }
}
