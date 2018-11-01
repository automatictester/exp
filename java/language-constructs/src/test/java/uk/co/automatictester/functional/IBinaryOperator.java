package uk.co.automatictester.functional;

import org.testng.annotations.Test;

import java.util.function.BiFunction;
import java.util.function.BinaryOperator;

import static java.lang.Math.max;


public class IBinaryOperator {

    @Test
    public void function() {
        BiFunction<Integer, Integer, Integer> biFunction = (i1, i2) -> max(i1, i2);
        int i = biFunction.apply(1, 7);
        System.out.println(i);
    }

    @Test
    public void operator() {
        BinaryOperator<Integer> binaryOperator = (i1, i2) -> max(i1, i2);
        int i = binaryOperator.apply(1, 7);
        System.out.println(i);
    }
}
