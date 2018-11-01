package uk.co.automatictester.streams.benchmarks;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Filtering {

    private List<String[]> entries = new ArrayList<>();

    private Supplier<String[]> supplier = () -> {
        String label = RandomStringUtils.randomAlphabetic(1);
        String elapsed = RandomStringUtils.randomNumeric(1, 4);
        String success = (Integer.parseInt(RandomStringUtils.randomNumeric(1)) % 2 == 0) ? "true" : "false";
        String timeStamp = RandomStringUtils.randomNumeric(13, 13);
        return new String[]{label, elapsed, success, timeStamp};
    };

    @BeforeClass
    public void populate() {
        Instant before = Instant.now();

        entries = Stream
                .generate(supplier)
                .limit(3_000_000)
                .collect(Collectors.toList());

        Instant after = Instant.now();
        long executionTime = after.toEpochMilli() - before.toEpochMilli();
        String message = String.format("TestGen execution time: %dms", executionTime);
        System.out.println(message);
    }

    @Test
    public void filter() {
        Instant before = Instant.now();
        int count = runStream();
//        int count = runLoop();
        Instant after = Instant.now();

        long executionTime = after.toEpochMilli() - before.toEpochMilli();
        String message = String.format("Execution time: %dms", executionTime);
        System.out.println(message);
        System.out.println(count);
    }

    private int runStream() {
        return (int) entries.stream()
                .filter(e -> "false".equals(e[2]))
                .count();
    }

    private int runLoop() {
        int failCount = 0;
        for (String[] e : entries) {
            if ("false".equals(e[2])) {
                failCount++;
            }
        }
        return failCount;
    }
}
