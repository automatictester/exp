package uk.co.automatictester.spring.cucumber.step;

import io.cucumber.java8.En;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import uk.co.automatictester.spring.cucumber.state.AnotherState;
import uk.co.automatictester.spring.cucumber.state.State;

public class AlphaSteps implements En {

    private final State state; // autowired constructor param allows for a property to be marked final

    @Autowired // autowired field - not recommended
    private AnotherState anotherState;

    public AlphaSteps(@Autowired @Qualifier("scenarioState") State state) {
        this.state = state;

        Given("nothing", () -> {
        });

        When("something happens", () -> {
            doStuff();
            doOtherStuff();
        });
    }

    private void doStuff() {
        state.setId(21);
    }

    private void doOtherStuff() {
        anotherState.setId(22);
    }
}
