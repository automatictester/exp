package uk.co.automatictester.spring.cucumber.step;

import io.cucumber.java8.En;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import uk.co.automatictester.spring.cucumber.component.AnotherState;
import uk.co.automatictester.spring.cucumber.component.ExternalBean;
import uk.co.automatictester.spring.cucumber.component.ExternalBeanC;
import uk.co.automatictester.spring.cucumber.component.State;

import static org.assertj.core.api.Assertions.assertThat;

public class BetaSteps implements En {

    private final State state;
    private final AnotherState anotherState;

    private @Value("${my.question}")
    String myQuestion;

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
        });
    }
}
