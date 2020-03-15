package uk.co.automatictester.java11;

import org.testng.annotations.Test;

import java.util.function.BiFunction;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class LambdaTest {

    @Test
    public void varForLambdaParameters() {
        BiFunction<Integer, Integer, Integer> f = (var i1, var i2) -> i1 + i2;

        assertThat(f.apply(2, 2), is(4));
    }
}
