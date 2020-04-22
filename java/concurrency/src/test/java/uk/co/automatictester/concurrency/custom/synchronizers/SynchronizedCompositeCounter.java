package uk.co.automatictester.concurrency.custom.synchronizers;

import lombok.extern.slf4j.Slf4j;

import javax.annotation.concurrent.ThreadSafe;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@ThreadSafe
class SynchronizedCompositeCounter extends CompositeCounter {

    public void incrementAlpha() {
        while (true) {
            synchronized (this) {
                if (canStartAlpha()) {
                    doIncrementAlpha();
                    return;
                }
            }
            // never sleep with intrinsic or explicit lock acquired
            sleep();
        }
    }

    public void incrementBeta() {
        while (true) {
            synchronized (this) {
                if (canStartBeta()) {
                    doIncrementBeta();
                    return;
                }
            }
            sleep();
        }
    }

    public synchronized void decrementAlpha() {
        doDecrementAlpha();
    }

    public synchronized void decrementBeta() {
        doDecrementBeta();
    }

    private void sleep() {
        int time = ThreadLocalRandom.current().nextInt(0, 2);
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
