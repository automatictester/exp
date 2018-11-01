package uk.co.automatictester.functional;

import org.testng.annotations.Test;

import java.util.function.Consumer;

public class IConsumer {

    private static void printInput(String input, Consumer<String> trait) {
        trait.accept(input);
    }

    @Test
    public void consumer1() {
        Consumer<String> consumer = System.out::println;
        printInput("hahaha", consumer);
    }
}
