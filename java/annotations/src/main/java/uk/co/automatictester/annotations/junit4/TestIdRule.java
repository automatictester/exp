package uk.co.automatictester.annotations.junit4;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class TestIdRule implements TestRule {

    @Override
    public Statement apply(Statement base, Description desc) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                try {
                    base.evaluate();
                } finally {
                    String testId = desc.getAnnotation(TestId.class).value();
                    String entry = String.format("TEST_ID:[%s]", testId);
                    System.out.println(entry);
                }
            }
        };
    }
}
