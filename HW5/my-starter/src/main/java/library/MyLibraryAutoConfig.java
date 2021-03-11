package library;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = MyLibraryAutoConfig.class)
public class MyLibraryAutoConfig {

}
