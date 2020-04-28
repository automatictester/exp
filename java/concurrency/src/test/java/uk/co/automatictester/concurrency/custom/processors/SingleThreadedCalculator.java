package uk.co.automatictester.concurrency.custom.processors;

import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;

public class SingleThreadedCalculator {

    @Test(invocationCount = 10)
    public void test() {
        double[] element = new Calculator().getValues();
        assertThat(element.length, equalTo(36_000_000));
        assertThat(element[36_000_000 - 1], closeTo(-3490.48, 0.01));
    }

    static class Calculator {

        public double[] getValues() {
            int size = 36_000_000;
            double[] lookupValues = new double[size];
            for (int i = 0; i < (size); i++) {
                float sinValue = (float) Math.sin((i % 360) * Math.PI / 180.0);
                lookupValues[i] = sinValue * (float) i / 180.0f;
            }
            return lookupValues;
        }
    }
}
