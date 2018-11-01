package uk.co.automatictester.streams.benchmarks;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Math.min;

public class Limiting {

    private List<Integer> entries = new ArrayList<>();

    private Supplier<String[]> supplier = () -> {
        String label = RandomStringUtils.randomAlphabetic(1);
        String elapsed = RandomStringUtils.randomNumeric(1, 4);
        String success = (Integer.parseInt(RandomStringUtils.randomNumeric(1)) % 2 == 0) ? "true" : "false";
        String timeStamp = RandomStringUtils.randomNumeric(13, 13);
        return new String[]{label, elapsed, success, timeStamp};
    };

    private IntSupplier intSupplier = () -> Integer.parseInt(RandomStringUtils.randomNumeric(1, 4));

    @BeforeClass
    public void populate() {
        Instant before = Instant.now();

        entries = IntStream
                .generate(intSupplier)
                .limit(3000000)
                .boxed()
                .sorted((i1, i2) -> i2 - i1)
                .collect(Collectors.toList());

        Instant after = Instant.now();
        long executionTime = after.toEpochMilli() - before.toEpochMilli();
        String message = String.format("TestGen execution time: %dms", executionTime);
        System.out.println(message);
    }

    @Test
    public void sort() {
        Instant before = Instant.now();
        List<Integer> result = runStream();
//        List<Integer> result = runLoop();
        Instant after = Instant.now();

        long executionTime = after.toEpochMilli() - before.toEpochMilli();
        String message = String.format("Execution time: %dms", executionTime);
        System.out.println(message);
        System.out.println(result);
    }

    private List<Integer> runStream() {
        int transactionDurationsCount = min(entries.size(), 5);
        return entries.stream()
                .limit(transactionDurationsCount)
                .collect(Collectors.toList());
    }

//    private List<Integer> runLoop() {
//        List<Integer> transactionDurations = new ArrayList<>();
//        for (String[] transaction : entries) {
//            int elapsed = Integer.parseInt(transaction[1]);
//            transactionDurations.add(elapsed);
//        }
//        Collections.sort(transactionDurations);
//        Collections.reverse(transactionDurations);
//        return transactionDurations;
//    }
}
