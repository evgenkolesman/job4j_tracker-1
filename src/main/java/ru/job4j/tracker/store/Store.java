package ru.job4j.tracker.store;

import ru.job4j.tracker.model.Item;

import java.sql.SQLException;
import java.util.List;

/*
 * Проект трэккер интерфейс Store
 * @author Kolesnikov Evgeniy (evgeniysanich@mail.ru)
 * @version 1.0
 */
public interface Store extends AutoCloseable {
    void init();
    Item add(Item item) throws SQLException;
    boolean replace(Integer id, Item item);
    boolean delete(Integer id);
    List<Item> findAll();
    List<Item> findByName(String key);
    Item findById(Integer id);
}