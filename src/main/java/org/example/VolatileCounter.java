package org.example;

public class VolatileCounter implements SiteVisitCounter {
    private volatile int count = 0;

    @Override
    public int getCount() {
        return count;//
    }

    @Override
    public void increment() {
        count++;
    }
}