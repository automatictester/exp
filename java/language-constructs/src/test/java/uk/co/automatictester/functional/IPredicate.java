package uk.co.automatictester.functional;

import org.testng.annotations.Test;

import java.util.function.Predicate;

public class IPredicate {

    private static void printConditionally(Integer x, Predicate<Integer> trait) {
        if (trait.test(x)) {
            System.out.println(x);
        }
    }

    @Test
    public void predicate() {
        Predicate<Integer> p = x -> x % 2 == 0;
        printConditionally(6, p);
    }
}
