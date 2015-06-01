package extension.transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by andrade on 01/06/15.
 */

@Interceptor
@Transactional
public class TransactionalInterceptor {

    /**
     * Exceptions that should not cause the transaction to rollback according to Java EE Documentation.
     * (http://docs.oracle.com/javaee/7/api/javax/persistence/PersistenceException.html)
     */
    private static final Set<Class<?>> NO_ROLLBACK_EXCEPTIONS= new HashSet<Class<?>>(Arrays.asList(
            NonUniqueResultException.class,
            NoResultException.class,
            QueryTimeoutException.class,
            LockTimeoutException.class));

    @Inject
    @PersistenceContext
    EntityManager em;

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionalInterceptor.class);

    private static int INTERCEPTOR_COUNTER = 0;

    @AroundInvoke
    public Object manageTransaction(InvocationContext ctx) throws Exception {

        EntityTransaction transaction = em.getTransaction();
        if (!transaction.isActive()) {
            transaction.begin();
            LOGGER.debug("Transaction started");
        }

        INTERCEPTOR_COUNTER++;
        Object result = null;
        try {
            result = ctx.proceed();

        } catch (Exception e) {
            if (isFirstInterceptor()) {
                markRollbackTransaction(e);
            }
            throw e;
        } finally {
            processTransaction();
        }

        return result;
    }

    private void processTransaction() {
        EntityTransaction transaction = em.getTransaction();
        try {

            if (em.isOpen() && transaction.isActive() && isFirstInterceptor()) {
                if (transaction.getRollbackOnly()) {
                    transaction.rollback();
                    LOGGER.debug("Transaction was rollbacked");
                } else {
                    transaction.commit();
                    LOGGER.debug("Transaction committed");
                }
                em.clear();
            }
        } catch (Exception e) {
            LOGGER.warn("Error when trying to commit transaction: {0}", e);
            throw e;
        } finally {
            INTERCEPTOR_COUNTER--;
        }
    }

    private static boolean isFirstInterceptor() {
        return INTERCEPTOR_COUNTER -1 == 0;
    }

    /**
     * Marks the transaction for rollback via {@link EntityTransaction#setRollbackOnly()}.
     */
    private void markRollbackTransaction(Exception exception) throws Exception {
        try {
            if (em.isOpen() && em.getTransaction().isActive() && shouldExceptionCauseRollback(exception)) {
                em.getTransaction().setRollbackOnly();
            }
        } catch (Exception e) {
            LOGGER.warn("Error when trying to roll back the  transaction: {0}", e);
            throw e;
        }

    }

    private static boolean shouldExceptionCauseRollback(Exception e ) {
        return ! NO_ROLLBACK_EXCEPTIONS.contains(e.getClass());
    }

}
