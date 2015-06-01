package extension.producer;

import extension.api.CdiContainerShutdown;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import java.lang.reflect.Member;
import java.lang.reflect.Method;

/**
 * Created by andrade on 01/06/15.
 */

@RequestScoped
public class EntityManagerProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(EntityManagerProducer.class);

    private static final String DEFAULT_BEAN_TEST_PERSISTENCE_UNIT = "beanTestPU";

    private EntityManagerFactory emf;

    private EntityManager em;

    @PostConstruct
    private void initializeEntityManagerFactory() {
        emf = Persistence.createEntityManagerFactory(DEFAULT_BEAN_TEST_PERSISTENCE_UNIT);
        LOGGER.info("Entity Manager Factory was successfully initialized");
    }

    @Produces
    public EntityManager getEntityManager(InjectionPoint ip) {
        PersistenceContext
                ctx = ip.getAnnotated().getAnnotation(PersistenceContext.class);

        if (ctx == null) {
            //if @PersisteceContext is declared on method, ctx is null at this point.
            //ctx should be retrieved from the Method.
            Member member = ip.getMember();
            if (member instanceof Method) {
                Method method = (Method) member;
                ctx = method.getAnnotation(PersistenceContext.class);
            }
        }

        LOGGER.debug("PersistenceContext info:");
        //This could happen if the application injects the EntityManager via @Inject instead of @PersistenceContext
        if(ctx != null) {
            LOGGER.debug("Unit name: {}", ctx.unitName());
        }

        LOGGER.debug("Bean defining the injection point: {}", ip.getBean().getBeanClass());
        LOGGER.debug("Field to be injected: {}", ip.getMember());

        if (em == null) {
            em = emf.createEntityManager();
        }
        return em;
    }

    /**
     * Closes the entity manager and entity manager factory when the event {@link CdiContainerShutdown} is fired.
     *
     * @param containerShutdown
     *            the event that indicates that the container is about to shutdown.
     */
    public void closeEntityManagerAndEntityManagerFactory(@Observes CdiContainerShutdown containerShutdown) {
        closeEntityManager();
        closeEntityManagerFactory();
    }

    private void closeEntityManagerFactory() {
        if (emf == null) {
            return;
        }
        if (emf.isOpen()) {
            LOGGER.debug("Closing entity manager factory");
            emf.close();
        }
    }

    private void closeEntityManager() {
        if (em == null) {
            return;
        }
        if (em.isOpen()) {
            try {
                // In case a transaction is still open.
                if (em.getTransaction().isActive() && !em.getTransaction().getRollbackOnly()) {
                    em.getTransaction().commit();
                }
            } finally {
                LOGGER.debug("Closing entity manager");
                em.close();
            }

        }
    }


}
