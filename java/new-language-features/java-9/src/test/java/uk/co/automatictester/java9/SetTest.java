package uk.co.automatictester.java9;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class SetTest {

    @Test
    public void java9() {
        Set<Integer> set = Set.of(1, 2, 3);
        log.info("{}", set);
    }

    @Test
    public void java8() {
        Set<Integer> set = new HashSet<>(Arrays.asList(1, 2, 3));
        log.info("{}", set);
    }
}
