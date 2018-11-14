package util;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static SessionFactory ourSessionFactory;

    public static SessionFactory getSessionFactory() throws HibernateException {

        if (ourSessionFactory == null) {
            try {
                ourSessionFactory = new Configuration().configure("hibernate.cfg.xml")
                        .setProperty("hibernate.connection.url", System.getenv("JDBC_DATABASE_URL"))
                        .buildSessionFactory();
            } catch (Throwable ex) {
                throw new ExceptionInInitializerError(ex);
            }
            return ourSessionFactory;
        } else {
            return ourSessionFactory;
        }
    }
}
