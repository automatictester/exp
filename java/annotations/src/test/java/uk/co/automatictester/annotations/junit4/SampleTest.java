package uk.co.automatictester.annotations.junit4;

import org.junit.Rule;
import org.junit.Test;

public class SampleTest {

    @Rule
    public TestIdRule rule = new TestIdRule();

    @Test
    @TestId("ABC-101")
    public void testSomething() {

    }

    @Test
    @TestId("ABC-102")
    public void testSomethingElse() {

    }
}
