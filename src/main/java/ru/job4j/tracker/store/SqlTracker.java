package ru.job4j.tracker.store;

import ru.job4j.tracker.model.Item;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

public class SqlTracker implements Store {
    private Connection cn;

    public void init(){
        try(InputStream in = SqlTracker.class.getClassLoader().getResourceAsStream("app.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("driver-class-name"));
            cn = DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password")
            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Item add(Item item) throws SQLException {
        writeSQL(String.format("insert into item(id, name) values(%s, %s);", item.toString()));
        item.setId(String.format("select id from items where name = %s", item.getName()));
        return item;
    }

    @Override
    public boolean replace(String id, Item item) {
        boolean flag;
        writeSQL(String.format("update items set "));
        return
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public List<Item> findAll() {
        return null;
    }

    @Override
    public List<Item> findByName(String key) {
        return null;
    }

    @Override
    public Item findById(String id) {
        return null;
    }

    @Override
    public void close() throws Exception {
        if (cn != null) {
            cn.close();
        }
    }

    public void writeSQL(String sql) throws SQLException {
        try (Statement statement = cn.createStatement()) {
            statement.execute(sql);
        }
    }
}
