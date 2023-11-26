package org.example.utils;
import org.example.model.Citizens;
import org.example.model.City;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    private HibernateUtil() {
    }

    public static synchronized SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
               sessionFactory = new Configuration().addAnnotatedClass(City.class).addAnnotatedClass(Citizens.class).buildSessionFactory();;

            } catch (Throwable ex) {
                System.err.println("Failed to create SessionFactory: " + ex);
                throw new ExceptionInInitializerError(ex);
            }
        }
        return sessionFactory;
    }
}