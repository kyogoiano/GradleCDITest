package extension.reflect;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by andrade on 15/04/15.
 */
public class ParameterizedTypeImpl implements ParameterizedType {

    private final Type raw;
    private final Type[] arguments;

    public ParameterizedTypeImpl(final Type raw, final Type[] arguments) {
        this.raw = raw;
        this.arguments = arguments;
    }

    @Override
    public Type[] getActualTypeArguments() {
        return arguments;
    }

    @Override
    public Type getRawType() {
        return raw;
    }

    @Override
    public Type getOwnerType() {
        return null;
    }
}
