package uk.co.automatictester.java9;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Optional;

@Slf4j
public class OptionalStreamTest {

    @Test
    public void test() {
        List<Optional<Integer>> listOfOptionals = List.of(
                Optional.of(1), Optional.empty(), Optional.of(2), Optional.of(3)
        );

        listOfOptionals.stream()
                .flatMap(Optional::stream) // new in Java 9
                .forEach(i -> log.info("{}", i));
    }
}
