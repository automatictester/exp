package uk.co.automatictester.streams.api;

import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Streams {

    @Test
    public void reduceA() {
        Stream<Integer> s = Stream.of(1, 2, 3);
        System.out.println(s.reduce(0, (x, y) -> x + y));
    }

    @Test
    public void sum() {
        Stream<Integer> s = Stream.of(1, 2, 3);
        System.out.println(s.mapToInt(x -> x).sum());
    }

    @Test
    public void reduceB() {
        Stream<String> s = Stream.of("h", "a", "h", "a");
        System.out.println(s.reduce("", (x, y) -> x + y));
    }

    @Test
    public void fibonacci() {
        Stream.iterate(new int[]{1, 1}, x -> new int[]{x[1], x[0] + x[1]})
                .limit(6)
                .collect(Collectors.toList())
                .forEach(x -> System.out.println(x[1]));
    }

    @Test
    public void even() {
        Stream.iterate(2, x -> x + 2)
                .limit(10)
                .forEach(System.out::println);
    }

    @Test
    public void odd() {
        Stream.iterate(1, x -> x + 2)
                .limit(10)
                .forEach(System.out::println);
    }

    @Test
    public void smarterOdd() {
        IntStream.rangeClosed(1, 20)
                .filter(x -> x % 2 == 1)
                .forEach(System.out::println);
    }

    @Test
    public void flatMap() {
        List<Integer> l1 = Arrays.asList(1, 2, 3);
        List<Integer> l2 = Arrays.asList(4, 5, 6);
        Stream<List<Integer>> s = Stream.of(l1, l2);
        s.flatMap(list -> list.stream()).forEach(System.out::println);
    }

    @Test
    public void joiningCollectorStream() {
        String s = Stream.iterate(1, i -> ++i)
                .limit(9)
                .map(i -> "" + i)
                .collect(Collectors.joining());
        System.out.println(s);
    }

    @Test
    public void joiningCollectorIntStream() {
        String s = IntStream.rangeClosed(1, 9)
                .mapToObj(i -> "" + i)
                .collect(Collectors.joining());
        System.out.println(s);
    }

    @Test
    public void groupCountIntoMap() {
        Stream<String> names = Stream.of("Anna", "Mona", "Anna", "Hanna");

        Map.Entry<String, Long> mostPopularName = names
                .parallel()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .parallelStream()
                .max(Map.Entry.comparingByValue())
                .get();

        System.out.println(mostPopularName.getKey() + ": " + mostPopularName.getValue());
    }
}
