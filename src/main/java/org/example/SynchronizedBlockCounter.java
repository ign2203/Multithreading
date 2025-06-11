package org.example;


public class SynchronizedBlockCounter implements SiteVisitCounter {
    private static int count = 0;
    private final Object lock = new Object(); // Один общий объект для синхронизации

    @Override
    public int getCount() {
        return count;//
    }

    @Override
    public void increment() {
        synchronized (lock) {
            count++;
        }
    }
}