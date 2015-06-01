package extension.api;

import org.apache.deltaspike.cdise.api.CdiContainer;
import org.apache.deltaspike.cdise.api.CdiContainerLoader;
import org.apache.deltaspike.core.api.provider.BeanProvider;

import javax.enterprise.inject.spi.BeanManager;
import java.lang.annotation.Annotation;

/**
 * Created by andrade on 16/04/15.
 */
public class BeanProviderHelper {
    private CdiContainer cdiContainer;
    private static BeanProviderHelper INSTANCE = new BeanProviderHelper();

    public static  BeanProviderHelper getInstance(){
        return INSTANCE;
    }

    private BeanProviderHelper(){
    }

    /**
     * Starts the CDI Container and initializes its contexts.
     */
    private void bootstrapCdiContainer(){
        cdiContainer = CdiContainerLoader.getCdiContainer();
        cdiContainer.boot();
        cdiContainer.getContextControl().startContexts();
    }

    public <T> T getBean(Class<T> beanClass, Annotation... qualifiers){
        if(cdiContainer == null){
            bootstrapCdiContainer();
        }
        return BeanProvider.getContextualReference(beanClass, qualifiers);
    }

    public BeanManager getBeanManager() {
        if (cdiContainer == null) {
            bootstrapCdiContainer();
        }

        return cdiContainer.getBeanManager();
    }

    public void shutdown() {
        if (cdiContainer != null) {
            try {
                fireShutdownEvent();
            } finally {
                cdiContainer.shutdown();
                cdiContainer = null;
            }

        }
    }

    private void fireShutdownEvent() {
        CdiContainerShutdown containerShutdown = new CdiContainerShutdown();
        getBeanManager().fireEvent(containerShutdown);
    }
}
