package apiTest;

import extension.api.BeanProviderHelper;
import org.apache.deltaspike.core.api.provider.BeanProvider;
import org.junit.After;
import org.junit.Before;

import java.lang.annotation.Annotation;

/**
 * Created by andrade on 16/04/15.
 */
public abstract class BaseBeanTest {

    private BeanProviderHelper beanProviderHelper;

    @Before
    public void initialize(){
        beanProviderHelper = BeanProviderHelper.getInstance();
    }

    @After
    public void cleanUp() {
        beanProviderHelper.shutdown();
    }

    protected <T> T getBean(Class<T> beanClass, Annotation... qualifiers) {
        return beanProviderHelper.getBean(beanClass, qualifiers);
    }

}
