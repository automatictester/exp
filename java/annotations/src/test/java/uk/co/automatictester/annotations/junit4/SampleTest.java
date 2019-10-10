package uk.co.automatictester.annotations.junit4;

import org.junit.Rule;
import org.junit.Test;

public class SampleTest {

    @Rule
    public TestIdRule rule = new TestIdRule();

    @Test
    @TestId("ABC-001")
    public void testSomething() {

    }

    @Test
    public void testSomethingElse() {

    }
}
