package extension.loader;

import extension.ConfigurationException;

/**
 * Created by andrade on 15/04/15.
 */
public final class ClassLoaders {

    private ClassLoaders() {
        // no-op
    }

    public static ClassLoader tccl() {
        final ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        if (contextClassLoader == null) {
            return ClassLoaders.class.getClassLoader();
        }
        return contextClassLoader;
    }

    public static Class<?> loadClass(final String classname) {
        try {
            return tccl().loadClass(classname);
        } catch (final ClassNotFoundException e) {
            throw new ConfigurationException(e);
        }
    }
}
