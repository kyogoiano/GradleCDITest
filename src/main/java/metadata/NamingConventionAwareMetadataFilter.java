package metadata;

import org.apache.deltaspike.core.api.literal.NamedLiteral;
import org.apache.deltaspike.core.util.metadata.builder.AnnotatedTypeBuilder;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessAnnotatedType;
import javax.inject.Named;

/**
 * Created by andrade on 08/04/15.
 */
public class NamingConventionAwareMetadataFilter implements Extension {
    public void ensureNamingConvention(@Observes ProcessAnnotatedType processAnnotatedType)
    {
        Class<?> beanClass = processAnnotatedType.getAnnotatedType().getJavaClass();
        Named namedAnnotation = beanClass.getAnnotation(Named.class);
        if (namedAnnotation != null &&
                namedAnnotation.value().length() > 0 &&
                Character.isUpperCase(namedAnnotation.value().charAt(0)))
        {
            AnnotatedTypeBuilder builder = new AnnotatedTypeBuilder();

            builder.readFromType(beanClass);
            String beanName = namedAnnotation.value();
            String newBeanName = beanName.substring(0, 1).toLowerCase() + beanName.substring(1);
            builder.removeFromClass(Named.class)
                    .addToClass(new NamedLiteral(newBeanName));
            processAnnotatedType.setAnnotatedType(builder.create());
        }
    }
}
