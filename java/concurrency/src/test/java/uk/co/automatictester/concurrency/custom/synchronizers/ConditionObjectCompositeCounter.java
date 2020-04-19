package uk.co.automatictester.concurrency.custom.synchronizers;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
class ConditionObjectCompositeCounter extends CompositeCounter {

    private final Lock lock = new ReentrantLock();
    private final Condition canStartAlpha = lock.newCondition();
    private final Condition canStartBeta = lock.newCondition();

    public void incrementAlpha() throws InterruptedException {
        lock.lock();
        try {
            while (!canStartAlpha()) {
                canStartAlpha.await();
            }
            doIncrementAlpha();
            canStartAlpha.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public void incrementBeta() throws InterruptedException {
        lock.lock();
        try {
            while (!canStartBeta()) {
                canStartBeta.await();
            }
            doIncrementBeta();
            canStartBeta.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public void decrementAlpha() {
        lock.lock();
        try {
            doDecrementAlpha();
            if (getAlpha() == 0) {
                canStartBeta.signalAll();
            }
        } finally {
            lock.unlock();
        }
    }

    public void decrementBeta() {
        lock.lock();
        try {
            doDecrementBeta();
            if (getBeta() == 0) {
                canStartAlpha.signalAll();
            }
        } finally {
            lock.unlock();
        }
    }
}
