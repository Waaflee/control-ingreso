package dao;

import entity.Parameter;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.List;

public class ParameterDaoImpl {

	/*public void insert(Audit newAudit) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        session.save(newAudit);
        t.commit();
    }*/

/*    public List<Audit> getAudits() {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        List<Audit> list = session.createQuery("from Audit").list();
        t.commit();
        return list;
    }*/

    public Parameter getParameter(int id) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        return (Parameter) session.load(Parameter.class, id);
    }

/*    public List<Audit> getAuditsFiltered(String filter) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        List<Audit> list = session.createQuery("from Audit where " + filter).list();
        t.commit();
        return list;
    }*/
}
