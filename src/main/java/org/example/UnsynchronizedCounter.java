package org.example;

public class UnsynchronizedCounter implements SiteVisitCounter {
    private int count = 0;

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public void increment() {
        count++;
    }
}