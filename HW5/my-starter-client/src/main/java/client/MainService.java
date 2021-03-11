package client;

import library.HelperInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MainService implements InitializingBean {

    private final HelperInterface helper;
    private final ServiceInterface service;

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("client: MainService");
        System.out.println("        helper: " + helper.getClass());
        System.out.println("        service: " + service.getClass());
    }

}
