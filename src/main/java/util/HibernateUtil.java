package util;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {

    private static SessionFactory ourSessionFactory;

    public static SessionFactory getSessionFactory() throws HibernateException {

        Logger log = LogManager.getLogger();
        log.debug("======================================================================");
        log.debug("======================================================================");
        log.debug("DATABASE_URL=" + System.getenv("DATABASE_URL"));
        log.debug("DB_USER=" + System.getenv("DB_USER"));
        log.debug("DB_PASSWORDL=" + System.getenv("DB_PASSWORD"));
        log.debug("======================================================================");
        log.debug("======================================================================");
        
        if (ourSessionFactory == null) {
            try {
                // ourSessionFactory = new Configuration().configure("hibernate.cfg.xml")
                //         .setProperty("hibernate.connection.url", System.getenv("DATABASE_URL"))
                //         .setProperty("hibernate.connection.username", System.getenv("DB_USER"))
                //         .setProperty("hibernate.connection.password", System.getenv("DB_PASSWORD"))
                //         .buildSessionFactory();
                Configuration configuration = new Configuration();
                configuration.configure("hibernate.cfg.xml")
                        .setProperty("hibernate.connection.url", System.getenv("DATABASE_URL"))
                        .setProperty("hibernate.connection.username", System.getenv("DB_USER"))
                        .setProperty("hibernate.connection.password", System.getenv("DB_PASSWORD"))
                        .buildSessionFactory();

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
