package uk.co.automatictester.concurrency.custom.synchronizers;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class ConditionQueueCompositeCounter extends CompositeCounter {

    public synchronized void incrementAlpha() throws InterruptedException {
        while (!canStartAlpha()) {
            wait();
        }
        doIncrementAlpha();
        notifyAll();
    }

    public synchronized void incrementBeta() throws InterruptedException {
        while (!canStartBeta()) {
            wait();
        }
        doIncrementBeta();
        notifyAll();
    }

    public synchronized void decrementAlpha() {
        doDecrementAlpha();
        if (getAlpha() == 0) {
            notifyAll();
        }
    }

    public synchronized void decrementBeta() {
        doDecrementBeta();
        if (getBeta() == 0) {
            notifyAll();
        }
    }
}
