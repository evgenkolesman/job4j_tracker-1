package ru.job4j.tracker.store;

import org.junit.Test;
import ru.job4j.tracker.model.Item;
import ru.job4j.tracker.proxy.ConnectionRollback;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;

import java.util.Properties;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SqlTrackerTest {

    public Connection init() {
        try (InputStream in = SqlTrackerTest.class.getClassLoader().getResourceAsStream("app.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("driver-class-name"));
            return DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password")
            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Test
    public void createItem() throws Exception {
        try (SqlTracker tracker = new SqlTracker(ConnectionRollback.create(this.init()))) {
            tracker.add(new Item("name"));
            assertThat(tracker.findByName("name").size(), is(1));
        }
    }

    @Test
    public void testReplace() throws Exception {
        try (SqlTracker tracker = new SqlTracker(ConnectionRollback.create(this.init()))) {
            Item item = new Item("name");
            tracker.add(item);
            tracker.replace(item.getId(), new Item("null"));
            assertThat(tracker.findById(item.getId()).getName(), is("null"));
        }
    }

    @Test
    public void testDelete()  throws Exception {
        try (SqlTracker tracker = new SqlTracker(ConnectionRollback.create(this.init()))) {
            Item item = new Item("name");
            tracker.add(item);
            tracker.delete(item.getId());
            assertThat(tracker.findById(item.getId()), is(nullValue()));
        }
    }

    @Test
    public void testFindById() throws Exception {
        try (SqlTracker tracker = new SqlTracker(ConnectionRollback.create(this.init()))) {
            Item item = new Item("name");
            tracker.add(item);
            assertThat(tracker.findById(item.getId()), is(item));
        }
    }
}