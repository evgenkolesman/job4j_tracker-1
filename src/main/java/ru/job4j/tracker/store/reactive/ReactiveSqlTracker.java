package ru.job4j.tracker.store.reactive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.tracker.model.Item;
import ru.job4j.tracker.store.SqlTracker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ReactiveSqlTracker extends SqlTracker {

    private static final Logger LOG = LoggerFactory.getLogger(ReactiveSqlTracker.class.getName());

    public ReactiveSqlTracker(Connection cn) {
        super(cn);
    }

    public List<Item> findAll(Observe<Item> observe) {
        LOG.debug("Find all reactive all");
        List<Item> result = new ArrayList<>();
        try (PreparedStatement ps = cn.prepareStatement("select * from items;")) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Item item = new Item(
                            String.valueOf(rs.getInt("id")),
                            rs.getString("name")
                    );
                    observe.receive(item);
                    result.add(item);
                }
            }
            LOG.debug("Selecting complete. Found items: {}", result.size());
        } catch (Exception e) {
            LOG.error("Something went wrong", e);
        }
        LOG.debug("Found {} items", result.size());
        return result;
    }

}
