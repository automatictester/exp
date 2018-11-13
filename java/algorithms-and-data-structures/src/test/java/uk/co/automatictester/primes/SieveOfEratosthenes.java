package uk.co.automatictester.primes;

import org.testng.annotations.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Math.sqrt;

public class SieveOfEratosthenes {

    private static final int START = 2;
    private static final int LIMIT = 50_000;

    @Test(invocationCount = 4)
    public void preJava8() {
        List<Integer> potentialPrimes = IntStream.rangeClosed(START, LIMIT).boxed().collect(Collectors.toList());
        Set<Integer> toRemove = new HashSet<>();
        Set<Integer> target = new HashSet<>(potentialPrimes);

        for (int i = 0; i < potentialPrimes.size() && potentialPrimes.get(i) < sqrt(LIMIT); i++) {
            int elementValue = potentialPrimes.get(i);

            for (int j = elementValue * 2; j <= LIMIT; j += elementValue) {
                if (j % elementValue == 0) {
                    toRemove.add(j);
                }
            }
        }

        target.removeAll(toRemove);
        System.out.println(target.size());
    }

    @Test(invocationCount = 4)
    public void java8() {
        List<Integer> potentialPrimes = IntStream.rangeClosed(START, LIMIT).boxed().collect(Collectors.toList());
        Set<Integer> primes = new HashSet<>();

        while (!potentialPrimes.isEmpty()) {
            int head = potentialPrimes.get(0);
            primes.add(head);
            potentialPrimes.removeIf(x -> x % head == 0);
        }

        System.out.println(primes.size());
    }
}
