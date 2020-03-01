package uk.co.automatictester.java9;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class ListTest {

    @Test
    public void java9() {
        List<Integer> list = List.of(1, 2, 3);
        log.info("{}", list);
    }

    @Test
    public void java8() {
        List<String> list = Arrays.asList("foo", "bar", "baz");
        log.info("{}", list);
    }
}
