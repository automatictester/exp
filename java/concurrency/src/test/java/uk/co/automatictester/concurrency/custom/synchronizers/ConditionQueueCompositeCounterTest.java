package uk.co.automatictester.concurrency.custom.synchronizers;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.CountDownLatch;

/*
 * Requirements:
 * - alpha cannot execute in parallel with beta
 * - execution order is not important
 */
public class ConditionQueueCompositeCounterTest extends CompositeCounterTest {

    @BeforeMethod
    public void setup() {
        latch = new CountDownLatch(threads);
        mutex = new ConditionQueueCompositeCounter();
    }

    /*
     * Sample output:
     * [INFO] ALPHA: phase started
     * [INFO] BETA:  phase started
     * [INFO] ALPHA: phase started
     * [INFO] BETA:  phase started
     * [INFO] ALPHA: phase started
     * [INFO] ALPHA: phase started
     * [INFO] BETA:  phase started
     */
    @Test(invocationCount = 5)
    public void trialMutex() throws InterruptedException {
        doTrialMutex();
    }
}

