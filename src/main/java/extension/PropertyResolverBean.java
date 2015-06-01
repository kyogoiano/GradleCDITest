package extension;

import extension.annotations.PropertyLocale;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by andrade on 20/05/15.
 */
public class PropertyResolverBean<R> {

    private final AnnotatedMethod<? super R> propertyResolverMethod;
    private final BeanManager beanManager;
    private Object propertyResolverInstance;
    private List<InjectionPoint> propertyResolverParameters;
    private final boolean propertyLocalePresent;


    public PropertyResolverBean(AnnotatedMethod<? super R> propertyResolverMethod,
                                BeanManager beanManager) {
        this.propertyResolverMethod = propertyResolverMethod;
        this.beanManager = beanManager;
        this.propertyLocalePresent = checkLocaleParameter();
    }

    private boolean checkLocaleParameter() {
        for (Annotation[] annotations : propertyResolverMethod
                .getJavaMember().getParameterAnnotations()) {
            for (Annotation annotation : annotations) {
                if (annotation.annotationType()
                        .equals(PropertyLocale.class)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void initializePropertyResolverBean() {
        // Get any existing eligible bean based on the type of the Property
        // Resolver method containing class.
        Set<Bean<?>> beans = beanManager.getBeans(propertyResolverMethod
                .getJavaMember().getDeclaringClass());

        final Bean<?> propertyResolverBean = beanManager.resolve(beans);
        CreationalContext<?> creationalContext = beanManager.
                createCreationalContext(propertyResolverBean);

        propertyResolverInstance = beanManager.getReference(propertyResolverBean,
                propertyResolverMethod.getJavaMember().getDeclaringClass(), creationalContext);

        propertyResolverParameters = new ArrayList<>();

        // We assume that the first parameter is the property to be resolved
        int startIndex = 1;
        if (propertyLocalePresent) {
            // If we have the additional locale property then the first
            // couple of parameters are the locale and the property key
            // (first is the locale; second is the property key)
            startIndex = 2;
        }

        // Create injection points for any additional Property Resolver
        // method parameters. They will be later injected by the container
        if (propertyResolverMethod.getParameters().size() > startIndex) {
            int currentIndex = 0;
            for (final AnnotatedParameter<? super R> parameter :
                    propertyResolverMethod.getParameters()) {

                if (currentIndex++ < startIndex) {
                    continue;
                }

                propertyResolverParameters.add(new InjectionPoint() {

                    @Override
                    public Type getType() {
                        return parameter.getBaseType();
                    }

                    @Override
                    public Set<Annotation> getQualifiers() {
                        Set<Annotation> qualifiers = new HashSet<Annotation>();
                        for (Annotation annotation : parameter
                                .getAnnotations()) {
                            if (beanManager.isQualifier(annotation
                                    .annotationType())) {
                                qualifiers.add(annotation);
                            }
                        }
                        return qualifiers;
                    }

                    @Override
                    public Bean<?> getBean() {
                        return propertyResolverBean;
                    }

                    @Override
                    public Member getMember() {
                        return parameter.getDeclaringCallable()
                                .getJavaMember();
                    }

                    @Override
                    public Annotated getAnnotated() {
                        return parameter;
                    }

                    @Override
                    public boolean isDelegate() {
                        return false;
                    }

                    @Override
                    public boolean isTransient() {
                        return false;
                    }

                });

            }
        }

    }

    public String resolveProperty(String key, CreationalContext<?> ctx)
            throws IllegalAccessException, IllegalArgumentException,
            InvocationTargetException {

        List<Object> parameters = new ArrayList<>();

        // If the Locale property is present, it must be the first (JSF)
        // parameter in the Property Resolver method
//        if (propertyLocalePresent) {
//            parameters.add(FacesContext.getCurrentInstance().getViewRoot()
//                    .getLocale());
//        }

        // The property key is the next parameter
        parameters.add(key);

        // Resolve any eventual additional parameter to be injected by the
        // CDI container
        for (InjectionPoint parameter : propertyResolverParameters) {
            parameters.add(beanManager.getInjectableReference(parameter,
                    ctx));
        }

        // Call the property resolver method
        return (String) propertyResolverMethod.getJavaMember().invoke(
                propertyResolverInstance, parameters.toArray());
    }

}
