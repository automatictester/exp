package uk.co.automatictester.java10;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@SuppressWarnings("ALL")
@Slf4j
public class VarTest {

    /**
     * var - available only for local variables with the initializer
     */

    @Test
    public void simpleExample() {
        var x = "string";
        assertThat(x instanceof String, is(true));
    }

    @Test
    public void genericsExample() {
        Map<Integer, Integer> oldMap = Map.of(1, 11, 2, 22, 3, 33);
        var newMap = Map.of(1, 11, 2, 22, 3, 33);
    }

    @Test
    public void loopCounterExample() {
        for (var i = 0; i < 10; i++) {
            log.info("{}", i);
        }
    }
}
