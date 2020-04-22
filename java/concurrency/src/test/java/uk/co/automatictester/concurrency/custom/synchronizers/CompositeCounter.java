package uk.co.automatictester.concurrency.custom.synchronizers;

import lombok.extern.slf4j.Slf4j;

import javax.annotation.concurrent.ThreadSafe;

@Slf4j
@ThreadSafe
public abstract class CompositeCounter {

    private volatile int alphaCount = 0;
    private volatile int betaCount = 0;

    public abstract void incrementAlpha() throws InterruptedException;

    public abstract void incrementBeta() throws InterruptedException;

    public abstract void decrementAlpha();

    public abstract void decrementBeta();

    protected synchronized void doIncrementAlpha() {
        if (alphaCount == 0) {
            log.debug("ALPHA: phase started");
        }
        alphaCount++;
        log.debug("ALPHA: {} - BETA: {}", alphaCount, betaCount);
    }

    protected synchronized void doIncrementBeta() {
        if (betaCount == 0) {
            log.debug("BETA:  phase started");
        }
        betaCount++;
        log.debug("ALPHA: {} - BETA: {}", alphaCount, betaCount);
    }

    protected synchronized void doDecrementAlpha() {
        alphaCount--;
    }

    protected synchronized void doDecrementBeta() {
        betaCount--;
    }

    protected boolean canStartAlpha() {
        return betaCount == 0;
    }

    protected boolean canStartBeta() {
        return alphaCount == 0;
    }

    protected int getAlpha() {
        return alphaCount;
    }

    protected int getBeta() {
        return betaCount;
    }
}
