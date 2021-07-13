package ru.job4j.tracker.gc;

/*
 * Демонстрация garbage collector
 * реализация
 * считаем обычный объект и объект без полей
 * предмет подсчета выделенные под данные объекты объемы памяти
 * @author Kolesnikov Evgeniy (evgeniysanich@mail.ru)
 * @version 1.0
 */

import ru.job4j.tracker.store.MemTracker;

public class GCTest {

    private static final long KB = 1000;
    //private static final long MB = KB * KB;
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
        for (int i = 0; i < 1000000; i++) {
            new MemTracker();
        }
        //System.out.printf("Size of object without fields is %s%n", sizeOf(new MemTracker()));
        //System.out.printf("Size of object with fields is %s%n", sizeOf(new User("N5", 5, "SP5")));
        //System.gc();
        //info();
    }
}

