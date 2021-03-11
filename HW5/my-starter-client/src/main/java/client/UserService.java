package client;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnMissingBean(AdminService.class)
public class UserService implements InitializingBean, ServiceInterface {

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("client: UserService in the context");
    }
}
