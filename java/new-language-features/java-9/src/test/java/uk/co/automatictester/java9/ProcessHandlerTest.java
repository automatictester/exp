package uk.co.automatictester.java9;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import java.util.Arrays;

@Slf4j
public class ProcessHandlerTest {

    @Test
    public void test() {
        ProcessHandle ph = ProcessHandle.current();

        long pid = ph.pid();
        long ppid = ph.parent().orElseThrow(RuntimeException::new).pid();
        log.info("PID: {}, PPID: {}", pid, ppid);

        ph.info().user().ifPresent(user -> {
            log.info("User: {}", user);
        });

        ph.info().command().ifPresent(command -> {
            log.info("Command: {}", command);
        });

        ph.info().commandLine().ifPresent(commandLine -> {
            log.info("Command line: {}", commandLine);
        });

        ph.info().arguments().ifPresent(args -> {
            log.info("Args: {}", Arrays.asList(args));
        });
    }
}
