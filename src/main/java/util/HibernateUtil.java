package util;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {

    private static SessionFactory ourSessionFactory;

    public static SessionFactory getSessionFactory() throws HibernateException {

        if (ourSessionFactory == null) {
            try {
                ourSessionFactory = new Configuration().configure("hibernate.cfg.xml")
                        .setProperty("hibernate.connection.url", System.getenv("JDBC_DATABASE_URL"))
                        .buildSessionFactory();

                Configuration configuration = new Configuration();
                configuration.configure("hibernate.cfg.xml");

                //apply configuration property settings to StandardServiceRegistryBuilder
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties())
                        .build();

                ourSessionFactory = configuration.buildSessionFactory(serviceRegistry);

            } catch (Throwable ex) {
                throw new ExceptionInInitializerError(ex);
            }
            return ourSessionFactory;
        } else {
            return ourSessionFactory;
        }
    }
}
