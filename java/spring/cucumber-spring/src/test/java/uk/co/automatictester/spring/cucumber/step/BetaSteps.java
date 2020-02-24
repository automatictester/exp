package uk.co.automatictester.spring.cucumber.step;

import io.cucumber.java8.En;
import uk.co.automatictester.spring.cucumber.state.AnotherState;

public class BetaSteps implements En {

    private final AnotherState anotherState;

    // As of Spring Framework 4.3, an @Autowired annotation on a constructor
    // is no longer necessary if the target bean defines only one constructor
    public BetaSteps(AnotherState anotherState) {
        this.anotherState = anotherState;

        When("something happens", () -> {
            doOtherStuff();
        });
    }

    private void doOtherStuff() {
        anotherState.setId(22);
    }
}
