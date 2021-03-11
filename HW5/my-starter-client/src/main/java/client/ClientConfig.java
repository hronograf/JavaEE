package client;

import library.HelperInterface;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfig {

    @Bean
    @ConditionalOnMissingBean(HelperInterface.class)
    public DefaultHelper defaultHelperBean(){
        return new DefaultHelper();
    }

}
