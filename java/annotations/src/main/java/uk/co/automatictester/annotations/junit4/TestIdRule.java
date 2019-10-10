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

                TestId testIdAnnotation = desc.getAnnotation(TestId.class);
                if (testIdAnnotation != null) {
                    String testIdAnnotationValue = testIdAnnotation.value();
                    String entry = String.format("TEST_ID:[%s]", testIdAnnotationValue);
                    System.out.println(entry);
                }

                base.evaluate();
            }
        };
    }
}
