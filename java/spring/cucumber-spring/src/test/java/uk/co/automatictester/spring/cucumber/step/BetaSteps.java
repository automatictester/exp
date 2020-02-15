package uk.co.automatictester.spring.cucumber.step;

import io.cucumber.java8.En;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import uk.co.automatictester.spring.cucumber.component.AnotherState;
import uk.co.automatictester.spring.cucumber.component.ExternalBean;
import uk.co.automatictester.spring.cucumber.component.ExternalBeanC;
import uk.co.automatictester.spring.cucumber.component.State;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class BetaSteps implements En {

    private final State state;
    private final AnotherState anotherState;

    private @Value("${my.question}")
    String myQuestion;

    @Autowired
    private ApplicationContext context;

    public BetaSteps(
            @Autowired @Qualifier("scenarioState") State state,
            @Autowired AnotherState anotherState,
            @Autowired @Qualifier("extBeanA") ExternalBean beanA,
            @Autowired @Qualifier("extBeanB") ExternalBean beanB,
            @Autowired ExternalBeanC beanC,
            @Autowired @Value("${my.key}") String myKey) {
        this.state = state;
        this.anotherState = anotherState;

        Then("there is a result of it", () -> {
            assertThat(state.getId()).isEqualTo(21);
        });

        And("stuff", () -> {
            assertThat(beanA.getClassName()).isEqualTo("ExternalBeanA");
            assertThat(beanB.getClassName()).isEqualTo("ExternalBeanB");
            assertThat(beanC.getClassName()).isEqualTo("ExternalBeanC");

            assertThat(myKey).isEqualTo("my.value");
            assertThat(myQuestion).isEqualTo("my.answer");

            listBeans();
        });
    }

    private void listBeans() {
        Arrays.stream(context.getBeanDefinitionNames())
                .filter(bean -> !bean.startsWith("org.springframework"))
                .filter(bean -> !bean.startsWith("uk.co.automatictester"))
                .forEach(bean -> log.info("{}", bean));
    }
}
