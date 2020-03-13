package uk.co.automatictester.java10;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@Slf4j
public class VarTest {

    /**
     * var - available only for local variables with the initializer
     */
    @Test
    public void var() {
        var x = "string";
        assertThat(x instanceof String, is(true));

        Map<Integer, Integer> oldMap = Map.of(1, 11, 2, 22, 3, 33);
        var map = Map.of(1, 11, 2, 22, 3, 33);
        assertThat(map.size(), is(3));

        for (var i = 0; i < 10; i++) {
            log.info("{}", i);
        }
    }
}
