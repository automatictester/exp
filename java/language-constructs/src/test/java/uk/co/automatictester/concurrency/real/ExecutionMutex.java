package uk.co.automatictester.concurrency.real;

import lombok.extern.slf4j.Slf4j;

/**
 * For use with ConcurrentTypeExecution example
 */
@Slf4j
public class ExecutionMutex {

    private int alphaCount = 0;
    private int betaCount = 0;

    public synchronized boolean tryIncrementAlpha() {
        if (betaCount == 0) {
            if (alphaCount == 0) {
                log.info("ALPHA: phase started");
            }
            alphaCount++;
            log.debug("ALPHA: {}", alphaCount);
            return true;
        } else {
            return false;
        }
    }

    public synchronized boolean tryIncrementBeta() {
        if (alphaCount == 0) {
            if (betaCount == 0) {
                log.info("BETA:  phase started");
            }
            betaCount++;
            log.debug("BETA:  {}", betaCount);
            return true;
        } else {
            return false;
        }
    }

    public synchronized void decrementAlpha() {
        alphaCount--;
    }

    public synchronized void decrementBeta() {
        betaCount--;
    }
}
