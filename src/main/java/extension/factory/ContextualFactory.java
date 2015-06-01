package extension.factory;

import extension.model.ConfigBean;
import org.apache.deltaspike.core.util.metadata.builder.ContextualLifecycle;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.naming.Context;
import javax.naming.Name;
import java.util.Hashtable;

/**
 * Created by andrade on 15/04/15.
 */
public class ContextualFactory<T> implements ContextualLifecycle<T> {

    private final ObjectFactory<T> delegate;

    public ContextualFactory(final ConfigBean bean) {
        this.delegate = new ObjectFactory<>(bean);
    }

    @Override
    public T create(Bean<T> bean, CreationalContext<T> creationalContext) {
        return delegate.create();
    }

    @Override
    public void destroy(Bean<T> bean, T instance, CreationalContext<T> creationalContext) {
        delegate.destroy(instance);
    }
}
