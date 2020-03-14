package uk.co.automatictester.java9;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
public class StreamTest {

    @Test
    public void optionalStream() {
        List<Optional<Integer>> listOfOptionals = List.of(
                Optional.of(1), Optional.empty(), Optional.of(2), Optional.of(3)
        );

        listOfOptionals.stream()
                .flatMap(Optional::stream) // new in Java 9
                .forEach(i -> log.info("{}", i)); // 1 2 3
    }

    @Test
    public void takeWhile() {
        List<Integer> orderedList = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        orderedList.stream()
                .takeWhile(i -> i < 6) // new in Java 9, useful with ordered streams
                .forEach(i -> log.info("{}", i)); // 1 2 3 4 5
    }

    @Test
    public void dropWhile() {
        List<Integer> orderedList = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        orderedList.stream()
                .dropWhile(i -> i < 6) // new in Java 9, useful with ordered streams
                .forEach(i -> log.info("{}", i)); // 6 7 8 9 10
    }

    @Test
    public void iterateJava9() {
        Stream
                .iterate(1, i -> i <= 10, i -> i + 1) // new overloaded version
                .forEach(i -> log.info("{}", i)); // 1 2 3 4 5 6 7 8 9 10
    }

    @Test
    public void iterateJava8() {
        Stream
                .iterate(1, i -> i + 1)
                .limit(10)
                .forEach(i -> log.info("{}", i)); // 1 2 3 4 5 6 7 8 9 10
    }
}
