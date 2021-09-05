package ru.job4j.tracker.trackerformockito.action;

import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.trackerformockito.store.Store;

public interface UserAction {
    String name();
    boolean execute(Input input, Store tracker);

}