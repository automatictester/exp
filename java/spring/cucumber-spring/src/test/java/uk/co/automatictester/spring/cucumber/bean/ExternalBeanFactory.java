package uk.co.automatictester.spring.cucumber.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.co.automatictester.spring.external.beans.ExternalBean;
import uk.co.automatictester.spring.external.beans.ExternalBeanA;
import uk.co.automatictester.spring.external.beans.ExternalBeanB;
import uk.co.automatictester.spring.external.beans.ExternalBeanC;

@Configuration
public class ExternalBeanFactory {

    @Bean("extBeanA") // renames @Bean bean from 'externalBeanA' to 'extBeanA'
    public ExternalBean externalBeanA() {
        return new ExternalBeanA();
    }

    @Bean
    public ExternalBean externalBeanB() {
        return new ExternalBeanB();
    }

    @Bean
    public ExternalBean externalBeanC() {
        return new ExternalBeanC();
    }
}
