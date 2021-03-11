package client;

import library.AdminHelper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnBean(AdminHelper.class)
public class AdminService implements InitializingBean, ServiceInterface {

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("client: AdminService in the context");
    }
}
