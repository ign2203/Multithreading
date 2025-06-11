package org.example;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockCounter implements SiteVisitCounter {
    private int count = 0;
    private ReentrantLock lock = new ReentrantLock();

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public void increment() {
        lock.lock();
        try {
            count++;
        } finally {
            lock.unlock();
        }
    }
}
