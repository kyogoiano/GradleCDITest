package hello;

import echo.DefaultEchoService;
import echo.EchoService;
import optional.OptionalService;
import org.apache.deltaspike.cdise.api.CdiContainer;
import org.apache.deltaspike.cdise.api.CdiContainerLoader;
import org.apache.deltaspike.cdise.api.ContextControl;
import org.apache.deltaspike.core.api.provider.BeanProvider;
import org.apache.deltaspike.core.util.bean.BeanBuilder;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by andrade on 07/04/15.
 *
 * Example which illustrates the usage of {@inheritDoc BeanProvider}
 */
public class MainApp {

    private static final Logger LOG = Logger.getLogger(MainApp.class.getName());

//    @Inject
//    private ApplicationScopedBean applicationScopedBean;

    @Inject static BeanManager beanManager;

    public static void main(String[] args){
        long startTime = System.currentTimeMillis();
        CdiContainer cdiContainer = CdiContainerLoader.getCdiContainer();
        cdiContainer.boot();

        ContextControl contextControl = cdiContainer.getContextControl();
        contextControl.startContext(ApplicationScoped.class);
        long stopTime = System.currentTimeMillis();

        long elapsedTime = stopTime - startTime;
        LOG.info("Building and Starting container and contex: " + elapsedTime);

        List<EchoService> echoServiceList = BeanProvider.getContextualReferences(EchoService.class, false);

        for (EchoService echoService: echoServiceList){
            LOG.info(echoService.echo("Info of the CDI bean"));
        }
        LOG.info("-----------");

        echoServiceList = BeanProvider.getContextualReferences(EchoService.class, false, false);
        for (EchoService echoService: echoServiceList){
            LOG.info(echoService.echo("Info of the non dependent CDI scoped beans"));
        }
        LOG.info("-----------");

        EchoService defaultEchoService = BeanProvider.getContextualReference(DefaultEchoService.class, false);
        LOG.info(defaultEchoService.echo("Info of the explicitly resolved CDI bean"));
        LOG.info("-----------");

        defaultEchoService = BeanProvider.getContextualReference("defaultEchoService", false, EchoService.class);
        LOG.info(defaultEchoService.echo("Info of CDI bean resolved by name"));
        LOG.info("-----------");

        OptionalService optionalService = BeanProvider.getContextualReference(OptionalService.class, true);
        if(optionalService == null){

            LOG.info("No (optional) implementation found for " + OptionalService.class.getName());
        } else {

            LOG.severe("Unexpected implementation found: " + optionalService.getClass().getName());
        }


        contextControl.stopContext(ApplicationScoped.class);
        cdiContainer.shutdown();

//        try{
//            BeanProvider.injectFields(new MainApp()).runApplicationLogic();
//        } finally {
//            cdiContainer.shutdown();
//        }
    }

//    void runApplicationLogic(){
//        LOG.info("Info of injected bean: " + this.applicationScopedBean.getValue());
//    }
}
