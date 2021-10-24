package ru.job4j.tracker.gc;

/**
  * Демонстрация garbage collector
  * реализация
  * считаем обычный объект и объект без полей
  * предмет подсчета выделенные под данные объекты объемы памяти
  * VM options: -XX:+UseG1GC -Xlog:gc*
  * @author Kolesnikov Evgeniy (evgeniysanich@mail.ru)
  * @version 1.0
 */

import ru.job4j.tracker.model.Item;
import ru.job4j.tracker.store.MemTracker;

public class GCTest {

    private static final long KB = 1000;
    private static final Runtime ENVIRONMENT = Runtime.getRuntime();

    public static void info() {
        final long freeMemory = ENVIRONMENT.freeMemory();
        final long totalMemory = ENVIRONMENT.totalMemory();
        final long maxMemory = ENVIRONMENT.maxMemory();
        System.out.println("=== Environment state ===");
        System.out.printf("Free: %d%n", freeMemory / KB);
        System.out.printf("Total: %d%n", totalMemory / KB);
        System.out.printf("Max: %d%n", maxMemory / KB);
    }

    public static void main(String[] args) {
        info();
        MemTracker memTracker = new MemTracker();
        for (int i = 1; i < 10000000; i++) {
            memTracker.add(new Item(String.valueOf(i)));
        }
        System.gc();
        info();
    }
}

