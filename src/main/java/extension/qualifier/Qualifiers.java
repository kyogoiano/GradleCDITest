package extension.qualifier;

import extension.ConfigurationException;
import extension.loader.ClassLoaders;
import org.apache.deltaspike.core.util.metadata.AnnotationInstanceProvider;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by andrade on 15/04/15.
 */
public abstract class Qualifiers {

    public static String selectQualifier(final String qualifier) {
        if (qualifier == null || "name".equals(qualifier)) {
            return "name";
        }
        return qualifier;
    }

    public static Annotation toQualifier(final String rawQualifier, final String name) {
        final String qualifier = selectQualifier(rawQualifier);
        if (qualifier == null || "name".equals(qualifier)) {
            return new NamedQualifier(name);
        }
        if (qualifier.isEmpty()) {
            return null;
        }

        final Map<String, String> potentialAttributes = new HashMap<>();
        potentialAttributes.put("value", name);
        potentialAttributes.put("name", name);

        try {
            return AnnotationInstanceProvider.of((Class<? extends Annotation>) ClassLoaders.tccl().loadClass(qualifier), potentialAttributes);
        } catch (final ClassNotFoundException e) {
            // no-op
        }

        throw new ConfigurationException("Can't find qualfier '" + qualifier + "'");
    }
}
