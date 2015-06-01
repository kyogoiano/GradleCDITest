package config;

import decorator.TicketServiceDecorator;
import decorator.TicketServiceImpl;
import exceptions.InventoryActions;
import extension.annotations.test.TestBean;
import message.DynamicMessageBean;
import org.apache.deltaspike.cdise.api.CdiContainer;
import org.apache.deltaspike.cdise.api.CdiContainerLoader;
import org.apache.deltaspike.cdise.api.ContextControl;
import org.apache.deltaspike.core.api.provider.BeanProvider;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import java.util.logging.Logger;

/**
 * Created by andrade on 09/04/15.
 */
public class ConfigExample {
    private static final Logger LOG = Logger.getLogger(ConfigExample.class.getName());

    private ConfigExample(){

    }

    public static void main(String[] args){
        /* Total number of processors or cores available to the JVM */
        LOG.info("Available processors (cores): " +
                Runtime.getRuntime().availableProcessors());

        /* Total amount of free memory available to the JVM */
        long freeMemory = Runtime.getRuntime().freeMemory();
        LOG.info("Free memory (bytes): " +
                freeMemory);

        /* Total memory currently available to the JVM */
        LOG.info("Total memory available to JVM (bytes): " +
                Runtime.getRuntime().totalMemory());

        CdiContainer cdiContainer = CdiContainerLoader.getCdiContainer();
        cdiContainer.boot();

        long startTime = System.currentTimeMillis();

        ContextControl contextControl = cdiContainer.getContextControl();
        contextControl.startContext(ApplicationScoped.class);


        SettingsBean settingsBean = BeanProvider.getContextualReference(SettingsBean.class, false);

        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        LOG.info("Building and Starting application contex and getting context reference time: " + elapsedTime);


        LOG.info("configured int-value #1: " + settingsBean.getIntProperty());
        LOG.info("configured long-value #2: " + settingsBean.getProperty2());
        LOG.info("configured inverse-value #2: " + settingsBean.getInverseProperty());
        LOG.info("configured location (custom config): " + settingsBean.getLocationId().name());


        DynamicMessageBean dynamicMessageBean = BeanProvider.getContextualReference(DynamicMessageBean.class, false);
        dynamicMessageBean.action();
        //LOG.info("configured dynamic message: "  + dynamicMessageBean.getMessageContext().message().toString());

        InventoryActions inventoryActions = BeanProvider.getContextualReference(InventoryActions.class, false);
        LOG.info("Dividing 10 per 1, no errors expected!");
        LOG.info(inventoryActions.aritmeticCalculation(1L).toString());
        LOG.info("Dividing 10 per 0, arithmetic exception expected!");
        LOG.info(inventoryActions.aritmeticCalculation(2L).toString());

        TicketServiceImpl ticketServiceImpl =
                BeanProvider.getContextualReference(TicketServiceImpl.class, false);
        LOG.info("Ordering Ticket: " + ticketServiceImpl.orderTicket("Monsters Of Rock"));

        //Starts request context in order to test it
        contextControl.startContext(RequestScoped.class);
        TestBean testBean = BeanProvider.getContextualReference(TestBean.class, false);
        LOG.info("Test bean:" + testBean.getText());



        /* Total amount of free memory available to the JVM after execution*/
        long freeMemoryAfterExecution = Runtime.getRuntime().freeMemory();
        LOG.info("Free memory (bytes) after execution: " + freeMemoryAfterExecution);

        LOG.info("Memory consumed during execution:" + (freeMemory - freeMemoryAfterExecution) );

        cdiContainer.shutdown();
    }
}
