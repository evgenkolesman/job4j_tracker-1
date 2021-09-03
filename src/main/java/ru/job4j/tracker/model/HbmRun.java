package ru.job4j.tracker.model;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.sql.Timestamp;


public class HbmRun {
    static Logger logger = Logger.getLogger(HbmRun.class);
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry)
                    .buildMetadata()
                    .buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            Car car = Car.of("Toyota",
                    new Timestamp(System.currentTimeMillis()), "Evgen");
            Car car1 = Car.of("Rolls Royce",
                    new Timestamp(System.currentTimeMillis()), "Michael");

            Car car2 = Car.of("Ford",
                    new Timestamp(System.currentTimeMillis()), "Alex");

            session.save(car);
            session.save(car1);
            session.save(car2);

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            logger.error("error: ", e);
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}