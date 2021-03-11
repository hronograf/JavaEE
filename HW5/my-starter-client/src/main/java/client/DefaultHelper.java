package client;

import library.AdminHelper;
import library.HelperInterface;
import library.UserHelper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;


public class DefaultHelper implements InitializingBean, HelperInterface {

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("client: DefaultHelper in the context");
    }

}
