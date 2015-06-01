package extension.annotations.test;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 * Created by andrade on 16/04/15.
 */
@SessionScoped
public class OtherBean implements Serializable{
    private static final long serialVersionUID = 1L;

    private String otherBeanText;

    @PostConstruct
    private void init() {
        otherBeanText = "other text";
    }

    public String getOtherBeanText() {
        return otherBeanText;
    }
}
