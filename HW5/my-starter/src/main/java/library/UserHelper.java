package library;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "role.property", havingValue = "user")
public class UserHelper implements InitializingBean, HelperInterface {

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("library: UserHelper in the context");
    }

}
