package uk.co.automatictester.functional;

import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static java.lang.Math.min;

public class IFunction {

    private static int getLongestTransactionNumber(List<Integer> transactionDurations, Function<List<Integer>, Integer> trait) {
        return trait.apply(transactionDurations);
    }

    @Test
    public void functionA() {
        List<Integer> list = Arrays.asList(1, 2, 3);
        Function<List<Integer>, Integer> trait = transactionDurations -> min(transactionDurations.size(), 5);
        int longestTransactionCount = getLongestTransactionNumber(list, trait);
        System.out.println(longestTransactionCount);
    }


    @Test
    public void functionB() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
        Function<List<Integer>, Integer> function = transactionDurations -> min(transactionDurations.size(), 5);
        int longestTransactionCount = function.apply(list);
        System.out.println(longestTransactionCount);
    }
}
