package org.example;

public class Main {
    public static void main(String[] args) {

        // UnsynchronizedCounter: race condition
        UnsynchronizedCounter unsynchronizedCounter = new UnsynchronizedCounter();
        MultithreadingSiteVisitor visitor1 = new MultithreadingSiteVisitor(unsynchronizedCounter);
        visitor1.visitMultithread(100000);
        visitor1.waitUntilAllVisited();
        System.out.println("Общее время обработки (без синхронизации): " + visitor1.getTotalTimeOfHandling() + " секунд");
        System.out.println("Текущее значение счетчика: " + unsynchronizedCounter.getCount());
        /*

Race condition (гонка данных) возникает, когда несколько потоков одновременно обращаются к одной переменной.
Операция инкремента состоит из трёх шагов: чтение — изменение — запись.
Если потоки выполняют эти шаги параллельно, они могут "потерять" часть инкрементов.
Проблема проявляется особенно сильно при большом количестве потоков (например, 100_000).

Почему именно так? Потому что потоки не синхронизированы, и один поток может перезаписать значение, пока другой его ещё не закончил изменять.
Результат — значение счётчика меньше ожидаемого.

По производительности: несмотря на некорректность, UnsynchronizedCounter работает очень быстро, потому что не тратит время на синхронизацию.
*/


        // VolatileCounter: видимость есть, атомарности нет
        VolatileCounter volatileCounter = new VolatileCounter();
        MultithreadingSiteVisitor visitor2 = new MultithreadingSiteVisitor(volatileCounter);
        visitor2.visitMultithread(100000);
        visitor2.waitUntilAllVisited();
        System.out.println("Общее время обработки volatile: " + visitor2.getTotalTimeOfHandling() + " секунд");
        System.out.println("Текущее значение счетчика: " + volatileCounter.getCount());

        /*
Ключевое отличие volatile от других механизмов синхронизации — он не делает операцию атомарной.
Инкремент — это составная операция (чтение → увеличение → запись), и volatile не защищает от гонки данных.
Хотя volatile обеспечивает, что потоки читают/пишут значение из общей памяти, это не исключает ситуацию, когда два потока читают одно и то же значение до того, как один из них успел записать новое.
В результате, при большом количестве потоков итоговое значение счётчика может быть значительно меньше ожидаемого.
Volatile полезен для управления флагами, например, когда один поток должен остановить другой (isRunning, isStopped и т.д.).
По производительности: чуть медленнее, чем UnsynchronizedCounter, потому что требует постоянной работы с общей памятью. Но всё ещё быстрее, чем полноценные блокировки (synchronized / ReentrantLock).
*/


        // AtomicIntegerCounter: лучшая производительность и атомарность
        AtomicIntegerCounter atomicIntegerCounter = new AtomicIntegerCounter();
        MultithreadingSiteVisitor visitor3 = new MultithreadingSiteVisitor(atomicIntegerCounter);
        visitor3.visitMultithread(100000);
        visitor3.waitUntilAllVisited();
        System.out.println("Общее время обработки Atomic: " + visitor3.getTotalTimeOfHandling() + " секунд");
        System.out.println("Текущее значение счетчика: " + atomicIntegerCounter.getCount());
        /*
        AtomicInteger — это "умная" переменная, которая сама следит, чтобы не было гонки,
        и аккуратно перезаписывает своё значение только если никто другой его не изменил.
        /*
AtomicInteger обеспечивает как видимость (через volatile), так и атомарность операций (через механизм CAS — Compare-And-Swap).
Метод incrementAndGet() реализован так, что при попытке инкремента, если переменная была изменена другим потоком, операция повторяется до тех пор, пока не произойдёт успешная запись нового значения.

Это делает AtomicInteger особенно эффективным для реализации счётчиков в многопоточной среде:
он точен, безопасен и работает быстрее большинства других синхронизирующих подходов (например, synchronized или ReentrantLock), потому что не блокирует потоки.
*/



        // SynchronizedBlockCounter: простая блокировка
        SynchronizedBlockCounter synchronizedBlockCounter = new SynchronizedBlockCounter();
        MultithreadingSiteVisitor visitor4 = new MultithreadingSiteVisitor(synchronizedBlockCounter);
        visitor4.visitMultithread(100000);
        visitor4.waitUntilAllVisited();
        System.out.println("Общее время обработки synchronized: " + visitor4.getTotalTimeOfHandling() + " секунд");
        System.out.println("Текущее значение счетчика: " + synchronizedBlockCounter.getCount());
        /*
        /*
Синхронизация с помощью synchronized создает критическую секцию — участок кода, доступ к которому может получить только один поток в момент времени.
Работает это через механизм мониторов: каждый объект в Java может выступать в роли "монитора", и когда поток входит в synchronized-блок, он захватывает монитор.
Другие потоки ждут, пока монитор освободится.

Поскольку потоки могут блокироваться и "пробуждаться", это накладывает дополнительные издержки, особенно при большом количестве конкуренции за ресурс.
Поэтому synchronized работает корректно, но медленнее, чем подходы без блокировок (например, AtomicInteger).
*/

        // ReentrantLockCounter: гибкая, но шумная синхронизация
        ReentrantLockCounter reentrantLockCounter = new ReentrantLockCounter();
        MultithreadingSiteVisitor visitor5 = new MultithreadingSiteVisitor(reentrantLockCounter);
        visitor5.visitMultithread(100000);
        visitor5.waitUntilAllVisited();
        System.out.println("Общее время обработки ReentrantLock: " + visitor5.getTotalTimeOfHandling() + " секунд");
        System.out.println("Текущее значение счетчика: " + reentrantLockCounter.getCount());
        /*
        🐢 Почему может быть медленнее, чем synchronized
У ReentrantLock больше логики: очередь, прерывание, флаги, проверка состояния
synchronized в новых JVM (начиная с Java 6+) сильно оптимизирован (biased locking, lightweight locking и др.)
То есть synchronized в большинстве случаев быстрее, если не нужно ничего сложного
         */
    }
}