package org.example;

public class Main {
    public static void main(String[] args) {

        UnsynchronizedCounter unsynchronizedCounter = new UnsynchronizedCounter();
        MultithreadingSiteVisitor visitor1 = new MultithreadingSiteVisitor(unsynchronizedCounter);
        visitor1.visitMultithread(100000);
        visitor1.waitUntilAllVisited();
        System.out.println("Общее время обработки (без синхронизации): " + visitor1.getTotalTimeOfHandling() + " секунд");
        System.out.println("Текущее значение счетчика: " + unsynchronizedCounter.getCount());

        VolatileCounter volatileCounter = new VolatileCounter();
        MultithreadingSiteVisitor visitor2 = new MultithreadingSiteVisitor(volatileCounter);
        visitor2.visitMultithread(100000);
        visitor2.waitUntilAllVisited();
        System.out.println("Общее время обработки volatile: " + visitor2.getTotalTimeOfHandling() + " секунд");
        System.out.println("Текущее значение счетчика: " + volatileCounter.getCount());

        AtomicIntegerCounter atomicIntegerCounter = new AtomicIntegerCounter();
        MultithreadingSiteVisitor visitor3 = new MultithreadingSiteVisitor(atomicIntegerCounter);
        visitor3.visitMultithread(100000);
        visitor3.waitUntilAllVisited();
        System.out.println("Общее время обработки Atomic: " + visitor3.getTotalTimeOfHandling() + " секунд");
        System.out.println("Текущее значение счетчика: " + atomicIntegerCounter.getCount());

        SynchronizedBlockCounter synchronizedBlockCounter = new SynchronizedBlockCounter();
        MultithreadingSiteVisitor visitor4 = new MultithreadingSiteVisitor(synchronizedBlockCounter);
        visitor4.visitMultithread(100000);
        visitor4.waitUntilAllVisited();
        System.out.println("Общее время обработки synchronized: " + visitor4.getTotalTimeOfHandling() + " секунд");
        System.out.println("Текущее значение счетчика: " + synchronizedBlockCounter.getCount());

        ReentrantLockCounter reentrantLockCounter = new ReentrantLockCounter();
        MultithreadingSiteVisitor visitor5 = new MultithreadingSiteVisitor(reentrantLockCounter);
        visitor5.visitMultithread(100000);
        visitor5.waitUntilAllVisited();
        System.out.println("Общее время обработки ReentrantLock: " + visitor5.getTotalTimeOfHandling() + " секунд");
        System.out.println("Текущее значение счетчика: " + reentrantLockCounter.getCount());
    }
}