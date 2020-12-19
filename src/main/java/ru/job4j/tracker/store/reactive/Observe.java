package ru.job4j.tracker.store.reactive;

public interface Observe<T> {
    void receive(T model);
}
