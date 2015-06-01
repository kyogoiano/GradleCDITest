package hello;

import javax.enterprise.context.ApplicationScoped;

/**
 * Created by andrade on 08/04/15.
 */
@ApplicationScoped
public class ApplicationScopedBean {
    public int getValue(){
        return 14;
    }
}
