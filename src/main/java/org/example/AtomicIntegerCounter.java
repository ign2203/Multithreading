package org.example;


import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerCounter implements SiteVisitCounter {
    private AtomicInteger count = new AtomicInteger();

    @Override
    public int getCount() {
        return count.get();
    }

    @Override
    public void increment() {
        count.incrementAndGet();
    }
}