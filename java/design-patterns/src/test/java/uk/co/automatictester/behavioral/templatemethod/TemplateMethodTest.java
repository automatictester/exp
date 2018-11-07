package uk.co.automatictester.behavioral.templatemethod;

import org.testng.annotations.Test;

public class TemplateMethodTest {

    @Test
    public void testExecute() {
        Algorithm one = new AlgorithmOne(2);
        Algorithm two = new AlgorithmTwo(2);

        one.execute();
        two.execute();
    }
}