package extension.annotations.test;

import extension.annotations.Property;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 * Created by andrade on 16/04/15.
 */
@Named
@RequestScoped
public class TestBean {
    @Property("property.one") //TODO: check
    private String text;

    public String getText() {
        return text;
    }
}
