package uk.co.automatictester.primes;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Math.sqrt;

public class SieveOfEratosthenes {

    private static final int START = 2;
    private static final int LIMIT = 1_000;

    @Test(invocationCount = 4)
    public void linearSieve() {
        List<Integer> input = IntStream.rangeClosed(START, LIMIT).boxed().collect(Collectors.toList());
        List<Integer> toRemove = new ArrayList<>();
        List<Integer> target = new ArrayList<>(input);

        for (int i = 0; i < input.size() && input.get(i) < sqrt(LIMIT); i++) {
            int elementValue = input.get(i);

            for (int j = elementValue * 2; j <= LIMIT; j += elementValue) {
                if (j % elementValue == 0) {
                    toRemove.add(j);
                }
            }
        }

        target.removeAll(toRemove);

        print(target);
    }

    @Test(invocationCount = 4)
    public void recursiveSieve() {
        List<Integer> input = IntStream.rangeClosed(START, LIMIT).boxed().collect(Collectors.toList());
        sieveNextElement(input, new ArrayList<>());
    }

    private void sieveNextElement(List<Integer> potentialPrimes, List<Integer> primes) {
        if (!potentialPrimes.isEmpty()) {

            int head = potentialPrimes.get(0);
            primes.add(head);

            potentialPrimes.removeIf(integer -> integer % head == 0);
            sieveNextElement(potentialPrimes, primes);

        } else {
            print(primes);
        }
    }

    private void print(List<Integer> primes) {
//        System.out.println(primes.toString());
        System.out.println(primes.size());
    }
}
