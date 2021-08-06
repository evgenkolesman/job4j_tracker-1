package ru.job4j.tracker.store;

import ru.job4j.tracker.model.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
 * Проект трэккер метод MemTracker интерфейс Store
 * @author Kolesnikov Evgeniy (evgeniysanich@mail.ru)
 * @version 1.0
 */
public class MemTracker implements Store {

    private final List<ru.job4j.tracker.model.Item> items = new ArrayList<>();

    @Override
    public void init() {

    }

    @Override
    public Item add(Item item) {
        item.setId(generateId());
        items.add(item);
        return item;
    }

    private Integer generateId() {
        Random rm = new Random();
        return rm.nextInt();
    }

    @Override
    public List<Item> findAll() {
        return items;
    }

    @Override
    public Item findById(Integer id) {
        int index = id;
        return index != -1 ? items.get(index) : null;
    }

    @Override
    public List<Item> findByName(String key) {
        List<Item> result = new ArrayList<>();
        for (Item item : items) {
            if (key.equals(item.getName())) {
                result.add(item);
            }
        }
        return result;
    }

    @Override
    public boolean replace(Integer id, Item item) {
        int index = id;
        if (index == -1) {
            return false;
        }
        item.setId(id);
        items.set(index, item);
        return true;
    }

    @Override
    public boolean delete(Integer id) {
        int index = id;
        if (index == -1) {
            return false;
        }
        items.remove(index);
        return true;
    }

    private int indexOf(String id) {
        int index = -1;
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId().equals(id)) {
                index = i;
                break;
            }
        }
        return index;
    }

    @Override
    public void close() throws Exception {

    }
}
