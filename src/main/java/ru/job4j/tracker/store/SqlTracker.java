package ru.job4j.tracker.store;

import ru.job4j.tracker.model.Item;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/*
 * Продолжение проекта с tracker, взаимодействие с БД
 * опираемся на интерфейс Store
 * @author Kolesnikov Evgeniy (evgeniysanich@mail.ru)
 * @version 1.0
 */

public class SqlTracker implements Store, AutoCloseable {
    private Connection cn;

    public SqlTracker() {
    }

    public SqlTracker(Connection cn) {
        this.cn = cn;
    }

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
    public Item add(Item item) {
        try (PreparedStatement statement =
                     cn.prepareStatement("insert into items(name) values(?) returning id;")) {
            statement.setString(1, item.getName());
            try (ResultSet res = statement.executeQuery()) {
                if (res.next()) {
                    item.setId(String.valueOf(res.getInt(1)));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public boolean replace(String id, Item item) {
        boolean flag = false;
        try (PreparedStatement statement = cn.prepareStatement("update items set name = ? where id = ? ;")) {
            statement.setString(1, item.getName());
            statement.setInt(2, Integer.parseInt(id));
            flag = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }


    @Override
    public boolean delete(String id) {
        boolean flag = false;
        try (PreparedStatement statement = cn.prepareStatement("delete from items where id = ?;")) {
            statement.setInt(1, Integer.parseInt(id));
            flag = statement.executeUpdate() > 0;
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
                            String.valueOf(resultSet.getInt("id")),
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
        try (PreparedStatement statement = cn.prepareStatement("select * from items where name = ?;")) {
            statement.setString(1, key);
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    values.add(new Item(String.valueOf(result.getInt("id")),
                            result.getString("name")));
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
        try (PreparedStatement statement = cn.prepareStatement("select * from items where id = ?;")) {
            statement.setInt(1, Integer.parseInt(id));
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    result1 = new Item(
                            String.valueOf(result.getInt("id")),
                            result.getString("name"));
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
