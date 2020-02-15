package uk.co.automatictester.spring.cucumber.step;

import io.cucumber.java8.En;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import uk.co.automatictester.spring.cucumber.component.AnotherState;
import uk.co.automatictester.spring.cucumber.component.State;

public class AlphaSteps implements En {

    private final State state;
    private final AnotherState anotherState;

    public AlphaSteps(@Autowired @Qualifier("scenarioState") State state,
                      @Autowired AnotherState anotherState) {
        this.state = state;
        this.anotherState = anotherState;

        Given("nothing", () -> {
        });

        When("something happens", () -> {
            state.setId(21);
        });
    }
}
