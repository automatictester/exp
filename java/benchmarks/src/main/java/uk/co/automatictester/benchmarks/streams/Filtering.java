package uk.co.automatictester.benchmarks.streams;

import org.apache.commons.lang3.RandomStringUtils;
import org.openjdk.jmh.annotations.*;
import uk.co.automatictester.lightning.core.readers.JmeterBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Filtering {

    @State(Scope.Thread)
    public static class FilteringState {

        private List<JmeterBean> entries = new ArrayList<>();
        private Random random = new Random();

        private Supplier<JmeterBean> supplier = () -> {
            String label = RandomStringUtils.randomAlphabetic(5, 25);
            String elapsed = RandomStringUtils.randomNumeric(1, 4);
            String success = String.valueOf(random.nextBoolean());
            String timeStamp = RandomStringUtils.randomNumeric(13, 13);
            return new JmeterBean(label, elapsed, success, timeStamp);
        };

        @Setup
        public void dataGen() {
            entries = Stream
                    .generate(supplier)
                    .limit(3_000_000)
                    .collect(Collectors.toList());
        }
    }

    @Benchmark
    @Fork(value = 1)
    @Warmup(iterations = 5, time = 5)
    @Measurement(iterations = 2, time = 20)
    @BenchmarkMode(Mode.AverageTime)
    public int filterStream(FilteringState state) {
        return (int) state.entries.stream()
                .filter(bean -> !bean.isSuccess())
                .count();
    }

    @Benchmark
    @Fork(value = 1)
    @Warmup(iterations = 5, time = 5)
    @Measurement(iterations = 2, time = 20)
    @BenchmarkMode(Mode.AverageTime)
    public int filterLoop(FilteringState state) {
        int failCount = 0;
        for (JmeterBean bean : state.entries) {
            if (!bean.isSuccess()) {
                failCount++;
            }
        }
        return failCount;
    }
}
