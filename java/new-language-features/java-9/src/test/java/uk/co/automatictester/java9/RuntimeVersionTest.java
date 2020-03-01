package uk.co.automatictester.java9;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

@Slf4j
public class RuntimeVersionTest {

    @Test
    public void test() {
        int major = Runtime.version().major();
        int build = Runtime.version().build().orElseThrow(RuntimeException::new);
        log.info("Major: {}, Build: {}", major, build);
    }
}
