package extension;

import config.Location;
import config.Property2;
import extension.factory.ContextualFactory;
import extension.model.ConfigBean;
import extension.reflect.ParameterizedTypeImpl;
import org.apache.deltaspike.core.spi.activation.Deactivatable;
import org.apache.deltaspike.core.util.bean.BeanBuilder;
import org.apache.deltaspike.core.util.metadata.AnnotationInstanceProvider;
import org.apache.deltaspike.core.util.metadata.builder.AnnotatedTypeBuilder;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.*;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.interceptor.Interceptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collection;

import static extension.loader.ClassLoaders.tccl;
import static extension.qualifier.Qualifiers.toQualifier;
import static extension.scope.Scopes.toScope;

/**
 * Created by andrade on 15/04/15.
 */

public class BeanTestExtension implements Extension, Deactivatable{
    public <X> void processInjectionTarget(@Observes @WithAnnotations(
            {Property2.class, Location.class, Interceptor.class, Singleton.class})ProcessAnnotatedType<X> pat){
        if(pat.getAnnotatedType().isAnnotationPresent(Property2.class)
                || pat.getAnnotatedType().isAnnotationPresent(Location.class)) {
            modifyAnnotatedTypeMetaData(pat);
        } else if (pat.getAnnotatedType().isAnnotationPresent(Interceptor.class)) {
            processInterceptorDependencies(pat);
        } else if (pat.getAnnotatedType().isAnnotationPresent(Singleton.class)) {
            addApplicationScopedAndTransactionalToSingleton(pat);
        }
    }

    private <X> void addApplicationScopedAndTransactionalToSingleton(ProcessAnnotatedType<X> pat) {
        AnnotatedType at = pat.getAnnotatedType();

        AnnotatedTypeBuilder<X> builder = new AnnotatedTypeBuilder<X>().readFromType(at);

        builder.addToClass(AnnotationInstances.APPLICATION_SCOPED).addToClass(AnnotationInstances.TRANSACTIONAL);

        InjectionHelper.addInjectAnnotation(at, builder);

        pat.setAnnotatedType(builder.create());
    }

    private <X> void processInterceptorDependencies(ProcessAnnotatedType<X> pat) {
        AnnotatedTypeBuilder<X> builder = new AnnotatedTypeBuilder<X>().readFromType(pat.getAnnotatedType());

        InjectionHelper.addInjectAnnotation(pat.getAnnotatedType(), builder);
        pat.setAnnotatedType(builder.create());
    }

    private <X> void modifyAnnotatedTypeMetaData(ProcessAnnotatedType<X> pat) {

        AnnotatedType <X> at = pat.getAnnotatedType();

        AnnotatedTypeBuilder<X> builder = new AnnotatedTypeBuilder<X>().readFromType(at);
        builder.addToClass(AnnotationInstances.TRANSACTIONAL).addToClass(AnnotationInstances.REQUEST_SCOPED);

        InjectionHelper.addInjectAnnotation(at, builder);
        //Set the wrapper instead the actual annotated type
        pat.setAnnotatedType(builder.create());
    }

}
