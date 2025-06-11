package org.example;

import java.util.ArrayList;
import java.util.List;

public class MultithreadingSiteVisitor {//
    private final SiteVisitCounter counter;
    private final List<Thread> threads = new ArrayList<>();
    private long totalTimeOfHandling;

    public MultithreadingSiteVisitor(SiteVisitCounter counter) {
        this.counter = counter;
    }

    public void visitMultithread(int numOfThreads) {
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < numOfThreads; i++) {
            Thread thread = new Thread(counter::increment);
            threads.add(thread);
            thread.start();
        }

        long endTime = System.currentTimeMillis();
        totalTimeOfHandling = (endTime - startTime) / 1000;
    }

    public void waitUntilAllVisited() {
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public long getTotalTimeOfHandling() {
        return totalTimeOfHandling;
    }
}