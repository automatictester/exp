package uk.co.automatictester.spring.cucumber.step;

import io.cucumber.java8.En;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import uk.co.automatictester.spring.cucumber.state.AnotherState;
import uk.co.automatictester.spring.cucumber.state.State;
import uk.co.automatictester.spring.external.beans.ExternalBean;
import uk.co.automatictester.spring.external.beans.ExternalBeanC;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

            checkBeans();
            listBeanClasses();

            assertThat(context.getBean("scenarioState")).isInstanceOf(State.class);
        });
    }

    private void checkBeans() {
        List<String> beans = Arrays.stream(context.getBeanDefinitionNames())
                .filter(bean -> !bean.startsWith("org.springframework"))
                .filter(bean -> !bean.startsWith("uk.co.automatictester")) // cucumber step definitions
                .collect(Collectors.toList());

        beans.forEach(bean -> log.info("Bean name: {}", bean));
        assertThat(beans.size()).isEqualTo(8);
    }

    private void listBeanClasses() {
        List<String> beans = Arrays.stream(context.getBeanDefinitionNames())
                .filter(bean -> !bean.startsWith("org.springframework"))
                .filter(bean -> !bean.startsWith("uk.co.automatictester"))
                .collect(Collectors.toList());

        beans.forEach(bean -> {
            String beanClass = context.getBean(bean).getClass().getSimpleName();
            log.info("Bean class: {}", beanClass);
        });
        assertThat(beans.size()).isEqualTo(8);
    }
}
