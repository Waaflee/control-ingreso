package dao;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.hibernate.Session;
import org.hibernate.Transaction;

import entity.Audit;
import util.HibernateUtil;

public class AuditDaoImpl implements AuditDAO {

	public void insert(Audit newAudit) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        session.save(newAudit);
        t.commit();
    }

    public List<Audit> getAudits() {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();

        @SuppressWarnings("unchecked")
        List<Audit> list = session.createQuery("from Audit").list();
        t.commit();
        return list;
    }

    public Audit getAudit(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        return session.load(Audit.class, id);
    }

    public List<Audit> getAuditsFiltered(String filter) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        @SuppressWarnings("unchecked")
        List<Audit> list = session.createQuery("from Audit where " + filter).list();
        t.commit();
        LogManager.getLogger().info(list);
        return list;
    }
}
