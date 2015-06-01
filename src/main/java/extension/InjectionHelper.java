package extension;

import org.apache.deltaspike.core.util.metadata.builder.AnnotatedTypeBuilder;

import javax.annotation.Resource;
import javax.enterprise.inject.spi.AnnotatedField;
import javax.enterprise.inject.spi.AnnotatedMember;
import javax.enterprise.inject.spi.AnnotatedMethod;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.inject.Inject;
import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by andrade on 01/06/15.
 */
public final class InjectionHelper {

    private static final Set<Class<? extends Annotation>> JAVA_EE_ANNOTATIONS = createJavaEEAnnotationSet();

    private static Set<Class<? extends Annotation>> createJavaEEAnnotationSet() {

        Set<Class<? extends Annotation>> javaEEAnnotations =
                new HashSet<>();
        javaEEAnnotations.add(Resource.class);
        return Collections.unmodifiableSet(javaEEAnnotations);
    }

    /**
     * Returns <code>true</code> if the member is NOT annotated with {@link javax.inject.Inject} and is annotated with one of the following annotations:
     * <ul>
     * <li> {@link Resource}
     * </ul>
     * Otherwise, it returns <code>false</code>.
     *
     * @param <X>
     *            the type of the annotated member
     * @param member
     *            the annotated member whose annotations should be verified.
     * @return <code>true</code> if the member is NOT annotated with {@link javax.inject.Inject}
     *          or {@link Resource}
     */
    public static <X>  boolean shouldInjectionAnnotationBeAddedToMember(AnnotatedMember<? super X> member) {
        return !member.isAnnotationPresent(Inject.class) && hasJavaEEAnnotations(member);
    }

    /**
     * Returns <code>true</code> if at least one of the following Java EE annotations is present in the given member:
     * <ul>
     * <li> {@link Resource}
     * </ul>
     * Otherwise, it returns <code>false</code>.
     * @param <X> the type of the annotated member.
     * @param member the member whose annotations should be verified.
     * @return <code>true</code> if the member is at least annotated with one of the following annotations: {@link Resource}.
     */
    private static <X> boolean hasJavaEEAnnotations(AnnotatedMember<? super X> member) {
        for(Class<? extends Annotation> javaEEannotation : JAVA_EE_ANNOTATIONS) {
            if (member.isAnnotationPresent(javaEEannotation)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds the {@link Inject} annotation to the fields and setters of the annotated type if required.
     *
     * @param <X>
     *            the type of the annotated type
     * @param annotatedType
     *            the annotated type whose fields and setters the inject annotation should be added to
     * @param builder
     *            the builder that should be used to add the annotation.
     * @see #shouldInjectionAnnotationBeAddedToMember(AnnotatedMember)
     */
    public static <X> void addInjectAnnotation(final AnnotatedType<X> annotatedType, AnnotatedTypeBuilder<X> builder) {
        for (AnnotatedField<? super X> field : annotatedType.getFields()) {
            if (shouldInjectionAnnotationBeAddedToMember(field)) {
                builder.addToField(field, AnnotationInstances.INJECT);
            }
        }
        for (AnnotatedMethod<? super X> method : annotatedType.getMethods()) {
            if (shouldInjectionAnnotationBeAddedToMember(method)) {
                builder.addToMethod(method,  AnnotationInstances.INJECT);
            }
        }
    }
}
