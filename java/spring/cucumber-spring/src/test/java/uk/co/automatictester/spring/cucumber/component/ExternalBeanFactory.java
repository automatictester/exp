package uk.co.automatictester.spring.cucumber.component;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExternalBeanFactory {

    @Bean("extBeanA")
    public ExternalBean externalBeanA() {
        return new ExternalBeanA();
    }

    @Bean("extBeanB")
    public ExternalBean externalBeanB() {
        return new ExternalBeanB();
    }

    @Bean
    public ExternalBean externalBeanC() {
        return new ExternalBeanC();
    }
}
