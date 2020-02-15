package uk.co.automatictester.spring.cucumber.step;

import io.cucumber.java8.En;
import org.springframework.test.context.ContextConfiguration;
import uk.co.automatictester.spring.cucumber.config.SpringConfig;

@ContextConfiguration(classes = SpringConfig.class)
public class SpringStepConfig implements En {
}
