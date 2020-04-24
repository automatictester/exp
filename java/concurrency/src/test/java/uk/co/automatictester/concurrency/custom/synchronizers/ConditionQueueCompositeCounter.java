package uk.co.automatictester.concurrency.custom.synchronizers;

import lombok.extern.slf4j.Slf4j;

import javax.annotation.concurrent.ThreadSafe;

@Slf4j
@ThreadSafe
class ConditionQueueCompositeCounter extends CompositeCounter {

    @Override
    public synchronized void incrementAlpha() throws InterruptedException {
        while (!canStartAlpha()) {
            // must be called from synchronized method or block,
            // it releases the lock prior to waiting and
            // reacquires the lock prior to returning from the method
            wait();
        }
        doIncrementAlpha();
        // must be called from synchronized method or block
        notifyAll();
    }

    @Override
    public synchronized void incrementBeta() throws InterruptedException {
        while (!canStartBeta()) {
            wait();
        }
        doIncrementBeta();
        notifyAll();
    }

    @Override
    public synchronized void decrementAlpha() {
        doDecrementAlpha();
        if (getAlpha() == 0) {
            notifyAll();
        }
    }

    @Override
    public synchronized void decrementBeta() {
        doDecrementBeta();
        if (getBeta() == 0) {
            notifyAll();
        }
    }
}
