package uk.co.automatictester.behavioral.templatemethod.test;

import org.testng.annotations.Test;
import uk.co.automatictester.behavioral.templatemethod.Algorithm;
import uk.co.automatictester.behavioral.templatemethod.AlgorithmOne;
import uk.co.automatictester.behavioral.templatemethod.AlgorithmTwo;

public class TemplateMethodTest {

    @Test
    public void testExecute() {
        Algorithm one = new AlgorithmOne(2);
        Algorithm two = new AlgorithmTwo(2);

        one.execute();
        two.execute();
    }
}