package extension.qualifier;

import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Named;

/**
 * Created by andrade on 15/04/15.
 */
public class NamedQualifier extends AnnotationLiteral<Named> implements Named {

    private final String name;

    public NamedQualifier(String name) {
        this.name = name;
    }

    @Override
    public String value() {
        return name;
    }
}
