package ru.job4j.tracker.store;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.criterion.Restrictions;
import org.jboss.logging.Logger;
import ru.job4j.tracker.model.Item;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * HBMTracker is implementing Store class.
 * It includes methods
 * init - connecting to DataBase (DB),
 * add - adding object to DB,
 * replace - updating object,
 * delete - deleting object
 * and other find methods with different variables
 */

public class HbmTracker implements Store, AutoCloseable {
    private final StandardServiceRegistry registry =
            new StandardServiceRegistryBuilder().configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();
    Logger logger = Logger.getLogger(HbmTracker.class);
    private Connection cn;


    public HbmTracker(Connection cn) {
        this.cn = cn;
    }

    public HbmTracker() {
    }

    @Override
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
            logger.error("error: ", e);
        }


    }

    @Override
    public Item add(Item item) throws SQLException {
        Session session = sf.openSession();
        session.beginTransaction();
        try {
            session.save(item);
            session.getTransaction().commit();
        } catch (Exception e) {
            logger.error("error: ", e);
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return item;
    }

    @Override
    public boolean replace(Integer id, Item item) {
        Session session = sf.openSession();
        session.beginTransaction();
        boolean flag = false;
        try {
            if (session.contains(item.getName())) {
                session.merge(item);
                session.getTransaction().commit();
                flag = true;
            }
        } catch (Exception e) {
            logger.error("error: ", e);
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return flag;
    }

    @Override
    public boolean delete(Integer id) {
        Session session = sf.openSession();
        session.beginTransaction();
        boolean flag = false;
        try {
            Item item = new Item();
            item = findById(id);

            session.delete(item);
            session.getTransaction().commit();
            flag = true;

        } catch (Exception e) {
            logger.error("error: ", e);
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return flag;
    }

    @Override
    public List<Item> findAll() {
        Session session = sf.openSession();
        List<Item> list = new ArrayList<>();
        try {
            session.beginTransaction();
//          HQL request
//          Query query = session.createQuery("from items");
//            list = query.list();
//          Criteria request
            Criteria criteria = session.createCriteria(Item.class, "items");
            list = criteria.list();
        } catch (Exception e) {
            logger.error("error: ", e);
        } finally {
            session.close();
        }

        return list;
    }

    @Override
    public List<Item> findByName(String key) {
        Session session = sf.openSession();
        List<Item> list = new ArrayList<>();
        try {
            session.beginTransaction();
//          HQL request
//          Query query = session.createQuery("from Item where name = ?");
//            query.getParameter(1, key);
//            list = query.list();
//          Criteria request
            Criteria criteria = session.createCriteria(Item.class, "items");
            criteria.add(Restrictions.eq("items.name", key));
            list = criteria.list();
        } catch (Exception e) {
            logger.error("error: ", e);
        } finally {
            session.close();
        }

        return list;
    }

    @Override
    public Item findById(Integer id) {
        Session session = sf.openSession();
        Item item;
        session.beginTransaction();
        Criteria criteria = session.createCriteria(Item.class, "items");
        criteria.add(Restrictions.eq("items.id", id));
        item = (Item) criteria.list().get(0);
        session.close();
        return item;
    }

    @Override
    public void close() throws Exception {
        StandardServiceRegistryBuilder.destroy(registry);
    }

    public static void main(String[] args) throws Exception {
        HbmTracker tracker = new HbmTracker();
        tracker.init();
//        List<Item> list1 = List.of(
//                new Item("item1"),
//                new Item("item2"),
//                new Item("item3"),
//                new Item("item4"),
//                new Item("item5"),
//                new Item("item1"));
//        for(Item i : list1) {
//            tracker.add(i);
//        }
//        tracker.findAll().forEach(System.out :: println);

        System.out.println(tracker.findById(1));
        System.out.println(tracker.replace(18, new Item("item3 new")));
//        tracker.findByName("item1").forEach(System.out :: println);
        System.out.println(tracker.delete(35));

    }
}
