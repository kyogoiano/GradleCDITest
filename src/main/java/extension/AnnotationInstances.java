package extension;

import extension.transactional.Transactional;
import org.apache.deltaspike.core.util.metadata.AnnotationInstanceProvider;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by andrade on 01/06/15.
 */
public final class AnnotationInstances {
    private AnnotationInstances(){

    }


    public static final Transactional TRANSACTIONAL = AnnotationInstanceProvider.of(Transactional.class);
    public static final RequestScoped REQUEST_SCOPED = AnnotationInstanceProvider.of(RequestScoped.class);
    public static final Inject INJECT = AnnotationInstanceProvider.of(Inject.class);
    public static final Singleton SINGLETON = AnnotationInstanceProvider.of(Singleton.class);
    public static final ApplicationScoped APPLICATION_SCOPED = AnnotationInstanceProvider.of(ApplicationScoped.class);


}
