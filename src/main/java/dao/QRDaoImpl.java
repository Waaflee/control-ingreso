package dao;

import entity.Qrcode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

public class QRDaoImpl {

	public static void put(Qrcode newQrcode) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        session.saveOrUpdate(newQrcode);
        t.commit();
    }

    public static Qrcode getQrcode(int id) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        return (Qrcode) session.load(Qrcode.class, id);
    }

}
