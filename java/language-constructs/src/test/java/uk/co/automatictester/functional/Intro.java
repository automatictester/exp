package uk.co.automatictester.functional;

import org.testng.annotations.Test;

public class Intro {

    /*
     * - interface with a single abstract method
     * - don't create new one if you can find one in java.util.function
     */
    @FunctionalInterface
    interface TogetherPrinter {
        void together(String s1, String s2);
    }

    /*
     * - method taking functional interface type as a last arg
     * - with former args exactly matching arg list of the abstract method from that interface
     * - that method calling the only abstract method in that interface using other args provided
     */
    private static void printTogether(String s1, String s2, TogetherPrinter printer) {
        printer.together(s1, s2);
    }

    /*
     * - calling that static method, with proper params (including inline definition of a lambda function)
     */
    @Test
    public void intro2() {
        printTogether("little", "monster", (s1, s2) -> {
            System.out.println(s1 + s2);
        });
    }
}
