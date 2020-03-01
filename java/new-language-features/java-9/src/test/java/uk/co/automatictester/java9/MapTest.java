package uk.co.automatictester.java9;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class MapTest {

    @Test
    public void java9() {
        Map<Integer, Integer> map = Map.of(1, 11, 2, 22);
        log.info("{}", map);
    }

    @Test
    public void java8() {
        Map<Integer, Integer> map = new HashMap<>() {{
            put(1, 11);
            put(2, 22);
        }};
        log.info("{}", map);
    }
}
