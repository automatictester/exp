package uk.co.automatictester.spring.cucumber.state;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE;

@Component("scenarioState") // renames @Component bean from 'state' to 'scenarioState'
@Scope(SCOPE_CUCUMBER_GLUE)
@Getter
@Setter
@Slf4j
public class State implements InitializingBean, DisposableBean {

    private int id;

    // spring and javax lifecycle annotations, in execution order

    @PostConstruct
    public void postContruct() {
        log.info("PostConstruct");
    }

    @Override
    public void afterPropertiesSet() {
        log.info("InitializingBean - afterPropertiesSet");
    }

    @PreDestroy
    public void preDestroy() {
        log.info("PreDestroy");
    }

    @Override
    public void destroy() {
        log.info("DisposableBean - destroy");
    }
}
