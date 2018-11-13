package util;

import entity.*;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    //    private static final SessionFactory ourSessionFactory;
    private static SessionFactory ourSessionFactory;

/*    static {
        try {
            ourSessionFactory = new Configuration().
                    configure("hibernate.cfg.xml").
                    setProperty("hibernate.connection.url", System.getenv("DATABASE_URL")).
                    *//*addAnnotatedClass(User.class).
                    addAnnotatedClass(Audit.class).
                    addAnnotatedClass(Entryoutput.class).
                    addAnnotatedClass(Parameter.class).
                    addAnnotatedClass(Qrcode.class).*//*
                            buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }*/

    //    public static Session getSessionFactory() throws HibernateException {
    public static SessionFactory getSessionFactory() throws HibernateException {
        //    return ourSessionFactory.openSession();
        if (ourSessionFactory == null) {
            try {
                ourSessionFactory = new Configuration().
                        configure("hibernate.cfg.xml").
                        setProperty("hibernate.connection.url", System.getenv("DATABASE_URL")).
                        /*addAnnotatedClass(User.class).
                        addAnnotatedClass(Audit.class).
                                addAnnotatedClass(Entryoutput.class).
                                addAnnotatedClass(Parameter.class).
                                addAnnotatedClass(Qrcode.class).*/
                                buildSessionFactory();
            } catch (Throwable ex) {
                throw new ExceptionInInitializerError(ex);
            }
            return ourSessionFactory;
        } else {
            return ourSessionFactory;
        }

    }

/*    public static void main(final String[] args) throws Exception {
        final Session session = getSession();
        try {
            System.out.println("querying all the managed entities...");
            final Map metadataMap = session.getSessionFactory().getAllClassMetadata();
            for (Object key : metadataMap.keySet()) {
                final ClassMetadata classMetadata = (ClassMetadata) metadataMap.get(key);
                final String entityName = classMetadata.getEntityName();
                final Query query = session.createQuery("from " + entityName);
                System.out.println("executing: " + query.getQueryString());
                for (Object o : query.list()) {
                    System.out.println("  " + o);
                }
            }
        } finally {
            session.close();
        }
    }*/
}
