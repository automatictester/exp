package uk.co.automatictester.concurrency.custom.processors;

import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;

public class SingleThreadedCalculator {

    private static final int ELEMENT_COUNT = 36_000_000;

    @Test(invocationCount = 10)
    public void test() {
        float[] result = new Calculator().getValues();
        assertThat(result.length, equalTo(ELEMENT_COUNT));
        assertThat((double) result[ELEMENT_COUNT - 1], closeTo(-3490.48, 0.01));
    }

    static class Calculator {

        public float[] getValues() {
            int size = ELEMENT_COUNT;
            float[] lookupValues = new float[size];
            for (int i = 0; i < (size); i++) {
                float sinValue = (float) Math.sin((i % 360) * Math.PI / 180.0);
                lookupValues[i] = sinValue * (float) i / 180.0f;
            }
            return lookupValues;
        }
    }
}
