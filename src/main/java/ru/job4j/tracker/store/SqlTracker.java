package ru.job4j.tracker.store;

import ru.job4j.tracker.model.Item;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/*
 * продолжение проекта с tracker, взаимодействие с БД
 * опираемся на интерфейс Store
 * @author Kolesnikov Evgeniy (evgeniysanich@mail.ru)
 * @version 1.0
 */

public class SqlTracker implements Store {
    private Connection cn;

    public void init() {
        try (InputStream in = SqlTracker.class.getClassLoader().getResourceAsStream("app.properties")) {
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
        try (PreparedStatement statement =
                     cn.prepareStatement("insert into item(id, name) values (?, ?)")) {
            statement.setString(1, item.getName());
            statement.setInt(2, Integer.parseInt(item.getId()));
            statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        item.setId(String.format("select id from items where name = %s", item.getName()));
        return item;
    }

    @Override
    public boolean replace(String id, Item item) {
        boolean flag = false;
        try (Statement statement = cn.createStatement()) {
            flag = statement.execute((String.format(
                    "update items set name %s where id = %s ", item.getName(), id)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }


    @Override
    public boolean delete(String id) {
        boolean flag = false;
        try (Statement statement = cn.createStatement()) {
            flag = statement.execute((String.format("delete from items where id = %s ", id)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public List<Item> findAll() {
        List<Item> values = new ArrayList<>();
        try (PreparedStatement statement = cn.prepareStatement("select * from items")) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    values.add(new Item(
                            resultSet.getString("id"),
                            resultSet.getString("name")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return values;
    }

    @Override
    public List<Item> findByName(String key) {
        List<Item> values = new ArrayList<>();
        try (PreparedStatement statement = cn.prepareStatement(String.format(
                "select name from items where %s", key))) {
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    values.add(new Item(
                            result.getString(String.format(key)
                            )));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return values;
    }

    @Override
    public Item findById(String id) {
        Item result1 = null;
        try (PreparedStatement statement = cn.prepareStatement("select * from items")) {
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    result1 = new Item(result.getString(String.format(id)));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result1;
    }

    @Override
    public void close() throws Exception {
        if (cn != null) {
            cn.close();
        }
    }
}
