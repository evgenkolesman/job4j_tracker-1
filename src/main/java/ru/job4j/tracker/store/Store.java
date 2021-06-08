package ru.job4j.tracker.store;

import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import ru.job4j.tracker.model.Item;

import java.util.List;

public interface Store extends AutoCloseable {
    void init();
    AbstractReadWriteAccess.Item add(Item item);
    boolean replace(String id, Item item);
    boolean delete(String id);
    List<Item> findAll();
    List<Item> findByName(String key);
    Item findById(String id);
}
