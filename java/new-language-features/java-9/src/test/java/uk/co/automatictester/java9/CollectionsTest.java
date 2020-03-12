package uk.co.automatictester.java9;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import java.util.*;

@Slf4j
public class CollectionsTest {

    @Test
    public void setJava9() {
        Set<Integer> set = Set.of(1, 2, 3);
        log.info("{}", set);
    }

    @Test
    public void setJava8() {
        Set<Integer> set = new HashSet<>(Arrays.asList(1, 2, 3));
        log.info("{}", set);
    }

    @Test
    public void mapJava9() {
        Map<Integer, Integer> map = Map.of(1, 11, 2, 22);
        log.info("{}", map);
    }

    @Test
    public void mapJava8() {
        Map<Integer, Integer> map = new HashMap<>() {{
            put(1, 11);
            put(2, 22);
        }};
        log.info("{}", map);
    }

    @Test
    public void listJava9() {
        List<Integer> list = List.of(1, 2, 3);
        log.info("{}", list);
    }

    @Test
    public void listJava8() {
        List<Integer> list = Arrays.asList(1, 2, 3);
        log.info("{}", list);
    }
}
